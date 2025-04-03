package com.example.chavegen.repository

import com.example.chavegen.data.DataSource
import com.example.chavegen.models.ItemLogin
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FireStoreRepository @Inject constructor(
    private val fireStore: FirebaseFirestore,
) {

    private val dataSource = DataSource(
        fireStore = fireStore
    )
    fun salvarLogin(userId: String, siteName: String, siteUrl: String, siteUser: String, sitePassword: String) {
        dataSource.salvarLogin(userId, siteName, siteUrl, siteUser, sitePassword)
    }

    fun getLogins(userId: String): Flow<MutableList<ItemLogin>> {
        return dataSource.getLogins(userId)
    }
}