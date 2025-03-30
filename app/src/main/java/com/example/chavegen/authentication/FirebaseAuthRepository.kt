package com.example.chavegen.authentication

import com.example.chavegen.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

data class AuthResult(
    val currentUser: FirebaseUser? = null,
    val isInitLoading: Boolean = true
)

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {
    private val _currentUser = MutableStateFlow(AuthResult())
    val currentUser = _currentUser.asStateFlow()

    init {
        firebaseAuth.addAuthStateListener {firebaseAuth ->
            _currentUser.update {
                it.copy(
                    currentUser = firebaseAuth.currentUser,
                    isInitLoading = false
                )
            }
        }
    }

    suspend fun signUp(email:String, password: String, userName: String){
        val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
        result.user?.let{user->
            val userData= hashMapOf("name" to userName, "email" to email)
            firebaseFirestore.collection("users").document(user.uid).set(userData).await()
        }
        firebaseAuth.signOut()
    }

    suspend fun signIn(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .await()
    }

    fun signOut(){
        firebaseAuth.signOut()
    }

    suspend fun getUserName(): String? {
        val userId = firebaseAuth.currentUser?.uid ?: return null
        val snapshot = firebaseFirestore.collection("users").document(userId).get().await()
        return snapshot.getString("name")
    }

    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }
}