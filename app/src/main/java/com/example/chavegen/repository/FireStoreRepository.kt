package com.example.chavegen.repository

import com.example.chavegen.data.DataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FireStoreRepository @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val fireAuth: FirebaseAuth,
    private val dataSource: DataSource
) {
    fun salvarLogin(siteName: String, url: String, email: String, password: String) {
        dataSource.salvarLogin(siteName, url, email, password)
    }

    



}