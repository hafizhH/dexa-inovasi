package com.example.tutorialapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorialapp.databinding.ActivityMainBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.util.Date

class MainActivity: AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  companion object {
    var produk: List<Produk>? = listOf(
      Produk(name = "Glimepirides 2mg", stock = 7, manufacturer = "PT. Dexa Medica", imageUrl = "https://images.k24klik.com/product/large/apotek_online_k24klik_20210504101356359225_GLIMEPIRIDE-HEXPHARM-2MG-TAB-100S-2.jpg"),
    )
  //  var relasi: Relasi
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)
    initAll(binding)
  }

  override fun onStart() {
    super.onStart()
    Log.d("MainActivity", "onStart")
    Log.d("MainActivity", produk.toString())
  }

  fun initAll(binding: ActivityMainBinding) {
    fetchAllData()
    initButtonListener(binding)
    initRecyclerView(binding)
  }

  fun fetchAllData() {
    var retrofit = RetrofitClient.getInstance()
    var apiInterface = retrofit.create(ApiInterface::class.java)
    lifecycleScope.launchWhenCreated {
      try {
        val response = apiInterface.fetchAllData()
        if (response.isSuccessful()) {
          produk = response.body()?.produk
          //Log.d("MainActivity", response.body()?.produk.toString())
          Log.d("MainActivity", produk.toString())
          //Log.d("relasi", response.body()?.relasi.toString())
//          Toast.makeText(
//            this@MainActivity,
//            "debug",
//            Toast.LENGTH_LONG
//          ).show()
        } else {
          Toast.makeText(
            this@MainActivity,
            response.errorBody().toString(),
            Toast.LENGTH_LONG
          ).show()
        }
      }catch (Ex:Exception){
        Log.e("Error",Ex.localizedMessage)
      }
    }
  }

  fun initButtonListener(binding: ActivityMainBinding) {
    val relasiButton: LinearLayout = binding.relasiButton
    relasiButton.setOnClickListener(object: View.OnClickListener {
      override fun onClick(v: View?) {
        val intent = Intent(this@MainActivity, RelasiActivity::class.java)
        startActivity(intent)
      }
    })

    val produkButton: LinearLayout = binding.produkButton
    produkButton.setOnClickListener(object: View.OnClickListener {
      override fun onClick(v: View?) {
        val intent = Intent(this@MainActivity, ProdukActivity::class.java)
        startActivity(intent)
      }
    })

    val rekomendasiButton: LinearLayout = binding.rekomendasiButton
    rekomendasiButton.setOnClickListener(object: View.OnClickListener {
      override fun onClick(v: View?) {
        val intent = Intent(this@MainActivity, RekomendasiActivity::class.java)
        startActivity(intent)
      }
    })

    val targetButton: LinearLayout = binding.targetButton
    val targetButton2: TextView = binding.targetButton2
    val targetListener = object: View.OnClickListener {
      override fun onClick(v: View?) {
        val intent = Intent(this@MainActivity, TargetActivity::class.java)
        startActivity(intent)
      }
    }
    targetButton.setOnClickListener(targetListener)
    targetButton2.setOnClickListener(targetListener)

    val pesananButton: LinearLayout = binding.pesananButton
    val pesananButton2: TextView = binding.pesananButton2
    val pesananListener = object: View.OnClickListener {
      override fun onClick(v: View?) {
        val intent = Intent(this@MainActivity, CreateOrderActivity::class.java)
        startActivity(intent)
      }
    }
    pesananButton.setOnClickListener(pesananListener)
    pesananButton2.setOnClickListener(pesananListener)

    val riwayatButton: LinearLayout = binding.riwayatButton
    val riwayatButton2: TextView = binding.riwayatButton2
    val riwayatListener = object: View.OnClickListener {
      override fun onClick(v: View?) {
        val intent = Intent(this@MainActivity, RiwayatActivity::class.java)
        startActivity(intent)
      }
    }
    riwayatButton.setOnClickListener(riwayatListener)
    riwayatButton2.setOnClickListener(riwayatListener)
  }

  fun initRecyclerView(binding: ActivityMainBinding) {
    var listTargetMain = listOf(
      Target(productName = "Glimepiride 2mg", currentSale = 5, targetSale = 10, productImageUrl = "https://images.k24klik.com/product/large/apotek_online_k24klik_20210504101356359225_GLIMEPIRIDE-HEXPHARM-2MG-TAB-100S-2.jpg"),
      Target(productName = "Paracetamol Triman 500mg", currentSale = 8, targetSale = 12, productImageUrl = "https://images.k24klik.com/product/large/apotek_online_k24klik_20210624013902359225_paracetamol-triman.jpg"),
      Target(productName = "Cotrimoxale 800mg", currentSale = 2, targetSale = 10, productImageUrl = "https://images.k24klik.com/product/apotek_online_k24klik_20220104091034359225_COTRIMOXAZOLE-KF-480MG-TAB-100S-removebg-preview.png"),
    )
    var targetMainAdapter = TargetMainAdapter(listTargetMain, baseContext)
    binding.rvTargetMain.apply {
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = targetMainAdapter
    }

    var listRiwayat = listOf(
      Riwayat(productName = "Glimepiride 2mg", relasiName = "Apotek Suka Sehat", date = Date(2022, 9, 30, 14, 32)),
      Riwayat(productName = "Glimepirid 2mg", relasiName = "Apotek Suka Sehat", date = Date(2022, 9, 30, 14, 32)),
      Riwayat(productName = "Glimepiri 2mg", relasiName = "Apotek Suka Sehat", date = Date(2022, 9, 30, 14, 32)),
    )
    var riwayatAdapter = RiwayatAdapter(listRiwayat)
    binding.rvRiwayatMain.apply {
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = riwayatAdapter
    }
  }

}

class TargetMainHolder(view: View) : RecyclerView.ViewHolder(view) {
  private val productName = view.findViewById<TextView>(R.id.productName)
  private val currentSale = view.findViewById<TextView>(R.id.currentSale)
  private val targetSale = view.findViewById<TextView>(R.id.targetSale)
  private val progress = view.findViewById<ProgressBar>(R.id.progress)
  private val productImage = view.findViewById<ImageView>(R.id.productImage)

  fun bindTarget(target: Target, context: Context) {
    productName.text = target.productName
    currentSale.text = target.currentSale.toString()
    targetSale.text = target.targetSale.toString()
    progress.progress = ((target.currentSale.toFloat() / target.targetSale.toFloat())*100f).toInt()
    Picasso.get().load(target.productImageUrl).into(productImage, object: com.squareup.picasso.Callback {
      override fun onSuccess() {
        val imageBitmap: Bitmap = (productImage.drawable as BitmapDrawable).bitmap
        val imageDrawable: RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, imageBitmap)
        imageDrawable.setCircular(true)
        imageDrawable.cornerRadius = Math.max(imageBitmap.width, imageBitmap.height) / 5.0f
        productImage.setImageDrawable(imageDrawable)
      }
      override fun onError(e: Exception?) {

      }
    })
  }
}

class TargetMainAdapter(private val target: List<Target>, val context: Context) : RecyclerView.Adapter<TargetMainHolder>() {
  override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): TargetMainHolder {
    return TargetMainHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_target_main, viewGroup, false))
  }
  override fun getItemCount(): Int {
    return target.size
  }
  override fun onBindViewHolder(holder: TargetMainHolder, position: Int)  {
    holder.bindTarget(target[position], context)
  }
}

data class Riwayat(
  val productName: String,
  val relasiName: String,
  val date: Date
)

class RiwayatHolder(view: View) : RecyclerView.ViewHolder(view) {
  private val productName = view.findViewById<TextView>(R.id.productName)
  private val relasiName = view.findViewById<TextView>(R.id.relasiName)
  private val date = view.findViewById<TextView>(R.id.date)
  fun bindTarget(riwayat: Riwayat) {
    productName.text = riwayat.productName
    relasiName.text = riwayat.relasiName
    date.text = riwayat.date.toString()
  }
}

class RiwayatAdapter(private val riwayat: List<Riwayat>) : RecyclerView.Adapter<RiwayatHolder>() {
  override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): RiwayatHolder {
    return RiwayatHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_riwayat_main, viewGroup, false))
  }
  override fun getItemCount(): Int {
    return riwayat.size
  }
  override fun onBindViewHolder(holder: RiwayatHolder, position: Int)  {
    holder.bindTarget(riwayat[position])
  }
}
