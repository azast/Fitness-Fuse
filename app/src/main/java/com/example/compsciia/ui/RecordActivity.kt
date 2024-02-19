package com.example.compsciia.ui

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.compsciia.R
import com.example.compsciia.data.local.database.entities.WorkoutRecord
import com.example.compsciia.databinding.ActivityRecordBinding
import com.example.compsciia.ui.viewmodel.WorkoutRecordViewModel
import com.example.compsciia.utilities.ExtensionFun.showToast

class RecordActivity : AppCompatActivity() {
    private val binding: ActivityRecordBinding by lazy {
        ActivityRecordBinding.inflate(layoutInflater)
    }
    private lateinit var workoutViewModel: WorkoutRecordViewModel
    private var isUpdate = false
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var editor: Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

         sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()
        workoutViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[WorkoutRecordViewModel::class.java]

        with(binding) {
            workoutViewModel.allWorkoutsRecord.observe(this@RecordActivity) { list ->
                if (list.isNotEmpty())
                    list?.let {
                        isUpdate = true
                        etTitle.setText(list[0].title)
                        etWorkout.setText(list[0].desc)
                        etFood.setText(list[0].diet)
                        etType.setText(list[0].type)
                        val goal = etTitle.text.toString().toInt() + etType.text.toString().toInt() + etFood.text.toString().toInt()
                        tvGoal.setText(sharedPreferences.getInt("goal",0).toString())
                        progressBar.max=tvGoal.text.toString().toInt()
                        progressBar.progress = goal
                    }
            }
            imgCheck.setOnClickListener {
                val record = etTitle.text.toString()
                val workoutDesc = etWorkout.text.toString()
                val sets = etType.text.toString()
                val diet = etFood.text.toString()


                if (title.isNotEmpty() || workoutDesc.isNotEmpty() || tvGoal.text.toString().isNotEmpty()) {
                    if (isUpdate) {
                        val workout = WorkoutRecord(1, title = record, sets, diet, desc = workoutDesc)
                        workoutViewModel.updateWorkout(workout)
                    } else {
                        val w = WorkoutRecord(null, title = record, sets, diet, desc = workoutDesc)
                        workoutViewModel.insertWorkout(w)
                    }
                    editor.putInt("goal",tvGoal.text.toString().toInt()).commit()
                    showToast(msg = getString(R.string.updated_successfully))
                } else {
                    showToast(msg = getString(R.string.please_enter_some_data))
                    return@setOnClickListener
                }
            }
            imgBackArrow.setOnClickListener { onBackPressed() }
        }
    }
}