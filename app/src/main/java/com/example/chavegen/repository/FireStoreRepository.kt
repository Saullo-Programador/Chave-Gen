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

    suspend fun salvarLogin(userId: String, itemLogin: ItemLogin) {
        dataSource.salvarLogin(userId, itemLogin)
    }

    fun getLogins(userId: String): Flow<MutableList<ItemLogin>> {
        return dataSource.getLogins(userId)
    }

    suspend fun deletarLogin(userId: String, documentId: String) {
        dataSource.deletarLogin(userId, documentId)
    }

    suspend fun editarLogin(userId: String, itemLogin: ItemLogin) {
        dataSource.editarLogin(userId, itemLogin)
    }

    suspend fun getLoginById(userId: String, documentId: String): ItemLogin? {
        return dataSource.getLoginById(userId, documentId)
    }
}
