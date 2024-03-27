package io.appwrite.messagewrite.ui.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor() : ViewModel() {
    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

}