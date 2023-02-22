package com.dmdbilal.emojistatusapp.data.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmdbilal.emojistatusapp.R
import com.dmdbilal.emojistatusapp.domain.User
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions


class ItemAdapter(
    val options: FirebaseRecyclerOptions<User>
) {
    val adapter = object: FirebaseRecyclerAdapter<User, ItemViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_layout, parent, false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int, model: User) {
            holder.txtName.text = model.displayName
            holder.txtEmoji.text = model.emojis
        }
    }
}
