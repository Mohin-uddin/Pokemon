package com.example.pokemon.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.data.response.pokemon_details.Stat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_state.*

class PokemonStatisticsAdapter :
    ListAdapter<Stat, PokemonStatisticsAdapter.PokemonStatisticsHolder>(DiffUtilStatItemList()) {
    class PokemonStatisticsHolder (itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonStatisticsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return PokemonStatisticsHolder(
            layoutInflater.inflate(
                R.layout.item_state,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonStatisticsHolder, position: Int) {
        val statName = getItem(position).stat.name
        val baseState = getItem(position).base_stat.toString()
        holder.tvStatName.text = "$statName  :"
        holder.tvStatValue.text = baseState
    }

    class DiffUtilStatItemList : DiffUtil.ItemCallback<Stat>() {
        override fun areItemsTheSame(
            oldItem: Stat,
            newItem: Stat
        ): Boolean {
            return oldItem.base_stat == newItem.base_stat
        }

        override fun areContentsTheSame(
            oldItem: Stat,
            newItem: Stat
        ): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }

    }
}