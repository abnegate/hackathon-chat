package io.appwrite.messagewrite.ui.chats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.appwrite.messagewrite.databinding.ItemChatBinding
import io.appwrite.messagewrite.models.views.Chat

class ChatAdapter(
    private val onClick: (Chat, Int) -> Unit,
) : RecyclerView.Adapter<ChatViewHolder>() {

    private var chats = emptyList<Chat>()

    fun setChats(chats: List<Chat>) {
        this.chats = chats
        notifyItemRangeChanged(0, chats.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ChatViewHolder(view.root)
    }

    override fun getItemCount() = chats.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClick(chats[position], position)
        }
        holder.bind(chats[position])
    }
}