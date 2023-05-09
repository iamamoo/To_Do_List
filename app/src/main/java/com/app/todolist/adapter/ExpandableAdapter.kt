package com.app.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.app.todolist.R
import com.app.todolist.models.ExpandableItem

class ExpandableListAdapter(val list: ArrayList<ExpandableItem>) : RecyclerView.Adapter<ExpandableListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val card : LinearLayout = itemView.findViewById(R.id.mainLayout)
        val title : TextView = itemView.findViewById(R.id.expTitle)
        val description : TextView = itemView.findViewById(R.id.expDes)
        val arrow : ImageView = itemView.findViewById(R.id.expImg)
        val expandableLayout : ConstraintLayout = itemView.findViewById(R.id.nestedLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expandale_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.title.text = model.title.toString()
        holder.description.text = model.description.toString()

        val isExpand : Boolean = list[position].isExpandable
        holder.expandableLayout.visibility = if (isExpand) View.VISIBLE else View.GONE
        if (model.isExpandable) holder.arrow.setImageResource(R.drawable.ic_arrow_up_24) else
            holder.arrow.setImageResource(R.drawable.ic_arrow_down)

        holder.card.setOnClickListener {
            val data = list[position]
            data.isExpandable = !data.isExpandable
            notifyItemChanged(position)
        }




    }


}
