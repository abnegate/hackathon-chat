package io.appwrite.messagewrite.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.appwrite.messagewrite.databinding.ItemUserBinding
import io.appwrite.messagewrite.models.views.User

class UserAdapter(
    private val onClick: (User, Int) -> Unit,
) : RecyclerView.Adapter<UserViewHolder>() {

    private var chats = emptyList<User>()

    fun setUsers(chats: List<User>) {
        this.chats = chats
        notifyItemRangeChanged(0, chats.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return UserViewHolder(view.root)
    }

    override fun getItemCount() = chats.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClick(chats[position], position)
        }
        holder.bind(chats[position])
    }
}