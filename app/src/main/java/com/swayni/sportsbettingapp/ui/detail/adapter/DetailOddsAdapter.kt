package com.swayni.sportsbettingapp.ui.detail.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.swayni.sportsbettingapp.R
import com.swayni.sportsbettingapp.core.component.BettingButton
import com.swayni.sportsbettingapp.core.enums.SelectedAction
import com.swayni.sportsbettingapp.data.model.EventModel
import com.swayni.sportsbettingapp.data.model.Markets
import com.swayni.sportsbettingapp.data.model.Outcomes
import com.swayni.sportsbettingapp.databinding.ItemDetailOddsBinding
import com.swayni.sportsbettingapp.domain.model.SelectedModel
import com.swayni.sportsbettingapp.domain.model.DetailOddsModel

class DetailOddsAdapter (
    private val selectedListener: (SelectedModel?, SelectedAction) -> Unit) : RecyclerView.Adapter<DetailOddsAdapter.ViewHolder>() {

    private var list : ArrayList<DetailOddsModel> = arrayListOf()
    private var eventModel : EventModel? = null
    private val selectedMap = HashMap<String, SelectedModel>()

    inner class ViewHolder(private val binding: ItemDetailOddsBinding) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bindHolder(markets: Markets, bookmaker: String){
            binding.title.text = "$bookmaker - ${markets.key}"
            markets.outcomes.forEachIndexed { index, outcomes ->
                when(outcomes.name){
                    eventModel?.homeTeam -> {
                        setBettingButton(
                            bettingButton = binding.bettingButton1,
                            id =  eventModel?.id!!,
                            marketKey = markets.key!!,
                            bookmakers = bookmaker,
                            outcomes = outcomes,
                        )
                    }
                    eventModel?.awayTeam -> {
                        setBettingButton(
                            bettingButton = binding.bettingButton2,
                            id = eventModel?.id!!,
                            marketKey = markets.key!!,
                            bookmakers = bookmaker,
                            outcomes = outcomes,
                        )
                    }
                    else -> {
                        setBettingButton(
                            bettingButton = binding.bettingButton0,
                            id = eventModel?.id!!,
                            marketKey = markets.key!!,
                            bookmakers = bookmaker,
                            outcomes = outcomes,
                        )
                    }
                }
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        private fun setBettingButton(bettingButton: BettingButton, id : String, marketKey:String, bookmakers: String, outcomes: Outcomes){
            var selectedType = SelectedAction.ADD
            selectedMap[id]?.let {
                selectedType = if (
                    it.marketKey == marketKey && it.bookmaker == bookmakers &&
                    it.name == outcomes.name && it.sid == outcomes.sid &&
                    it.point == outcomes.point && it.price == outcomes.price){
                    SelectedAction.DELETE
                }else{
                    SelectedAction.UPDATE
                }
            }
            bettingButton.visibility = View.VISIBLE
            bettingButton.setBettingButton(outcomes.price.toString(), selectedType == SelectedAction.DELETE, isEnabled = true){
                val result = when(selectedType){
                    SelectedAction.DELETE -> selectedMap[id]
                    SelectedAction.ADD,
                    SelectedAction.UPDATE -> SelectedModel(
                        id = id,
                        homeTeam = eventModel?.homeTeam,
                        awayTeam = eventModel?.awayTeam,
                        league = "${eventModel?.sportTitle}",
                        time = eventModel?.commenceTime,
                        bookmaker = bookmakers,
                        marketKey = marketKey,
                        name = outcomes.name,
                        price = outcomes.price,
                        point = outcomes.point,
                        sid = outcomes.sid)
                }
                selectedListener.invoke(result, selectedType)
                notifyItemChanged(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemDetailOddsBinding.inflate(android.view.LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHolder(list[position].markets, list[position].bookmaker)

        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fall_down)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSportOdds(eventModel: EventModel){
        list.clear()
        this.eventModel = eventModel
        eventModel.bookmakers.forEach { bookmaker ->
            bookmaker.markets.forEach { market ->
                list.add(DetailOddsModel(bookmaker.key!!, market))
            }
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSelectedMap(map: HashMap<String, SelectedModel>){
        selectedMap.clear()
        selectedMap.putAll(map)
        notifyDataSetChanged()
    }
}