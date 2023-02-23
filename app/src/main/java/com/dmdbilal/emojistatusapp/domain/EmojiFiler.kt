package com.dmdbilal.emojistatusapp.domain

import android.content.Context
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.widget.Toast

class EmojiFilter(private val context: Context): InputFilter {
    // Valid character types for unicode
    private val VALID_CHAR_TYPES = listOf(
        Character.NON_SPACING_MARK, // 6
        Character.DECIMAL_DIGIT_NUMBER, // 9
        Character.LETTER_NUMBER, // 10
        Character.OTHER_NUMBER, // 11
        Character.SPACE_SEPARATOR, // 12
        Character.FORMAT, // 16
        Character.SURROGATE, // 19
        Character.DASH_PUNCTUATION, // 20
        Character.START_PUNCTUATION, // 21
        Character.END_PUNCTUATION, // 22
        Character.CONNECTOR_PUNCTUATION, // 23
        Character.OTHER_PUNCTUATION, // 24
        Character.MATH_SYMBOL, // 25
        Character.CURRENCY_SYMBOL, //26
        Character.MODIFIER_SYMBOL, // 27
        Character.OTHER_SYMBOL // 28
    ).map { it.toInt() }.toSet()

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence {
        if (source == null || source.isBlank()) return ""
//        Log.i("MainActivity", "Added text $source, it has length ${source.length} characters.")
        for (inputChar in source) {
            val type = Character.getType(inputChar)
            if (!VALID_CHAR_TYPES.contains(type)) {
                Toast.makeText(
                    context,
                    "Only emojis are allowed!",
                    Toast.LENGTH_SHORT
                ).show()
                return ""
            }
        }
        return source
    }
}