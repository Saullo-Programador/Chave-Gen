package com.example.chavegen.security

import android.util.Base64
import android.util.Log
import com.example.chavegen.models.ItemLogin
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object LoginCryptoMapper {
    private const val KEY_ALIAS = "CHAVEGEN_AES_KEY"
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"

    init {
        generateSecretKeyIfNotExists()
    }

    private fun generateSecretKeyIfNotExists() {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        if (keyStore.containsAlias(KEY_ALIAS)) return

        val keyGenerator = KeyGenerator.getInstance("AES", ANDROID_KEYSTORE)
        val spec = android.security.keystore.KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            android.security.keystore.KeyProperties.PURPOSE_ENCRYPT or
                    android.security.keystore.KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(android.security.keystore.KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(spec)
        keyGenerator.generateKey()
    }

    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        return (keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry).secretKey
    }

    private fun encrypt(text: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(text.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(iv + encrypted, Base64.DEFAULT)
    }

    private fun decrypt(encrypted: String): String {
        val decoded = Base64.decode(encrypted, Base64.DEFAULT)
        val iv = decoded.copyOfRange(0, 12)
        val encryptedBytes = decoded.copyOfRange(12, decoded.size)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)
        val decrypted = cipher.doFinal(encryptedBytes)
        return String(decrypted, Charsets.UTF_8)
    }

    fun toEncrypted(item: ItemLogin): ItemLogin {
        return try {
            item.copy(
                siteName = encrypt(item.siteName ?: ""),
                siteUrl = encrypt(item.siteUrl ?: ""),
                siteUser = encrypt(item.siteUser ?: ""),
                sitePassword = encrypt(item.sitePassword ?: "")
            )
        } catch (e: Exception) {
            Log.e("CryptoMapper", "Erro ao criptografar", e)
            item
        }
    }

    fun toDecrypted(item: ItemLogin): ItemLogin {
        return try {
            item.copy(
                siteName = decrypt(item.siteName ?: ""),
                siteUrl = decrypt(item.siteUrl ?: ""),
                siteUser = decrypt(item.siteUser ?: ""),
                sitePassword = decrypt(item.sitePassword ?: "")
            )
        } catch (e: Exception) {
            Log.e("CryptoMapper", "Erro ao descriptografar item", e)
            item.copy(sitePassword = "Erro ao descriptografar")
        }
    }
}
