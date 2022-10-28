package com.example.tutorialapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorialapp.databinding.ActivityProdukBinding
import com.example.tutorialapp.databinding.ActivityRelasiBinding

class RelasiActivity : AppCompatActivity() {
  private lateinit var binding: ActivityRelasiBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRelasiBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    initAll(binding)
  }

  fun initAll(binding: ActivityRelasiBinding) {
    initButtonListener(binding)
    initRecyclerView()
  }

  fun initButtonListener(binding: ActivityRelasiBinding) {
    val backButton: ImageButton = binding.backButton
    backButton.setOnClickListener(object: View.OnClickListener {
      override fun onClick(v: View?) {
        finish()
      }
    })
  }

  fun initRecyclerView() {
    var listRelasi = listOf(
      Relasi(name = "Apotek Suka Sehat", address = "Jln. Sains No 13, Yogyakarta", distance = 1.2f, waUrl = "https://wa.me/6281234567890", mapUrl = "https://goo.gl/maps/4eT3o4keKYFk1Nxq6"),
      Relasi(name = "Apotek Anti Sakit", address = "Jln. Pegangsaan Timur, Yogyakarta", distance = 1.6f, waUrl = "https://wa.me/6281234567890", mapUrl = "https://goo.gl/maps/4eT3o4keKYFk1Nxq7"),
      Relasi(name = "Apotek Sejahtera", address = "Jln. Murah Hati 12, Yogyakarta", distance = 2.1f, waUrl = "https://wa.me/6281234567890", mapUrl = "https://goo.gl/maps/4eT3o4keKYFk1Nxq8"),
    )
    var relasiAdapter = RelasiAdapter(listRelasi)
    binding.rvRelasi.apply {
      layoutManager = LinearLayoutManager(this@RelasiActivity)
      adapter = relasiAdapter
    }
  }
}

data class Relasi(
  val name: String,
  val waUrl: String,
  val mapUrl: String,
  val address: String,
  val distance: Float,
)

class RelasiHolder(view: View) : RecyclerView.ViewHolder(view) {
  private val index = view.findViewById<TextView>(R.id.index)
  private val name = view.findViewById<TextView>(R.id.name)
  private val address = view.findViewById<TextView>(R.id.address)
  private val distance = view.findViewById<TextView>(R.id.distance)
  private val waButton = view.findViewById<Button>(R.id.waButton)
  private val mapButton = view.findViewById<Button>(R.id.mapButton)

  fun bindTarget(relasi: Relasi, position: Int) {
    index.text = (position+1).toString()
    name.text = relasi.name
    address.text = relasi.address
    distance.text = relasi.distance.toString() + " km"
    waButton.setOnClickListener(object: View.OnClickListener {
      override fun onClick(v: View) {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(relasi.waUrl))
        v.context.startActivity(i)
      }
    })
    mapButton.setOnClickListener(object: View.OnClickListener {
      override fun onClick(v: View) {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(relasi.mapUrl))
        v.context.startActivity(i)
      }
    })
  }
}

class RelasiAdapter(private val relasi: List<Relasi>) : RecyclerView.Adapter<RelasiHolder>() {
  override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): RelasiHolder {
    return RelasiHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_relasi, viewGroup, false))
  }
  override fun getItemCount(): Int {
    return relasi.size
  }
  override fun onBindViewHolder(holder: RelasiHolder, position: Int)  {
    holder.bindTarget(relasi[position], position)
  }
}