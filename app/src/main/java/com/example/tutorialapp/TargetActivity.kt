package com.example.tutorialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorialapp.databinding.ActivityTargetBinding

class TargetActivity : AppCompatActivity() {
  private lateinit var binding: ActivityTargetBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityTargetBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    initAll(binding)
  }

  fun initAll(binding: ActivityTargetBinding) {
    initButtonListener(binding)
    initRecyclerView()
  }

  fun initButtonListener(binding: ActivityTargetBinding) {
    val backButton: ImageButton = binding.backButton
    backButton.setOnClickListener(object: View.OnClickListener {
      override fun onClick(v: View?) {
        finish()
      }
    })
  }

  fun initRecyclerView() {
    var listTarget = listOf(
      Target(productName = "Glimepiride 2mg", currentSale = 5, targetSale = 10, productImageUrl = "https://images.k24klik.com/product/large/apotek_online_k24klik_20210504101356359225_GLIMEPIRIDE-HEXPHARM-2MG-TAB-100S-2.jpg"),
      Target(productName = "Paracetamol Triman 500mg", currentSale = 8, targetSale = 12, productImageUrl = "https://images.k24klik.com/product/large/apotek_online_k24klik_20210624013902359225_paracetamol-triman.jpg"),
      Target(productName = "Cotrimoxale 800mg", currentSale = 2, targetSale = 10, productImageUrl = "https://images.k24klik.com/product/apotek_online_k24klik_20220104091034359225_COTRIMOXAZOLE-KF-480MG-TAB-100S-removebg-preview.png"),
    )
    var targetAdapter = TargetAdapter(listTarget)
    binding.rvTarget.apply {
      layoutManager = LinearLayoutManager(this@TargetActivity)
      adapter = targetAdapter
    }
  }
}

data class Target(
  val productName: String,
  val productImageUrl: String,
  val currentSale: Int,
  val targetSale: Int
)

class TargetHolder(view: View) : RecyclerView.ViewHolder(view) {
  private val productName = view.findViewById<TextView>(R.id.productName)
  private val currentSale = view.findViewById<TextView>(R.id.currentSale)
  private val targetSale = view.findViewById<TextView>(R.id.targetSale)
  private val progress = view.findViewById<ProgressBar>(R.id.progress)
  private val progressLabel = view.findViewById<TextView>(R.id.progressLabel)


  fun bindTarget(target: Target) {
    val percentage = ((target.currentSale.toFloat() / target.targetSale.toFloat())*100f).toInt()
    val percentageLabel = percentage.toString() + "%"

    productName.text = target.productName
    currentSale.text = target.currentSale.toString()
    targetSale.text = target.targetSale.toString()
    progress.progress = percentage
    progressLabel.text = percentageLabel
  }
}

class TargetAdapter(private val target: List<Target>) : RecyclerView.Adapter<TargetHolder>() {
  override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): TargetHolder {
    return TargetHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_target, viewGroup, false))
  }
  override fun getItemCount(): Int {
    return target.size
  }
  override fun onBindViewHolder(holder: TargetHolder, position: Int)  {
    holder.bindTarget(target[position])
  }
}