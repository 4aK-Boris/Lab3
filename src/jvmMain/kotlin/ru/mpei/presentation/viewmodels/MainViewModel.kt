package ru.mpei.presentation.viewmodels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.mpei.domain.usecases.FileUseCases
import ru.mpei.domain.usecases.KeyStoreUseCases
import ru.mpei.domain.usecases.KeyUseCases
import kotlin.system.exitProcess

class MainViewModel(
    private val fileUseCases: FileUseCases,
    private val keyUseCases: KeyUseCases,
    private val keyStoreUseCases: KeyStoreUseCases
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _fileExpanded: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    private val _keyExpanded: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    private val _nickName: MutableStateFlow<String> = MutableStateFlow(value = "")
    private val _text: MutableStateFlow<String> = MutableStateFlow(value = "")
    private val _title: MutableStateFlow<String> = MutableStateFlow(value = "Кирилл Крылов")
    private val _error: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    private val _errorMassage: MutableStateFlow<String> = MutableStateFlow(value = "")
    private val _enabledNickName: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    private val _state: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    private val _certificates: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

    val fileExpanded: StateFlow<Boolean> = _fileExpanded
    val keyExpanded: StateFlow<Boolean> = _keyExpanded
    val nickName: StateFlow<String> = _nickName
    val text: StateFlow<String> = _text
    val title: StateFlow<String> = _title
    val error: StateFlow<Boolean> =_error
    val errorMessage: StateFlow<String> = _errorMassage
    val enabledNickName: StateFlow<Boolean> = _enabledNickName
    val state: StateFlow<Boolean> = _state
    val certificates: StateFlow<List<String>> = _certificates

    fun onFileExpandedChanged(value: Boolean) {
        _fileExpanded.value = value
    }

    fun onKeyExpandedChanged(value: Boolean) {
        _keyExpanded.value = value
    }

    fun onNickNameChanged(value: String) {
        _nickName.value = value
    }

    fun onTextChanged(value: String) {
        _text.value = value
    }

    fun closeErrorDialog() {
        _error.value = false
    }

    fun createFile() {
        _title.value = "Подписанный документ"
        _text.value = ""
        _fileExpanded.value = false
    }

    fun downloadFile() = saveFun {
        val (fileModel, nickName) = fileUseCases.downloadUseCase()
        _fileExpanded.value = false
        _text.value = fileModel.data.decodeToString()
        _title.value = "Подписанный документ $nickName"
    }

    fun saveFile() = saveFun {
        fileUseCases.saveFileUseCase(nickName = _nickName.value, text = _text.value)
        _fileExpanded.value = false
    }

    fun deleteKeyPair() = saveFun {
        _keyExpanded.value = false
        keyUseCases.deleteKeyPair(nickName = _nickName.value)
    }

    fun createKeyPair() = saveFun {
        _keyExpanded.value = false
        keyUseCases.createKeyPair(nickName = _nickName.value)
    }

    fun chooseUser() = saveFun {
        _keyExpanded.value = false
        _enabledNickName.value = true
    }

    fun disableNickName() {
        _enabledNickName.value = false
    }

    fun exit() {
        exitProcess(status = 0)
    }

    fun aboutProgram() {
        _fileExpanded.value = false
        _title.value = "О программе"
        _text.value = "Лабораторная работа 3, Кирилл Крылов"
    }

    fun chooseCertificate() = saveFun {
        _keyExpanded.value = false
        _certificates.value = keyUseCases.chooseCertificate()
        _state.value = true
    }

    fun closeChooserCertificate() {
        _state.value = false
    }

    fun onClickChooseCertificate(nickName: String) {
        _nickName.value = nickName
        _state.value = false
    }

    fun loadKeyStore() = saveFun {
        keyStoreUseCases.loadKeyStore()
    }

    private fun saveFun(f: suspend () -> Unit) = scope.launch {
        try {
            f()
        } catch (e: Exception) {
            _error.value = true
            _errorMassage.value = e.message ?: ""
        }
    }
}