package ru.mpei.data.di

import org.koin.dsl.module
import ru.mpei.data.mappers.FileMapper
import ru.mpei.data.mappers.IntToByteMapper

val mapperModule = module {

    factory {
        FileMapper(intToByteMapper = get())
    }

    factory {
        IntToByteMapper()
    }
}