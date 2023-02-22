package com.dmdbilal.emojistatusapp.data.recyclerView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dmdbilal.emojistatusapp.R

class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val txtName = view.findViewById<TextView>(R.id.txtUserName)
    val txtEmoji = view.findViewById<TextView>(R.id.txtUserEmoji)
}