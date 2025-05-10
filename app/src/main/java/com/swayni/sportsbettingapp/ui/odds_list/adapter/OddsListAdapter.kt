package com.swayni.sportsbettingapp.ui.odds_list.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.swayni.sportsbettingapp.R
import com.swayni.sportsbettingapp.core.utils.apiToUiString
import com.swayni.sportsbettingapp.databinding.ItemOddsListBinding
import com.swayni.sportsbettingapp.domain.model.SelectedModel


class OddsListAdapter (private val deleteListener: (SelectedModel) -> Unit): RecyclerView.Adapter<OddsListAdapter.ViewHolder>() {

    private val list = ArrayList<SelectedModel>()

    inner class ViewHolder(private val binding: ItemOddsListBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindHolder(data: SelectedModel){
            binding.date.text = apiToUiString(data.time.toString())
            binding.league.text = data.league
            binding.teamsText.text = "${data.homeTeam} - ${data.awayTeam}"
            binding.rate.text = data.price.toString()

            binding.swipeLayout.showMode = SwipeLayout.ShowMode.PullOut
            binding.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, binding.llSplitDelete)
            binding.swipeLayout.isRightSwipeEnabled = true

            binding.llSplitDelete.setOnClickListener {
                deleteListener.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(ItemOddsListBinding.inflate(android.view.LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHolder(list[position])

        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fall_down)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList (newList: List<SelectedModel>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun allDelete(){
        notifyItemRangeRemoved(0, list.size)
        list.clear()
    }
}