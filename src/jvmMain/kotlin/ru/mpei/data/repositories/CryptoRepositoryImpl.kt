package ru.mpei.data.repositories

import java.io.ByteArrayInputStream
import java.security.PrivateKey
import java.security.SecureRandom
import java.security.Signature
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import ru.mpei.domain.repositories.CryptoRepository

class CryptoRepositoryImpl(
    private val fileSignature: Signature,
    private val secureRandom: SecureRandom,
    private val certificateFactory: CertificateFactory
): CryptoRepository {
    override fun createSignature(data: ByteArray, privateKey: PrivateKey): ByteArray {
        fileSignature.initSign(privateKey, secureRandom)
        fileSignature.update(data)
        return fileSignature.sign()
    }

    override fun verifySignature(data: ByteArray, sign: ByteArray, certificate: X509Certificate): Boolean {
        fileSignature.initVerify(certificate.publicKey)
        fileSignature.update(data)
        return fileSignature.verify(sign)
    }

    override fun decodeCertificate(certificate: ByteArray): X509Certificate {
        val inputStream = ByteArrayInputStream(certificate)
        return certificateFactory.generateCertificate(inputStream) as X509Certificate
    }
}