package com.example.compsciia.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.compsciia.R
import com.example.compsciia.ui.adapter.WorkoutsAdapter
import com.example.compsciia.ui.viewmodel.CompletedViewModel
import com.example.compsciia.data.local.database.entities.CompletedWorkout
import com.example.compsciia.data.local.database.entities.Workout
import com.example.compsciia.ui.viewmodel.WorkoutViewModel
import com.example.compsciia.databinding.ActivityMainBinding
import com.example.compsciia.utilities.CURRENT_WORKOUT_INTENT_KEY
import com.example.compsciia.utilities.ExtensionFun.getCurrentDate2
import com.example.compsciia.utilities.WORKOUT_SERIALIZABLE_INTENT_KEY

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(),
    WorkoutsAdapter.WorkoutsClickListener,
    PopupMenu.OnMenuItemClickListener {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var workoutViewModel: WorkoutViewModel
    private val workoutsAdapter: WorkoutsAdapter by lazy {
        WorkoutsAdapter(this, this)
    }
    private lateinit var selectedWorkout: Workout

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val workout = result.data?.getSerializableExtra(WORKOUT_SERIALIZABLE_INTENT_KEY) as? Workout
                if (workout != null) {
                    workoutViewModel.insertWorkout(workout)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initializing UI
        initUI()

        workoutViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[WorkoutViewModel::class.java]

        workoutViewModel.allWorkouts.observe(this) { list ->
            list?.let {
                workoutsAdapter.updateList(list)
            }
        }
    }

    private fun initUI() {
        with(binding) {
            drawerLayout.apply {
                val toggle = ActionBarDrawerToggle(
                    this@MainActivity,
                    this,
                    binding.toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close
                )
                this.addDrawerListener(toggle)
                toggle.syncState()
            }
            navigationView.setNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_item1 -> {
                        val intent = Intent(this@MainActivity, AddWorkoutActivity::class.java)
                        getContent.launch(intent)
                        true
                    }

                    R.id.menu_item2 -> {
                        val intent = Intent(this@MainActivity, WorkoutHistoryActivity::class.java)
                        getContent.launch(intent)
                        true
                    }
                    R.id.menu_item3 -> {
                        val intent = Intent(this@MainActivity, RecordActivity::class.java)
                        getContent.launch(intent)
                        true
                    }

                    else -> false
                }
            }
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
                adapter = workoutsAdapter
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        workoutsAdapter.filterList(newText)
                    }
                    return true
                }
            })
        }
    }

    override fun onItemClicked(workout: Workout) {
        val intent = Intent(this@MainActivity, AddWorkoutActivity::class.java)
        intent.putExtra(CURRENT_WORKOUT_INTENT_KEY, workout)
        startActivity(intent)
    }

    override fun onLongItemClicked(workout: Workout, cardView: CardView) {
        selectedWorkout = workout
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView) {
        val popUp = PopupMenu(this, cardView)
        popUp.apply {
            setOnMenuItemClickListener(this@MainActivity)
            inflate(R.menu.pop_up_menu)
            show()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_workout) {
            workoutViewModel.deleteWorkout(selectedWorkout)
            return true
        } else if (item?.itemId == R.id.complete_workout) {
            val completedWorkout = CompletedWorkout(
                id2 = null,
                title2 = selectedWorkout.title,
                date2 = getCurrentDate2(),
                associatedWorkoutId = selectedWorkout.id
            )
            val completedViewModel = ViewModelProvider(this)[CompletedViewModel::class.java]
            completedViewModel.insertCompleted(completedWorkout)
            return true
        }
        return false
    }
}
