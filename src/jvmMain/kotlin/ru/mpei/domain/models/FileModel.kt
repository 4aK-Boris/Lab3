package ru.mpei.domain.models

data class FileModel(
    val data: ByteArray,
    val certificate: ByteArray,
    val signature: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileModel

        if (!data.contentEquals(other.data)) return false
        if (!certificate.contentEquals(other.certificate)) return false
        if (!signature.contentEquals(other.signature)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.hashCode()
        result = 31 * result + certificate.contentHashCode()
        result = 31 * result + signature.contentHashCode()
        return result
    }
}