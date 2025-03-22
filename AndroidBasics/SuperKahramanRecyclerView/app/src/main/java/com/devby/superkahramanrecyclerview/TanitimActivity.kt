package com.devby.superkahramanrecyclerview

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devby.superkahramanrecyclerview.databinding.ActivityMainBinding
import com.devby.superkahramanrecyclerview.databinding.ActivityTanitimBinding

class TanitimActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTanitimBinding
    private lateinit var superKahramanList: ArrayList<SuperKahraman>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTanitimBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //val adapterdenGelenIntent = intent
        // adapterdenGelenIntent.getSerializableExtra("secilenKahraman",SuperKahraman::class.java)     @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        //val secilenKahraman = adapterdenGelenIntent.getSerializableExtra("secilenKahraman") as SuperKahraman
        val secilenKahraman = MySingleton.secilenSuperKahraman
        secilenKahraman?.let{
            binding.imageView.setImageResource((secilenKahraman.gorsel))
            binding.isimTextView.setText(secilenKahraman.isim)
            binding.meslekTextView.setText(secilenKahraman.meslek)
        }





    }
}