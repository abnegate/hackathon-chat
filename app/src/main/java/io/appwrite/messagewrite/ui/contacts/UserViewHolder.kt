package io.appwrite.messagewrite.ui.contacts

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import io.appwrite.messagewrite.R
import io.appwrite.messagewrite.models.views.User


class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(user: User) {
        val profileImage = itemView.findViewById<ImageView>(R.id.profile_image)
        val username = itemView.findViewById<TextView>(R.id.username)
        val email = itemView.findViewById<TextView>(R.id.email)

        Glide
            .with(itemView.context)
            .load(user.photoUrl)
            .transform(CircleCrop())
            .into(profileImage)

        username.text = user.name
        email.text = user.email
    }
}