package com.example.chavegen.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class DataSource(
    private val fireStore: FirebaseFirestore
){
    fun salvarLogin(siteName: String, url: String, email: String, password: String){

        val loginMap = hashMapOf(
            "siteName" to siteName,
            "url" to url,
            "email" to email,
            "password" to password
        )

        fireStore
            .collection("logins")
            .document(siteName)
            .set(loginMap)
            .addOnCompleteListener { i->
                Log.i("DataSource", "Item login salvo com sucesso")
            }.addOnFailureListener { e->
                Log.e("DataSource", "Erro ao salvar Item login: $e")
            }
    }
}