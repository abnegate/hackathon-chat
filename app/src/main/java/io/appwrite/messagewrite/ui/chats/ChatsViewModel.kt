package io.appwrite.messagewrite.ui.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appwrite.messagewrite.models.AuthState
import io.appwrite.messagewrite.repositories.Chats
import io.appwrite.messagewrite.repositories.Messages
import io.appwrite.messagewrite.repositories.Users
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import io.appwrite.messagewrite.models.views.Chat as ChatView

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val authState: AuthState,
    private val userRepository: Users,
    private val chatRepository: Chats,
    private val messageRepository: Messages,
) : ViewModel() {

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _chats = MutableLiveData<List<ChatView>>()
    val chats = _chats

    fun getChats() {
        viewModelScope.launch {
            try {
                val chats = withContext(IO) {
                    // TODO: Pagination
                    val chats = chatRepository.list(
                        limit = 100,
                        offset = 0
                    )

                    val user = authState.user!!

                    return@withContext chats.documents.map {
                        val isUser1 = user.id == it.data.user1Id
                        val profileUrl: String
                        val username: String

                        val user1 = userRepository.get(it.data.user1Id)!!
                        val user2 = userRepository.get(it.data.user2Id)!!

                        if (isUser1) {
                            profileUrl = user2.data.photoUrl!!
                            username = user2.data.name
                        } else {
                            profileUrl = user1.data.photoUrl!!
                            username = user1.data.name
                        }

                        val lastMessage = if (!it.data.lastMessageId.isNullOrEmpty()) {
                            messageRepository
                                .get(it.data.lastMessageId!!)
                                .data
                                .content
                        } else {
                            ""
                        }

                        ChatView(
                            id = it.id,
                            profileImageUrl = profileUrl,
                            username = username,
                            lastMessage = lastMessage,
                            lastMessageTime = it.updatedAt,
                        )
                    }
                }

                _chats.value = chats
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            }
        }
    }
}