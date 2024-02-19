package com.example.compsciia.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.compsciia.R
import com.example.compsciia.ui.adapter.CompletedAdapter
import com.example.compsciia.ui.viewmodel.CompletedViewModel
import com.example.compsciia.data.local.database.entities.CompletedWorkout
import com.example.compsciia.databinding.ActivityWorkoutHistoryBinding

class WorkoutHistoryActivity : AppCompatActivity(), CompletedAdapter.CompletedClickListener {

    private val binding: ActivityWorkoutHistoryBinding by lazy {
        ActivityWorkoutHistoryBinding.inflate(layoutInflater)
    }
    private lateinit var completedViewModel: CompletedViewModel
    private val completedAdapter: CompletedAdapter by lazy {
            CompletedAdapter(this)
    }
    private lateinit var selectedWorkout: CompletedWorkout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize ViewModel and RecyclerView
        completedViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[CompletedViewModel::class.java]

        with(binding) {
            recyclerView2.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@WorkoutHistoryActivity)
                adapter = completedAdapter
            }
            imgBackArrow.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
        completedViewModel.allCompletedWorkouts.observe(this) { completedWorkouts ->
            completedWorkouts?.let {
                completedAdapter.updateList2(completedWorkouts)
            }
        }
    }
    private fun popUpDisplay(cardView: CardView) {
        val popUp = PopupMenu(this, cardView)
        popUp.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete_completed -> {
                    completedViewModel.deleteCompleted(selectedWorkout)
                    true
                }

                else -> false
            }
        }
        popUp.inflate(R.menu.pop_up_menu2) // Define a menu resource for completed workouts
        popUp.show()
    }

    override fun onLongItemClicked(completedWorkout: CompletedWorkout, cardView: CardView) {
        selectedWorkout = completedWorkout
        popUpDisplay(cardView)
    }
}
