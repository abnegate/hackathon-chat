package io.appwrite.messagewrite.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.messagewrite.models.AuthState
import io.appwrite.models.User
import io.appwrite.services.Account
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authState: AuthState,
    private val account: Account,
) : ViewModel() {

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _user: MutableLiveData<User<Map<String, Any>>?> = MutableLiveData()
    val user: LiveData<User<Map<String, Any>>?> = _user

    fun login(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            try {
                val user = withContext(IO) {
                    account.createEmailPasswordSession(
                        email = email,
                        password = password,
                    )

                    return@withContext account.get()
                }

                authState.user = user
                _user.value = user
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            }
        }
    }
}