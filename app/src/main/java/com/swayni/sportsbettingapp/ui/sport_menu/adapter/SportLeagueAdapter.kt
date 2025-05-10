package com.swayni.sportsbettingapp.ui.sport_menu.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.swayni.sportsbettingapp.R
import com.swayni.sportsbettingapp.core.extensions.isVisible
import com.swayni.sportsbettingapp.data.model.SportLeagueModel
import com.swayni.sportsbettingapp.databinding.ItemSportLeagueBinding

class SportLeagueAdapter (private val onItemClick : (String) -> Unit)  : RecyclerView.Adapter<SportLeagueAdapter.ViewHolder>(){

    private val list = mutableListOf<SportLeagueModel>()

    inner class ViewHolder(private val binding: ItemSportLeagueBinding, private val isSportVisible: Boolean) : RecyclerView.ViewHolder(binding.root){

        fun bindHolder(sportLeagueModel: SportLeagueModel){
            binding.containerSport.isVisible(isSportVisible)
            binding.sport.text = sportLeagueModel.group
            binding.title.text = sportLeagueModel.title
            binding.description.text = sportLeagueModel.description

            binding.tvActive.text = if (sportLeagueModel.active) "active" else "inactive"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(ItemSportLeagueBinding.inflate(
        LayoutInflater.from(parent.context), parent, false), viewType == 1)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHolder(list[position])

        holder.itemView.setOnClickListener {
            onItemClick.invoke(list[position].key)
        }

        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fall_down)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return 1
        else{
            val beforeData = list[position - 1]
            val currentData = list[position]
            if (beforeData.group.equals(currentData.group)){
                return 0
            }
        }
        return 1
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList : List<SportLeagueModel>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}