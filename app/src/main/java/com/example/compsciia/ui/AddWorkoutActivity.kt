package com.example.compsciia.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.compsciia.R
import com.example.compsciia.data.local.database.entities.Workout
import com.example.compsciia.databinding.ActivityAddWorkoutBinding
import com.example.compsciia.ui.viewmodel.WorkoutViewModel
import com.example.compsciia.utilities.CURRENT_WORKOUT_INTENT_KEY
import com.example.compsciia.utilities.ExtensionFun.addNewLine
import com.example.compsciia.utilities.ExtensionFun.getCurrentDate2
import com.example.compsciia.utilities.ExtensionFun.showToast

@Suppress("DEPRECATION")
class AddWorkoutActivity : AppCompatActivity() {

    private val binding: ActivityAddWorkoutBinding by lazy {
        ActivityAddWorkoutBinding.inflate(layoutInflater)
    }

    //private lateinit var workout: Workout
    private lateinit var oldWorkout: Workout
    private var isUpdate = false
    private lateinit var workoutViewModel: WorkoutViewModel
    private var isTextWatcherEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        workoutViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[WorkoutViewModel::class.java]
        with(binding) {
            try {
                oldWorkout = intent.getSerializableExtra(CURRENT_WORKOUT_INTENT_KEY) as Workout
                etTitle.setText(oldWorkout.title)
                etWorkout.setText(oldWorkout.workout)
                etFood.setText(oldWorkout.diet)
                etType.setText(oldWorkout.type)
                isUpdate = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
            imgCheck.setOnClickListener {
                val title = etTitle.text.toString()
                val workoutDesc = etWorkout.text.toString()
                val sets = etType.text.toString()
                val diet = etFood.text.toString()

                if (title.isNotEmpty() || workoutDesc.isNotEmpty()) {
                    if (isUpdate) {
                        val workout = Workout(
                            oldWorkout.id,
                            title,
                            workoutDesc,
                            getCurrentDate2(),
                            sets,
                            diet
                        )
                        workoutViewModel.updateWorkout(workout)

                    } else {
                        val w = Workout(null, title, workoutDesc, getCurrentDate2(), sets, diet)
                        workoutViewModel.insertWorkout(w)
                    }
                    startActivity(Intent(this@AddWorkoutActivity, MainActivity::class.java))
                } else {
                    showToast(msg = getString(R.string.please_enter_some_data))
                    return@setOnClickListener
                }
            }
            imgBackArrow.setOnClickListener {
                onBackPressed()
            }
            addNewLine.setOnClickListener {
                etFood.addNewLine()
            }
        }
    }

}