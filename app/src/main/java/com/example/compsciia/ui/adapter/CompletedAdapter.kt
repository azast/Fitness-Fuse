package com.example.compsciia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.compsciia.data.local.database.entities.CompletedWorkout
import com.example.compsciia.databinding.CompletedListItemBinding

class CompletedAdapter(private val listener2: CompletedClickListener) :
    RecyclerView.Adapter<CompletedAdapter.CompletedViewHolder>() {

    private val completedList = ArrayList<CompletedWorkout>()
    private val fullCompletedList = ArrayList<CompletedWorkout>()

    inner class CompletedViewHolder(var binding: CompletedListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedViewHolder {
        val view = CompletedListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompletedViewHolder(view)
    }

    override fun onBindViewHolder(holder2: CompletedViewHolder, position: Int) {
        val item = completedList[position]
        with(holder2.binding) {
            tvTitle2.text = item.title2
            tvDate2.text = item.date2
            tvDate2.isSelected = true
            // Assuming you want to use a light blue color
            cardLayout2.setCardBackgroundColor(0xFFADD8E6.toInt())
            cardLayout2.setOnClickListener {
                listener2.onItemClicked(completedList[holder2.adapterPosition])
            }
            cardLayout2.setOnLongClickListener {
                listener2.onLongItemClicked(
                    completedList[holder2.adapterPosition],
                    cardLayout2
                )
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return completedList.size
    }

    fun updateList2(newList2: List<CompletedWorkout>) {
        fullCompletedList.clear()
        fullCompletedList.addAll(newList2)
        completedList.clear()
        completedList.addAll(fullCompletedList)
        notifyDataSetChanged()
    }

    interface CompletedClickListener {
        fun onItemClicked(completedWorkout: CompletedWorkout) {}
        fun onLongItemClicked(completedWorkout: CompletedWorkout, cardView: CardView) {}
    }
}
