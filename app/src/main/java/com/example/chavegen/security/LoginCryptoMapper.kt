package com.example.chavegen.security

import android.util.Base64
import android.util.Log
import com.example.chavegen.models.ItemLogin
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object LoginCryptoMapper {
    private const val TRANSFORMATION = "AES/GCM/NoPadding"

    private fun encrypt(text: String, key: SecretKey): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val iv = cipher.iv
        val encrypted = cipher.doFinal(text.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(iv + encrypted, Base64.NO_WRAP)
    }

    private fun decrypt(encrypted: String, key: SecretKey): String {
        val decoded = Base64.decode(encrypted, Base64.NO_WRAP)
        val iv = decoded.copyOfRange(0, 12)
        val encryptedBytes = decoded.copyOfRange(12, decoded.size)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, key, spec)
        return String(cipher.doFinal(encryptedBytes), Charsets.UTF_8)
    }

    fun toEncrypted(item: ItemLogin, key: SecretKey): ItemLogin {
        return item.copy(
            siteName = encrypt(item.siteName ?: "", key),
            siteUrl = encrypt(item.siteUrl ?: "", key),
            siteUser = encrypt(item.siteUser ?: "", key),
            sitePassword = encrypt(item.sitePassword ?: "", key)
        )
    }

    fun toDecrypted(item: ItemLogin, key: SecretKey): ItemLogin {
        return try {
            item.copy(
                siteName = decrypt(item.siteName ?: "", key),
                siteUrl = decrypt(item.siteUrl ?: "", key),
                siteUser = decrypt(item.siteUser ?: "", key),
                sitePassword = decrypt(item.sitePassword ?: "", key)
            )
        } catch (e: Exception) {
            Log.e("CryptoMapper", "Erro ao descriptografar", e)
            item.copy(sitePassword = "Erro ao descriptografar")
        }
    }
}
