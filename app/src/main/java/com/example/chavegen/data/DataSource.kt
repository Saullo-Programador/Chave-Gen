package com.example.chavegen.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class DataSource(
    private val fireStore: FirebaseFirestore
){
    fun salvarLogin(userId: String, siteName: String, siteUrl: String, siteUser: String, sitePassword: String){

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
}