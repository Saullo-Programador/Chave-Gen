package com.example.chavegen.data

import com.example.chavegen.models.ItemLogin
import com.example.chavegen.security.CryptoUtils
import com.example.chavegen.security.LoginCryptoMapper
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.crypto.SecretKey

class DataSource(
    private val fireStore: FirebaseFirestore
) {
    private suspend fun getOrCreateUserKey(userId: String): SecretKey? {
        val userDoc = fireStore.collection("users").document(userId).get().await()
        val saltBase64 = userDoc.getString("salt")

        val salt = if (saltBase64 != null) {
            CryptoUtils.decodeSalt(saltBase64)
        } else {
            val novoSalt = CryptoUtils.gerarSalt()
            fireStore.collection("users").document(userId)
                .set(mapOf("salt" to CryptoUtils.encodeSalt(novoSalt)), SetOptions.merge())
            novoSalt
        }

        return CryptoUtils.derivarChave(userId, salt)
    }

    suspend fun salvarLogin(userId: String, itemLogin: ItemLogin) {
        val key = getOrCreateUserKey(userId) ?: return
        val encryptedItem = LoginCryptoMapper.toEncrypted(itemLogin, key)

        fireStore.collection("users")
            .document(userId)
            .collection("logins")
            .document(encryptedItem.documentId)
            .set(encryptedItem)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getLogins(userId: String): Flow<MutableList<ItemLogin>> {
        val loginsFlow = MutableStateFlow<MutableList<ItemLogin>>(mutableListOf())

        fireStore.collection("users")
            .document(userId)
            .collection("logins")
            .addSnapshotListener { snapshot, e ->
                if (e != null || snapshot == null) return@addSnapshotListener

                // Coroutine para descriptografar com a chave
                kotlinx.coroutines.GlobalScope.launch {
                    val key = getOrCreateUserKey(userId) ?: return@launch
                    val listLogins = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(ItemLogin::class.java)?.let {
                            LoginCryptoMapper.toDecrypted(it, key)
                        }
                    }.toMutableList()
                    loginsFlow.emit(listLogins)
                }
            }

        return loginsFlow
    }

    suspend fun editarLogin(userId: String, itemLogin: ItemLogin) {
        val key = getOrCreateUserKey(userId) ?: return
        val encryptedItem = LoginCryptoMapper.toEncrypted(itemLogin, key)

        fireStore.collection("users")
            .document(userId)
            .collection("logins")
            .document(encryptedItem.documentId)
            .update(
                mapOf(
                    "siteName" to encryptedItem.siteName,
                    "siteUrl" to encryptedItem.siteUrl,
                    "siteUser" to encryptedItem.siteUser,
                    "sitePassword" to encryptedItem.sitePassword
                )
            )
    }

    fun deletarLogin(userId: String, documentId: String) {
        fireStore.collection("users")
            .document(userId)
            .collection("logins")
            .document(documentId)
            .delete()
    }

    suspend fun getLoginById(userId: String, documentId: String): ItemLogin? {
        val key = getOrCreateUserKey(userId) ?: return null
        val document = fireStore.collection("users")
            .document(userId)
            .collection("logins")
            .document(documentId)
            .get()
            .await()

        return document.toObject(ItemLogin::class.java)?.let {
            LoginCryptoMapper.toDecrypted(it, key)
        }
    }
}
