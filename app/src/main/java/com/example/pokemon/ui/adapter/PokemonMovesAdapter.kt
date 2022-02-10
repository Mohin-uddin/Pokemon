package com.example.pokemon.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.data.response.pokemon_details.Move
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_pokemon_move.*

class PokemonMovesAdapter : ListAdapter<Move, PokemonMovesAdapter.PokemonMovesHolder>(DiffUtilMovesItemList()) {

    class PokemonMovesHolder (itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonMovesHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return PokemonMovesHolder(
            layoutInflater.inflate(
                R.layout.item_pokemon_move,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonMovesHolder, position: Int) {
        holder.tvMoveName.text=getItem(position).move.name
    }

    class DiffUtilMovesItemList : DiffUtil.ItemCallback<Move>() {
        override fun areItemsTheSame(
            oldItem: Move,
            newItem: Move
        ): Boolean {
            return oldItem.move.name == newItem.move.name
        }

        override fun areContentsTheSame(
            oldItem: Move,
            newItem: Move
        ): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }

    }
}