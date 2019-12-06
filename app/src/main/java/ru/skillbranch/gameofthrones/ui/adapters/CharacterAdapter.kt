package ru.skillbranch.gameofthrones.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.NeedHouses
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem

class CharacterAdapter(val listener: (CharacterItem) -> Unit) : RecyclerView.Adapter<CharacterAdapter.CharacterItemViewHolder>() {
    var items : List<CharacterItem> = listOf()
    var house : NeedHouses? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterItemViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterItemViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    fun updateData(data : List<CharacterItem>, house : NeedHouses){
        val diffCallback = object : DiffUtil.Callback(){
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = items[oldItemPosition].id == data[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean  = items[oldItemPosition].hashCode() == data[newItemPosition].hashCode()

            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = data.size

        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = data
        this.house = house
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CharacterItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item:CharacterItem, listener: (CharacterItem) -> Unit){
            if (house != null) {
                containerView.findViewById<ImageView>(R.id.imgHouseIcon)
                    .setImageDrawable(containerView.resources.getDrawable(house!!.iconId, containerView.context.theme))
            }
            containerView.findViewById<TextView>(R.id.tvCharacterName).text = item.name
            containerView.findViewById<TextView>(R.id.tvCharacterTitles).text = item.titles.joinToString(" • ")
        }
    }
}