package com.wafer.toy.githubclient.util

import android.content.Context
import android.content.SharedPreferences
import android.security.KeyPairGeneratorSpec
import android.util.Base64
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.util.*
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal

/**
 * The CredentialHelper
 * Please put more info here.
 * @author wafer
 * @since 2019/3/1 12:18
 */
object CredentialHelper {
    private const val KEY_STORE = "AndroidKeyStore"
    private const val TOKEN_ALIAS = "TOKEN"
    private const val RSA_MODE = "RSA/ECB/PKCS1Padding"

    private val keyStore: KeyStore = KeyStore.getInstance(KEY_STORE)

    private lateinit var keyPairGenerator: KeyPairGenerator

    private lateinit var keyPairGeneratorSpec: KeyPairGeneratorSpec

    fun init(context: Context) {
        keyStore.load(null)

        if (!keyStore.containsAlias(TOKEN_ALIAS)) {
            generateKey(context)
        }
    }

    private fun generateKey(context: Context) {
        val start = Calendar.getInstance()
        val end = Calendar.getInstance()
        end.add(Calendar.YEAR, 2)

        keyPairGenerator = KeyPairGenerator.getInstance("RSA", KEY_STORE)

        keyPairGeneratorSpec =
                KeyPairGeneratorSpec.Builder(context)
                        .setKeySize(1024)
                        .setAlias(TOKEN_ALIAS)
                        .setSubject(X500Principal("CN=$TOKEN_ALIAS"))
                        .setSerialNumber(BigInteger.TEN)
                        .setStartDate(start.time)
                        .setEndDate(end.time)
                        .build()

        keyPairGenerator.initialize(keyPairGeneratorSpec)
        keyPairGenerator.generateKeyPair()
    }

    fun encrypt(plainText: String): String {
        val publicKey = keyStore.getCertificate(TOKEN_ALIAS).publicKey
        val cipher = Cipher.getInstance(RSA_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        val encryptedBytes = cipher.doFinal(plainText.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    fun decrypt(encryptedText: String?): String {
        if (encryptedText == null)
            return ""

        val privateKey = keyStore.getKey(TOKEN_ALIAS, null)
        val cipher = Cipher.getInstance(RSA_MODE)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)

        val encryptedBytes = Base64.decode(encryptedText, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(encryptedBytes)

        return decryptedBytes.toString(Charsets.UTF_8)
    }
}