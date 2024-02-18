package com.lovejeet.geniusgrove.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lovejeet.geniusgrove.R
import com.lovejeet.geniusgrove.models.Category
import com.lovejeet.geniusgrove.models.CategoryModel

class GridViewAdapter(private val items:List<CategoryModel>,
     private val categoryStats: Map<String,Category>) : RecyclerView.Adapter<GridViewAdapter.ViewHolder>() {

    private var onTouchResponse : OnTouchResponse ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewAdapter.ViewHolder {
       val view = LayoutInflater.from(parent.context)
           .inflate(R.layout.category_list_items,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewAdapter.ViewHolder, position: Int) {
       val item = items[position]
        holder.image.setImageResource(item.image)
        holder.tvCategoryName.text = item.name
        val count = categoryStats[item.id]?.total_num_of_questions
        holder.tvQuestionCount.text = count.toString()+" questions"

        holder.itemView.setOnClickListener{
            if(onTouchResponse!=null)
            {
                onTouchResponse!!.onClick(item.id.toInt())
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.iv_category_icon)
        val tvCategoryName : TextView = itemView.findViewById(R.id.tv_Category_name)
        val tvQuestionCount : TextView = itemView.findViewById(R.id.tv_no_of_questions)
    }

    fun setOnTouchResponse(onTouchResponse: OnTouchResponse)
    {
        this.onTouchResponse = onTouchResponse
    }
    interface OnTouchResponse{
        fun onClick(id:Int)
    }
}