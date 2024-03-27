package io.appwrite.messagewrite.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.messagewrite.models.AuthState
import io.appwrite.models.User
import io.appwrite.services.Account
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authState: AuthState,
    private val account: Account,
): ViewModel() {

    private val _user: MutableLiveData<User<Map<String, Any>>?> = MutableLiveData()
    val user: LiveData<User<Map<String, Any>>?> = _user

    fun getUser() {
        viewModelScope.launch {
            try {
                val user = account.get()

                authState.user = user
                _user.value = user
            } catch (e: Exception) {
                _user.value = null
            }
        }
    }
}