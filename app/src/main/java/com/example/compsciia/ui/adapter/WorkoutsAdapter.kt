package com.example.compsciia.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.compsciia.data.local.database.entities.Workout
import com.example.compsciia.databinding.ListItemBinding
import com.example.compsciia.utilities.ExtensionFun.randomColor

class WorkoutsAdapter(private val context: Context, private val listener: WorkoutsClickListener) :
    RecyclerView.Adapter<WorkoutsAdapter.WorkoutViewHolder>() {

    private val workoutsList = ArrayList<Workout>()
    private val fullList = ArrayList<Workout>()

    inner class WorkoutViewHolder(var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        return WorkoutViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val item = workoutsList[position]
        with(holder.binding) {
            tvTitle.apply {
                text = item.title
                isSelected = true
            }
            tvFood.apply {
                text = item.diet
                isSelected = true
            }
            tvSets.apply {
                text = item.type
                isSelected = true
            }
            tvWorkout.text = item.diet
            cardLayout.apply {
                setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(), null))
                setOnClickListener {
                    listener.onItemClicked(workoutsList[holder.adapterPosition])
                }
                setOnLongClickListener {
                    listener.onLongItemClicked(workoutsList[holder.adapterPosition], this)
                    true
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return workoutsList.size
    }

    fun updateList(newList: List<Workout>) {
        fullList.clear()
        fullList.addAll(newList)
        workoutsList.clear()
        workoutsList.addAll(fullList)
        notifyDataSetChanged()
    }

    //Searching -> if the title contains string x or the description contains x, then it will filter
    fun filterList(search: String) {
        workoutsList.clear()
        for (item in fullList) {
            if (item.title?.lowercase()
                    ?.contains(search.lowercase()) == true || item.workout?.lowercase()
                    ?.contains(search.lowercase()) == true
            ) {
                workoutsList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    interface WorkoutsClickListener {
        fun onItemClicked(workout: Workout){}
        fun onLongItemClicked(workout: Workout, cardView: CardView){}
    }
}