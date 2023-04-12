package ru.mpei.domain.repositories

import java.security.PrivateKey
import java.security.cert.X509Certificate

interface CryptoRepository {

    fun createSignature(data: ByteArray, privateKey: PrivateKey): ByteArray

    fun verifySignature(data: ByteArray, sign: ByteArray, certificate: X509Certificate): Boolean

    fun decodeCertificate(certificate: ByteArray): X509Certificate
}