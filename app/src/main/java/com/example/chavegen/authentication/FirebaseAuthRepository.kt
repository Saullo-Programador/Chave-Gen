package com.example.chavegen.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

data class AuthResult(
    val currentUser: FirebaseUser? = null,
    val isInitLoading: Boolean = true,
    val error: String? = null,
    val success: String? = null,
)
class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {
    private val _currentUser = MutableStateFlow(AuthResult())
    val currentUser = _currentUser.asStateFlow()

    init {
        firebaseAuth.addAuthStateListener { firebaseAuth ->
            val currentUser = firebaseAuth.currentUser
            _currentUser.value = AuthResult(
                currentUser = currentUser,
                isInitLoading = false
            )
        }
    }

    suspend fun signUp(email: String, password: String, userName: String): Boolean{

        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let { user ->
                val userData = hashMapOf(
                    "name" to userName,
                    "email" to email
                )
                firebaseFirestore
                    .collection("users")
                    .document(user.uid)
                    .set(userData)
                    .await()
                firebaseAuth.signOut()
            }
            true
        } catch (e: Exception) {
            // Atualiza o estado com erro
            _currentUser.value = AuthResult(error = e.message)
            false
        }
    }



    suspend fun signIn(email: String, password: String) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = firebaseAuth.currentUser
            _currentUser.value = AuthResult(currentUser = user)
        } catch (e: Exception) {
            // Se ocorrer um erro, atualize o estado com a mensagem de erro
            _currentUser.value = AuthResult(error = e.message)
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        _currentUser.value = AuthResult() // Resetar o estado após o logout
    }

    suspend fun getUserName(): String? {
        val userId = firebaseAuth.currentUser?.uid ?: return null
        val snapshot = firebaseFirestore.collection("users").document(userId).get().await()
        return snapshot.getString("name")
    }

    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
