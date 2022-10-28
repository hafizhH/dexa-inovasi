package com.example.tutorialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.tutorialapp.databinding.ActivityRiwayatBinding

class RiwayatActivity : AppCompatActivity() {
  private lateinit var binding: ActivityRiwayatBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRiwayatBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    val backButton: ImageButton = binding.backButton
    backButton.setOnClickListener(object: View.OnClickListener {
      override fun onClick(v: View?) {
        finish()
      }
    })
  }
}