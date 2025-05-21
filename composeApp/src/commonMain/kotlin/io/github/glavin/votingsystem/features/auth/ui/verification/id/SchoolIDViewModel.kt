package io.github.glavin.votingsystem.features.auth.ui.verification.id

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.glavin.votingsystem.core.ui.SnackbarController
import io.github.glavin.votingsystem.core.ui.SnackbarEvent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class SchoolIDViewModel(
    private val snackbarController: SnackbarController
) : ViewModel() {


    private var schoolIdJob: Job? = null
    private val schoolIdRegex = "\\d+-\\d+-\\d+".toRegex()

    private val _state = MutableStateFlow(SchoolIDState())
    val state = _state
        .onStart {
            observeSchoolIdChanges()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SchoolIDState())

    private val _event: Channel<SchoolIDAction> = Channel()
    val event = _event.receiveAsFlow()

    fun onAction(action: SchoolIDAction) {
        when (action) {
            is SchoolIDAction.SchoolIdNumber -> _state.update { it.copy(schoolId = action.id) }
            is SchoolIDAction.HasIncompleteProfile -> Unit
            is SchoolIDAction.Submit -> submitSchoolID()
            else -> Unit
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSchoolIdChanges() {
        viewModelScope.launch {
            state
                .map { it.schoolId }
                .distinctUntilChanged()
                .debounce(1.seconds)
                .collect { id ->
                    when {
                        id.isEmpty() -> _state.update {
                            it.copy(
                                isFormatInvalid = false,
                                isSchoolIdNotFound = false
                            )
                        }

                        id.length <= 2 -> snackbarController.sendEvent(SnackbarEvent("School ID Too short"))
                        else -> {
                            schoolIdJob?.cancel()
                            schoolIdJob = validateSchoolIdFormat(id)
                        }
                    }
                }
        }
    }

    private fun submitSchoolID() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        val schoolId = _state.value.schoolId.trim()
        delay(2.seconds)
        _state.update { it.copy(isLoading = false, isVerified = true) }
        snackbarController.sendEvent(SnackbarEvent("School ID Verified!"))
        _event.send(SchoolIDAction.Verified)
    }

    private fun validateSchoolIdFormat(id: String) = viewModelScope.launch {
        val isValid = isSchoolIdFormattedCorrectly(id)
        if (isValid) {
            _state.update { it.copy(isFormatInvalid = false) }
        } else {
            _state.update { it.copy(isFormatInvalid = true) }
        }
    }

    private fun isSchoolIdFormattedCorrectly(id: String): Boolean {
        return schoolIdRegex.matches(id)
    }
}