package io.appwrite.messagewrite.ui.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.messagewrite.models.views.Contact
import io.appwrite.messagewrite.repositories.Contacts
import io.appwrite.messagewrite.repositories.Users
import io.appwrite.models.Document
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewContactViewModel @Inject constructor(
    private val userRepository: Users,
    private val contactsRepository: Contacts,
) : ViewModel() {

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _contact: MutableLiveData<Contact> = MutableLiveData()
    val contact: LiveData<Contact> = _contact

    fun createContact(email: String) {
        viewModelScope.launch {
            val user = withContext(IO) {
                userRepository.getByEmail(email)
            }

            val contact = if (user != null) {
                withContext(IO) {
                    contactsRepository.create(user.id)
                }
            } else {
                _error.value = "User not found"
                return@launch
            }

            _contact.value = Contact(contact.id)
        }
    }
}