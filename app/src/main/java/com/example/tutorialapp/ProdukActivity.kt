package com.example.tutorialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorialapp.databinding.ActivityMainBinding
import com.example.tutorialapp.databinding.ActivityProdukBinding
import com.example.tutorialapp.databinding.ActivityTargetBinding
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.util.*

class ProdukActivity : AppCompatActivity() {
  private lateinit var binding: ActivityProdukBinding

  var produk: List<Produk>? = listOf(
    Produk(id="P1", name = "Glimepiride 2mg", stock = 7, manufacturer = "PT. Dexa Medica", imageUrl = "https://images.k24klik.com/product/large/apotek_online_k24klik_20210504101356359225_GLIMEPIRIDE-HEXPHARM-2MG-TAB-100S-2.jpg", detail="Glimepiride adalah obat untuk menurunkan kadar gula darah pada penderita diabetes tipe 2. Untuk meningkatkan efektivitasnya, penggunaan glimepiride harus disertai dengan pengaturan pola makan dan olahraga yang teratur.", price=56000),
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityProdukBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)
    initAll(binding)
  }

  override fun onStart() {
    super.onStart()
    fetchAllData()
  }

  fun initAll(binding: ActivityProdukBinding) {
    initButtonListener(binding)
  }

  fun fetchAllData() {
    var retrofit = RetrofitClient.getInstance()
    var apiInterface = retrofit.create(ApiInterface::class.java)
    lifecycleScope.launchWhenCreated {
      try {
        val response = apiInterface.fetchAllData()
        if (response.isSuccessful()) {
          //produk = response.body()?.produk
          Log.d("ProdukActivity", "DEBUG : " + response.body())
          if (response.body() != null) {
            //response.body().produk
            response.body()?.produk?.let { initRecyclerView(it) }
          }
          Toast.makeText(
            this@ProdukActivity,
            "Fetch completed",
            Toast.LENGTH_LONG
          ).show()
        } else {
          Toast.makeText(
            this@ProdukActivity,
            response.errorBody().toString(),
            Toast.LENGTH_LONG
          ).show()
        }
      }catch (Ex: Exception){
        Log.e("Error",Ex.localizedMessage)
      }
    }
  }

  fun initButtonListener(binding: ActivityProdukBinding) {
    val backButton: ImageButton = binding.backButton
    backButton.setOnClickListener(object: View.OnClickListener {
      override fun onClick(v: View?) {
        finish()
      }
    })
  }

  fun initRecyclerView(produk: List<Produk>) {
//    var listProduk = listOf(
//      Produk(name = "Glimepiride 2mg", stock = 7, manufacturer = "PT. Dexa Medica", imageUrl = "https://images.k24klik.com/product/large/apotek_online_k24klik_20210504101356359225_GLIMEPIRIDE-HEXPHARM-2MG-TAB-100S-2.jpg"),
//      Produk(name = "Paracetamol Triman 500mg", stock = 3, manufacturer = "PT. Kimia Farma", imageUrl = "https://images.k24klik.com/product/large/apotek_online_k24klik_20210624013902359225_paracetamol-triman.jpg"),
//      Produk(name = "Cotrimoxale 800mg", stock = 5, manufacturer = "PT. Bernofarm", imageUrl = "https://images.k24klik.com/product/apotek_online_k24klik_20220104091034359225_COTRIMOXAZOLE-KF-480MG-TAB-100S-removebg-preview.png"),
//    )
    if (produk != null) {
      var produkAdapter = ProdukAdapter(produk)
      binding.rvProduk.apply {
        layoutManager = LinearLayoutManager(this@ProdukActivity)
        adapter = produkAdapter
      }
    }
  }
}

data class Produk(
  val id: String,
  val name: String,
  val imageUrl: String,
  val stock: Int,
  val detail: String,
  val manufacturer: String,
  val price: Int
)

class ProdukHolder(view: View) : RecyclerView.ViewHolder(view) {
  private val name = view.findViewById<TextView>(R.id.name)
  private val stock = view.findViewById<TextView>(R.id.stock)
  private val manufacturer = view.findViewById<TextView>(R.id.manufacturer)
  private val image = view.findViewById<ImageView>(R.id.image)

  fun bindTarget(produk: Produk) {
    name.text = produk.name
    stock.text = produk.stock.toString() + " pack"
    manufacturer.text = produk.manufacturer
    Picasso.get().load(produk.imageUrl).into(image)
  }
}

class ProdukAdapter(private val produk: List<Produk>) : RecyclerView.Adapter<ProdukHolder>() {
  override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ProdukHolder {
    return ProdukHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_produk, viewGroup, false))
  }
  override fun getItemCount(): Int {
    return produk.size
  }
  override fun onBindViewHolder(holder: ProdukHolder, position: Int)  {
    holder.bindTarget(produk[position])
  }
}