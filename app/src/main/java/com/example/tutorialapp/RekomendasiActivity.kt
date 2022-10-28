package com.example.tutorialapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorialapp.databinding.ActivityMainBinding
import com.example.tutorialapp.databinding.ActivityRekomendasiBinding
import com.squareup.picasso.Picasso
import java.util.*

class RekomendasiActivity : AppCompatActivity() {
  private lateinit var binding: ActivityRekomendasiBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRekomendasiBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)
    initAll(binding)
  }

  fun initAll(binding: ActivityRekomendasiBinding) {
    initButtonListener(binding)
    initRecyclerView(binding)
  }

  fun initButtonListener(binding: ActivityRekomendasiBinding) {
    val backButton: ImageButton = binding.backButton
    backButton.setOnClickListener(object: View.OnClickListener {
      override fun onClick(v: View?) {
        finish()
      }
    })
  }

  fun initRecyclerView(binding: ActivityRekomendasiBinding) {
    var listRelasiRekomendasi = listOf(
      Rekomendasi(
        Relasi(name = "Apotek Suka Sehat", address = "Jln. Sains No 13, Yogyakarta", distance = 1.2f, waUrl = "https://wa.me/6281234567890", mapUrl = "https://goo.gl/maps/4eT3o4keKYFk1Nxq6"),
        listOf(
          Produk(name = "Glimepiride 2mg", stock = 7, manufacturer = "PT. Dexa Medica", imageUrl = "https://images.k24klik.com/product/large/apotek_online_k24klik_20210504101356359225_GLIMEPIRIDE-HEXPHARM-2MG-TAB-100S-2.jpg"),
          Produk(name = "Paracetamol Triman 500mg", stock = 3, manufacturer = "PT. Kimia Farma", imageUrl = "https://images.k24klik.com/product/large/apotek_online_k24klik_20210624013902359225_paracetamol-triman.jpg"),
          Produk(name = "Cotrimoxale 800mg", stock = 5, manufacturer = "PT. Bernofarm", imageUrl = "https://images.k24klik.com/product/apotek_online_k24klik_20220104091034359225_COTRIMOXAZOLE-KF-480MG-TAB-100S-removebg-preview.png"),
        )
      ),
      Rekomendasi(
        Relasi(name = "Apotek Anti Sakit", address = "Jln. Pegangsaan Timur, Yogyakarta", distance = 1.6f, waUrl = "https://wa.me/6281234567890", mapUrl = "https://goo.gl/maps/4eT3o4keKYFk1Nxq7"),
        listOf(
          Produk(name = "Glimepiride 2mg", stock = 7, manufacturer = "PT. Dexa Medica", imageUrl = "https://images.k24klik.com/product/large/apotek_online_k24klik_20210504101356359225_GLIMEPIRIDE-HEXPHARM-2MG-TAB-100S-2.jpg"),
          Produk(name = "Paracetamol Triman 500mg", stock = 3, manufacturer = "PT. Kimia Farma", imageUrl = "https://images.k24klik.com/product/large/apotek_online_k24klik_20210624013902359225_paracetamol-triman.jpg"),
          Produk(name = "Cotrimoxale 800mg", stock = 5, manufacturer = "PT. Bernofarm", imageUrl = "https://images.k24klik.com/product/apotek_online_k24klik_20220104091034359225_COTRIMOXAZOLE-KF-480MG-TAB-100S-removebg-preview.png"),
        )
      ),
    )
    var relasiRekomendasiAdapter = RelasiRekomendasiAdapter(listRelasiRekomendasi, this@RekomendasiActivity)
    binding.rvRelasiRekomendasi.apply {
      layoutManager = LinearLayoutManager(this@RekomendasiActivity)
      adapter = relasiRekomendasiAdapter
    }
  }
}

data class Rekomendasi(
  val relasi: Relasi,
  val produkRekomendasi: List<Produk>
)

class RelasiRekomendasiHolder(val view: View) : RecyclerView.ViewHolder(view) {
  private val index = view.findViewById<TextView>(R.id.index)
  private val name = view.findViewById<TextView>(R.id.name)
  private val address = view.findViewById<TextView>(R.id.address)
  private val distance = view.findViewById<TextView>(R.id.distance)
  private val childRecyclerView = view.findViewById<RecyclerView>(R.id.rvRekomendasi)
  private val rekomendasiLayout = view.findViewById<LinearLayout>(R.id.rekomendasiLayout)
  private val itemRelasiRekomendasi = view.findViewById<LinearLayout>(R.id.itemRelasiRekomendasi)

  fun bindTarget(rekomendasi: Rekomendasi, position: Int, context: Context) {
    index.text = (position+1).toString()
    name.text = rekomendasi.relasi.name
    address.text = rekomendasi.relasi.address
    distance.text = rekomendasi.relasi.distance.toString() + " km"
    itemRelasiRekomendasi.setOnClickListener(object: View.OnClickListener {
      override fun onClick(v: View) {
        if (rekomendasiLayout.visibility == View.VISIBLE) {
          rekomendasiLayout.visibility = View.GONE
        } else {
          rekomendasiLayout.visibility = View.VISIBLE
        }
      }
    })

    var listRekomendasi = rekomendasi.produkRekomendasi
    var rekomendasiAdapter = RekomendasiAdapter(listRekomendasi)
    childRecyclerView.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = rekomendasiAdapter
    }
  }
}

class RelasiRekomendasiAdapter(private val rekomendasi: List<Rekomendasi>, val context: Context) : RecyclerView.Adapter<RelasiRekomendasiHolder>() {
  override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): RelasiRekomendasiHolder {
    return RelasiRekomendasiHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_relasi_rekomendasi, viewGroup, false))
  }
  override fun getItemCount(): Int {
    return rekomendasi.size
  }
  override fun onBindViewHolder(holder: RelasiRekomendasiHolder, position: Int)  {
    holder.bindTarget(rekomendasi[position], position, context)
  }
}


class RekomendasiHolder(view: View) : RecyclerView.ViewHolder(view) {
  private val name = view.findViewById<TextView>(R.id.name)
  private val stock = view.findViewById<TextView>(R.id.stock)
  private val manufacturer = view.findViewById<TextView>(R.id.manufacturer)
  private val image = view.findViewById<ImageView>(R.id.image)

  fun bindTarget(rekomendasi: Produk) {
    name.text = rekomendasi.name
    stock.text = rekomendasi.stock.toString() + " pack"
    manufacturer.text = rekomendasi.manufacturer
    Picasso.get().load(rekomendasi.imageUrl).into(image)
  }
}

class RekomendasiAdapter(private val rekomendasi: List<Produk>) : RecyclerView.Adapter<RekomendasiHolder>() {
  override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): RekomendasiHolder {
    return RekomendasiHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_rekomendasi, viewGroup, false))
  }
  override fun getItemCount(): Int {
    return rekomendasi.size
  }
  override fun onBindViewHolder(holder: RekomendasiHolder, position: Int)  {
    holder.bindTarget(rekomendasi[position])
  }
}