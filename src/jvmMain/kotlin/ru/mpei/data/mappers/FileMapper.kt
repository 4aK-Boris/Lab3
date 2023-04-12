package ru.mpei.data.mappers

import ru.mpei.data.dto.FileDTO
import ru.mpei.domain.models.FileModel

class FileMapper(
    private val intToByteMapper: IntToByteMapper
) {

    fun map(fileModel: FileModel): FileDTO {
        val certificateLength = fileModel.certificate.size
        val certificateLengthByte = intToByteMapper.map(value = certificateLength)
        val signatureLength = fileModel.signature.size
        val signatureLengthByte = intToByteMapper.map(value = signatureLength)
        val file = certificateLengthByte + signatureLengthByte + fileModel.certificate + fileModel.signature + fileModel.data
        return FileDTO(file = file)
    }

    fun map(fileDTO: FileDTO): FileModel {
        val certificateLengthByte = fileDTO.file.copyOfRange(fromIndex = 0, toIndex = 3)
        val certificateLength = intToByteMapper.map(value = certificateLengthByte)
        val signatureLengthByte = fileDTO.file.copyOfRange(fromIndex = 3, toIndex = 6)
        val signatureLength =  intToByteMapper.map(value = signatureLengthByte)
        val certificate = fileDTO.file.copyOfRange(fromIndex = 6, toIndex = 6 + certificateLength)
        val signature = fileDTO.file.copyOfRange(fromIndex = 6 + certificateLength, toIndex = 6 + certificateLength + signatureLength)
        val data = fileDTO.file.copyOfRange(fromIndex = 6 + certificateLength + signatureLength, toIndex = fileDTO.file.size)
        return FileModel(data = data, signature = signature, certificate = certificate)
    }
}