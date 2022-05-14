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
    var onClickItem: (Scientist?) -> Unit,
    var onClickEdit: (Scientist?) -> Unit,
    var onClickDelete: (Scientist?) -> Unit
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

        fun getEditBtn() : View {
            return view.findViewById(R.id.button_edit)
        }

        fun getDeleteBtn() : View {
            return view.findViewById(R.id.button_delete)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScientistHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scientist_item,parent,false)
        return (ScientistHolder(view))
    }

    override fun onBindViewHolder(holder: ScientistHolder, position: Int) {
        scientistList?.get(position)?.let { holder.setName(it.name) }
        holder.getCard().setOnClickListener {
            onClickItem(scientistList?.get(position))
        }

        holder.getEditBtn().setOnClickListener {
            onClickEdit(scientistList?.get(position))
        }

        holder.getDeleteBtn().setOnClickListener {
            onClickDelete(scientistList?.get(position))
        }
    }

    override fun getItemCount(): Int {
        return scientistList?.size ?: 0
    }
}