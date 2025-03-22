package com.devby.superkahramanrecyclerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.devby.superkahramanrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var superKahramanList: ArrayList<SuperKahraman>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val superman = SuperKahraman("Superman","Gazeteci",R.drawable.superman)
        val batman = SuperKahraman("Batman","Zengin",R.drawable.batman)
        val ironman = SuperKahraman("Ironman","Mühendis",R.drawable.ironman)
        val aquaman = SuperKahraman("Aquaman","Kral",R.drawable.aquaman)
        superKahramanList = arrayListOf(superman,batman,ironman,aquaman)
        val adapter = SuperKahramanAdapter(superKahramanList)
        binding.superKahramanRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.superKahramanRecyclerView.adapter = adapter

    }
}