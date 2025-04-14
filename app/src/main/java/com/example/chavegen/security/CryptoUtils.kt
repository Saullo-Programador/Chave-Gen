package com.example.chavegen.security

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object CryptoUtils {
    fun gerarSalt(): ByteArray {
        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)
        return salt
    }

    fun encodeSalt(salt: ByteArray): String {
        return Base64.encodeToString(salt, Base64.NO_WRAP)
    }

    fun decodeSalt(saltBase64: String): ByteArray {
        return Base64.decode(saltBase64, Base64.NO_WRAP)
    }

    fun derivarChave(uid: String, salt: ByteArray): SecretKey {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec = PBEKeySpec(uid.toCharArray(), salt, 10000, 256)
        val tmp = factory.generateSecret(spec)
        return SecretKeySpec(tmp.encoded, "AES")
    }
}
