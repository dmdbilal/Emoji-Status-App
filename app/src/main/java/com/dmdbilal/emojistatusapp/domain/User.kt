package com.dmdbilal.emojistatusapp.domain

class User {
    var displayName: String? = null
    var emojis: String = ""

    constructor() {} // Needed for Firebase.

    constructor(name: String?, emoji: String) {
        this.displayName = name
        this.emojis = emoji
    }
}