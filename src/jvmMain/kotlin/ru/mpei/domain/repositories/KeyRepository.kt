package ru.mpei.domain.repositories

interface KeyRepository {

    suspend fun createKeyPair(nickName: String)

    suspend fun deleteKeyPair(nickName: String)

    suspend fun chooseCertificate(): List<String>
}