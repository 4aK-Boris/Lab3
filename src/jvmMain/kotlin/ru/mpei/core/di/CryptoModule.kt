package ru.mpei.core.di

import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.SecureRandom
import java.security.Signature
import java.security.cert.CertificateFactory
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.koin.dsl.module

val cryptoModule = module {

    factory {
        Signature.getInstance("MD5WithRSA", BouncyCastleProvider())
    }

    single {
        SecureRandom()
    }

    single {
        KeyPairGenerator.getInstance("RSA")
    }

    single {
        KeyStore.getInstance("PKCS12")
    }

    single {
        CertificateFactory.getInstance("X.509")
    }
}