package com.example.chavegen.data

import android.util.Log
import com.example.chavegen.models.ItemLogin
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class DataSource(
    private val fireStore: FirebaseFirestore
) {


    //private val _todosLogins = MutableStateFlow<MutableList<ItemLogin>>(mutableListOf())
    //private val todosLogins: StateFlow<MutableList<ItemLogin>> = _todosLogins

    fun salvarLogin(
        userId: String,
        siteName: String,
        siteUrl: String,
        siteUser: String,
        sitePassword: String
    ) {

        val loginMap = hashMapOf(
            "siteName" to siteName,
            "siteUrl" to siteUrl,
            "siteUser" to siteUser,
            "sitePassword" to sitePassword
        )

        fireStore
            .collection("users")
            .document(userId)
            .collection("logins")
            .document(siteName)
            .set(loginMap)
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

}