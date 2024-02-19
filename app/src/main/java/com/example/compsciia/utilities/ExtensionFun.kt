package com.example.compsciia.utilities

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import com.example.compsciia.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

object ExtensionFun {
    fun Context.showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun getCurrentDate2(): String {
        val sdf = SimpleDateFormat("EEE, d MMM, yyyy HH:mm a", Locale.getDefault())
        return sdf.format(Date())
    }

    fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.WorkoutColor1)
        list.add(R.color.WorkoutColor2)
        list.add(R.color.WorkoutColor3)
        list.add(R.color.WorkoutColor4)
        list.add(R.color.WorkoutColor5)
        list.add(R.color.WorkoutColor6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    fun EditText.addNewLine() {
        val currentText = text.toString()
        val newText = "$currentText\nExample Exercise 2 x 5"
        setText(newText)
        setSelection(text.length)
    }
}