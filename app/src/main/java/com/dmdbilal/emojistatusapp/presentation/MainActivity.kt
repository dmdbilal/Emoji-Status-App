package com.dmdbilal.emojistatusapp.presentation

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.dmdbilal.emojistatusapp.R
import com.dmdbilal.emojistatusapp.data.recyclerView.ItemAdapter
import com.dmdbilal.emojistatusapp.data.recyclerView.ItemViewHolder
import com.dmdbilal.emojistatusapp.domain.User
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {

    private companion object {
        private val TAG = "MainActivity"
    }

    private lateinit var auth: FirebaseAuth
    private val database = Firebase.database
    private lateinit var dbRef: DatabaseReference
    private lateinit var rvUser: RecyclerView
    private lateinit var adapter: FirebaseRecyclerAdapter<User, ItemViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        dbRef = database.getReference("users")

        /** RecyclerView */
        rvUser = findViewById(R.id.rvUser)
        val query: Query = Firebase.database.reference.child("users").limitToFirst(50)
        val options = FirebaseRecyclerOptions.Builder<User>()
            .setLifecycleOwner(this)
            .setQuery(query, User::class.java)
            .build()
        adapter = ItemAdapter(options).adapter

        rvUser.adapter = adapter

    }

    // Menu option
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Logout option
        if(item.itemId == R.id.miLogout) {
            auth.signOut()
            Log.i(TAG, "Logout")
            val logoutIntent = Intent(this, LoginActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logoutIntent)
        }
        if(item.itemId == R.id.miEdit) {
            showAlertDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog() {
        val editText = EditText(this)
        editText.hint = "Enter emojis"
        //TODO: restrict the input to only emoji of length 9.
        val dialog = AlertDialog.Builder(this)
            .setTitle("Update your status")
            .setView(editText)
            .setPositiveButton("Update", null)
            .setNegativeButton("Cancel", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val emojisEntered = editText.text.toString()
            if(emojisEntered.isBlank()) {
                Toast.makeText(this, "Can't submit empty text", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val user = auth.currentUser
            if(user == null) {
                Toast.makeText(this, "No signed in user", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val newUser = User(user.displayName, emojisEntered)
            dbRef.child(user.uid).setValue(newUser)
            dialog.dismiss()
        }
    }
}