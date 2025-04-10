package com.example.chavegen.data

import android.util.Log
import com.example.chavegen.models.ItemLogin
import com.example.chavegen.security.LoginCryptoMapper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class DataSource(
    private val fireStore: FirebaseFirestore
) {

    fun salvarLogin(
        userId: String,
        itemLogin: ItemLogin
    ) {
        val encryptedItem = LoginCryptoMapper.toEncrypted(itemLogin)
        fireStore
            .collection("users")
            .document(userId)
            .collection("logins")
            .document(encryptedItem.documentId)
            .set(encryptedItem)
            .addOnCompleteListener {
                Log.i("DataSource", "Item login salvo com sucesso para o usuário $userId")
            }
            .addOnFailureListener { e ->
                Log.e("DataSource", "Erro ao salvar Item login: $e")
            }
    }

    fun getLogins(userId: String): Flow<MutableList<ItemLogin>> {
        val loginsFlow = MutableStateFlow<MutableList<ItemLogin>>(mutableListOf())

        fireStore
            .collection("users")
            .document(userId)
            .collection("logins")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("DataSource", "Erro ao buscar logins: $e")
                    return@addSnapshotListener
                }

                val listLogins = mutableListOf<ItemLogin>()
                snapshot?.documents?.forEach { document ->
                    document.toObject(ItemLogin::class.java)?.let {
                        listLogins.add(LoginCryptoMapper.toDecrypted(it))
                    }
                }

                loginsFlow.value = listLogins
            }

        return loginsFlow
    }

    fun deletarLogin(
        userId: String,
        documentId: String
    ) {
        fireStore
            .collection("users")
            .document(userId)
            .collection("logins")
            .document(documentId)
            .delete()
            .addOnSuccessListener {
                Log.i("DataSource", "Login deletado com sucesso para o usuário $userId")
            }
            .addOnFailureListener { e ->
                Log.e("DataSource", "Erro ao deletar login: $e")
            }
    }

    fun editarLogin(
        userId: String,
        itemLogin: ItemLogin
    ) {
        val encryptedItem = LoginCryptoMapper.toEncrypted(itemLogin)
        fireStore
            .collection("users")
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
            .addOnCompleteListener {
                Log.i("DataSource", "Item login editado com sucesso para o usuário $userId")
            }
            .addOnFailureListener { e ->
                Log.e("DataSource", "Erro ao editar Item login: $e")
            }
    }

    fun getLoginById(userId: String, documentId: String, callback: (ItemLogin?) -> Unit) {
        fireStore
            .collection("users")
            .document(userId)
            .collection("logins")
            .document(documentId)
            .get()
            .addOnSuccessListener { document ->
                val item = document.toObject(ItemLogin::class.java)
                item?.let {
                    val decrypted = LoginCryptoMapper.toDecrypted(it)
                    callback(decrypted)
                } ?: callback(null)
            }
            .addOnFailureListener {
                Log.e("DataSource", "Erro ao buscar login por ID: $it")
                callback(null)
            }
    }
}
