package com.swayni.sportsbettingapp.ui.bulletin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.swayni.sportsbettingapp.R
import com.swayni.sportsbettingapp.core.extensions.isVisible
import com.swayni.sportsbettingapp.core.utils.apiToUiString
import com.swayni.sportsbettingapp.data.model.EventsModel
import com.swayni.sportsbettingapp.databinding.ItemBulletinBinding


class BulletinAdapter (
    private val itemClickListener: ((EventsModel) -> Unit) ) : RecyclerView.Adapter<BulletinAdapter.BulletinViewHolder>() {

    private val list = ArrayList<EventsModel>()

    inner class BulletinViewHolder (
        private val binding: ItemBulletinBinding, private val isLeagueVisible: Boolean): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindData(data: EventsModel) {
            binding.containerLeague.isVisible(isLeagueVisible)
            binding.league.text = data.sportTitle
            binding.teamsText.text = "${data.homeTeam} - ${data.awayTeam}"
            binding.date.text = apiToUiString(data.commenceTime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BulletinViewHolder = BulletinViewHolder(ItemBulletinBinding.inflate(LayoutInflater.from(parent.context), parent, false), viewType == 1)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BulletinViewHolder, position: Int) {
        holder.bindData(
            data = list[position]
        )

        holder.itemView.setOnClickListener {
            itemClickListener.invoke(list[position])
        }

        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fall_down)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return 1
        else{
            val beforeData = list[position - 1]
            val currentData = list[position]
            if (beforeData.sportTitle.equals(currentData.sportTitle) && beforeData.commenceTime.equals(currentData.commenceTime)){
                return 0
            }
        }
        return 1
    }

    fun updateList(newList : List<EventsModel>){
        notifyItemRangeRemoved(0, list.size)
        list.clear()
        list.addAll(newList)
        notifyItemInserted(list.size)
    }
}