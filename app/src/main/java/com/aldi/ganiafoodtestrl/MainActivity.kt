package com.aldi.ganiafoodtestrl

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldi.ganiafoodtestrl.adapter.ListFoodAdapter
import com.aldi.ganiafoodtestrl.adapter.RekomendasiFoodAdapter
import com.aldi.ganiafoodtestrl.databinding.ActivityMainBinding
import com.aldi.ganiafoodtestrl.ext.PrefManager
import com.aldi.ganiafoodtestrl.model.Food
import com.aldi.ganiafoodtestrl.viewmodel.MainViewModel
import com.aldi.ganiafoodtestrl.viewmodel.MyViewModelFactory
import com.aldi.services.ApiConfig
import com.aldi.services.ApiService
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager
    lateinit var viewModel: MainViewModel
//    private val adapter = FoodAdapter()
    private val list = ArrayList<Food>()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefManager = PrefManager(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //get api
        val retrofitService = ApiService.getInstance()
        val mainRepository = ApiConfig(retrofitService)

        //rv rekomendasi
        binding.rvRekomendasi.setHasFixedSize(true)
        binding.rvRekomendasi.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        val adapter = RekomendasiFoodAdapter(list)
        binding.rvRekomendasi.adapter = adapter
        //rv list
        binding.rvMakanan.setHasFixedSize(true)
        binding.rvMakanan.layoutManager = GridLayoutManager(this, 2)
        val adapterr = ListFoodAdapter(list)
        adapterr.setOnItemClickCallback(object : ListFoodAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Food) {
                showSelectedFood(data)
            }
        })
        binding.rvMakanan.adapter = adapterr

        //viewmodel
        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(MainViewModel::class.java)

        viewModel.foodList.observe(this, {
            adapter.setFoods(it)
            adapterr.setFoods(it)
            Log.d("iniloh","${it}")
        })

        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        viewModel.getAllFood()
    }

    private fun showSelectedFood(data: Food) {
        Toast.makeText(this,"${data.image}",Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.DETAIL_FOOD, data)
        startActivity(intent)
        Log.d("Capcay","${DetailActivity.DETAIL_FOOD}")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.back_to_splash -> {
                prefManager!!.setLaunched(true)
                startActivity(Intent(this,SlideScreen::class.java))
                true
            }
            R.id.credits -> {
                showMessageBox("Credits")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showMessageBox(text: String){

        //Inflate the dialog as custom view
        val messageBoxView = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null)
        //AlertDialogBuilder
        val messageBoxBuilder = AlertDialog.Builder(this).setView(messageBoxView)
        //show dialog
        val  messageBoxInstance = messageBoxBuilder.show()
        //set Listener
        messageBoxView.setOnClickListener(){
            //close dialog
            messageBoxInstance.dismiss()
        }
    }
}