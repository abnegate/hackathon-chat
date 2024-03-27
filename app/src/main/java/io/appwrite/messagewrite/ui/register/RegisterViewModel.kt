package io.appwrite.messagewrite.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.ID
import io.appwrite.Permission
import io.appwrite.Role
import io.appwrite.exceptions.AppwriteException
import io.appwrite.messagewrite.COLLECTION_USERS
import io.appwrite.messagewrite.DATABASE_ID
import io.appwrite.messagewrite.models.AuthState
import io.appwrite.messagewrite.repositories.Users
import io.appwrite.models.User
import io.appwrite.messagewrite.models.network.User as NetworkUser
import io.appwrite.services.Account
import io.appwrite.services.Databases
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authState: AuthState,
    private val userRepository: Users,
    private val account: Account,
) : ViewModel() {

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _user: MutableLiveData<User<Map<String, Any>>?> = MutableLiveData()
    val user: LiveData<User<Map<String, Any>>?> = _user

    fun register(
        email: String,
        password: String,
        name: String,
    ) {
        viewModelScope.launch {
            try {
                val user = withContext(IO) {
                    val user = account.create(
                        ID.unique(),
                        email,
                        password,
                        name
                    )

                    account.createEmailPasswordSession(
                        email = email,
                        password = password,
                    )

                    userRepository.create(
                        user.id,
                        name,
                        email,
                    )

                    return@withContext user
                }

                authState.user = user
                _user.value = user
            } catch (e: AppwriteException) {
                _error.value = e.message ?: "Unknown error"
            }
        }
    }
}