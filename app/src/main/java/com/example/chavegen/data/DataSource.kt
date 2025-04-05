package com.example.chavegen.data

import android.util.Log
import com.example.chavegen.models.ItemLogin
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

        fireStore
            .collection("users")
            .document(userId)
            .collection("logins")
            .document(itemLogin.documentId)
            .set(itemLogin)
            .addOnCompleteListener {
                Log.i("DataSource", "Item login salvo com sucesso para o usuário $userId")
            }
            .addOnFailureListener { e ->
                Log.e("DataSource", "Erro ao salvar Item login: $e")
            }
    }

    fun getLogins(
        userId: String
    ): Flow<MutableList<ItemLogin>> {

        val loginsFlow = MutableStateFlow<MutableList<ItemLogin>>(mutableListOf())

        fireStore
            .collection("users")
            .document(userId)
            .collection("logins")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                val listLogins = mutableListOf<ItemLogin>()
                snapshot?.documents?.forEach { document ->
                    document.toObject(ItemLogin::class.java)?.let { listLogins.add(it) }
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
        fireStore
            .collection("users")
            .document(userId)
            .collection("logins")
            .document(itemLogin.documentId)
            .update(mapOf(
                "siteName" to itemLogin.siteName,
                "siteUrl" to itemLogin.siteUrl,
                "siteUser" to itemLogin.siteUser,
                "sitePassword" to itemLogin.sitePassword
            ))
            .addOnCompleteListener {
                Log.i("DataSource", "Item login editado com sucesso para o usuário $userId")
            }
            .addOnFailureListener { e ->
                Log.e("DataSource", "Erro ao editar Item login: $e")
            }
    }

    fun getLoginById(userId: String, documentId: String, callback: (ItemLogin?) -> Unit) {
        fireStore.collection("users")
            .document(userId)
            .collection("logins")
            .document(documentId)
            .get()
            .addOnSuccessListener { document ->
                val item = document.toObject(ItemLogin::class.java)
                callback(item)
            }
            .addOnFailureListener {
                callback(null)
            }
    }


}