package com.example.foodrecipeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.entities.CategoryItems
import com.example.foodrecipeapp.entities.Recipes

class MainCategoryAdapter() : RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>() {

    var listener: onItemClickListener? = null
    var ctx: Context? = null
    var arrMainCategory = ArrayList<CategoryItems>()
    class RecipeViewHolder (view: View): RecyclerView.ViewHolder(view) {

    }

    fun setData(arrData: List<CategoryItems>){
        arrMainCategory = arrData as ArrayList<CategoryItems>
    }

    fun setClickListener(listener1: onItemClickListener){
        listener = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        ctx = parent.context
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv_main_category, parent, false))
    }

    override fun getItemCount(): Int = arrMainCategory.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.tv_dish).text = arrMainCategory[position].strcategory
        Glide.with(ctx!!).load(arrMainCategory[position].strcategorythumb).into(holder.itemView.findViewById<ImageView>(R.id.iv_dish))
        holder.itemView.rootView.setOnClickListener {
            listener!!.onClicked(arrMainCategory[position].strcategory)
        }
    }

    interface onItemClickListener{
        fun onClicked(categoryName: String)
    }
}