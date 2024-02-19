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
import com.example.foodrecipeapp.entities.converter.MealsItems

class SubCategoryAdapter : RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder>() {

    var listener: SubCategoryAdapter.onItemClickListener? = null
    var ctx: Context? = null
    var arrSubCategory = ArrayList<MealsItems>()

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    fun setData(arrData: List<MealsItems>) {
        arrSubCategory = arrData as ArrayList<MealsItems>
    }

    fun setClickListener(listener1: SubCategoryAdapter.onItemClickListener){
        listener = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        ctx = parent.context
        return RecipeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv_sub_category, parent, false)
        )
    }

    override fun getItemCount(): Int = arrSubCategory.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.tv_dish_name).text =
            arrSubCategory[position].strMeal
        Glide.with(ctx!!).load(arrSubCategory[position].strMealThumb)
            .into(holder.itemView.findViewById<ImageView>(R.id.iv_dish_sub))

        holder.itemView.rootView.setOnClickListener {
            listener!!.onClicked(arrSubCategory[position].idMeal)
        }

    }

    interface onItemClickListener {
        fun onClicked(id: String)
    }
}