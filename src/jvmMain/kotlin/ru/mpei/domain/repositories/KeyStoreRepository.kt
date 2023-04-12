package ru.mpei.domain.repositories

import java.security.KeyPair
import java.security.PrivateKey
import java.security.cert.X509Certificate

interface KeyStoreRepository {

    suspend fun load()

    suspend fun save()

    suspend fun addKeyPair(keyPair: KeyPair, nickName: String)

    suspend fun getPrivateKey(nickName: String): PrivateKey

    suspend fun getCertificate(nickName: String): X509Certificate

    suspend fun chooseCertificate(): List<String>

    suspend fun deleteKeyPair(nickName: String)
}