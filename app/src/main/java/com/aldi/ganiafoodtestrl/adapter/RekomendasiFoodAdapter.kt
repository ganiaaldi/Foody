package com.aldi.ganiafoodtestrl.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aldi.ganiafoodtestrl.databinding.ItemRekomendasiBinding
import com.aldi.ganiafoodtestrl.model.Food
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RekomendasiFoodAdapter(private var listFood: ArrayList<Food>) : RecyclerView.Adapter<RekomendasiFoodAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(user: Food)
    }

    fun setFoods(foods: List<Food>) {
        this.listFood = foods.toMutableList() as ArrayList<Food>
        listFood.shuffle()
       notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRekomendasiBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
//        listFood.shuffle()
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFood[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listFood[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = Math.min(listFood.size, 5)

    inner class ListViewHolder(private val binding: ItemRekomendasiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Food) {
            with(binding){
                Glide.with(itemView.context)
                        .load(user.image)
                        .apply(RequestOptions())
                        .into(imageViewFood)
                tvNameFood.text = user.name
            }
        }
    }
}