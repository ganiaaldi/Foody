package com.aldi.ganiafoodtestrl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldi.ganiafoodtestrl.databinding.ActivityDetailBinding
import com.aldi.ganiafoodtestrl.model.Food
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding

    companion object{
        const val DETAIL_FOOD = "detail_food"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
    }

    private fun getData() {
        val food = intent.getParcelableExtra<Food>(DETAIL_FOOD) as Food
        binding.detailFood.text = food.name
        binding.detailBahan.text = food.desc
        Glide.with(applicationContext)
            .load(food.image)
            .into(binding.detailImg)
    }
}