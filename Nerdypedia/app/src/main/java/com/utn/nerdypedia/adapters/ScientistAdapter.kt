package com.utn.nerdypedia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utn.nerdypedia.entities.Scientist
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.utn.nerdypedia.R

class ScientistAdapter(
    var scientistList: MutableList<Scientist?>?,
    var onClick: (Scientist?) -> Unit
) : RecyclerView.Adapter<ScientistAdapter.ScientistHolder>(){

    class ScientistHolder(v : View) : RecyclerView.ViewHolder(v) {
        //Se va a comunicar con el item, aca van los metodos para interactuar con el item
        private var view: View = v //Referencia al item

        fun setName (name : String) {
            var itemName : TextView = view.findViewById(R.id.scientistName)
            itemName.text = name
        }

        fun getCard () : CardView {
            return view.findViewById(R.id.card_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScientistHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scientist_item,parent,false)
        return (ScientistHolder(view))
    }

    override fun onBindViewHolder(holder: ScientistHolder, position: Int) {
        scientistList?.get(position)?.let { holder.setName(it.name) }
        holder.getCard().setOnClickListener {
            onClick(scientistList?.get(position))
        }
    }

    override fun getItemCount(): Int {
        return scientistList?.size ?: 0
    }
}