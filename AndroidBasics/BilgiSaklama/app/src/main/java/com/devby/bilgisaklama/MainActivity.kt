package com.devby.bilgisaklama

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devby.bilgisaklama.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    var inputUsername: String? = null

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

        //SharedPreferences
        sharedPreferences =
            this.getSharedPreferences("com.devby.bilgisaklama", Context.MODE_PRIVATE)
        inputUsername = sharedPreferences.getString("isim", "")
        if (inputUsername == "") {
            binding.textView.text = "Kaydedilen İsim : "
        } else {
            binding.textView.text = "Kaydedilen İsim : ${inputUsername}"
        }

    }

    fun kaydet(view: View) {
        val username = binding.editText.text.toString()
        if (username == "") {
            Toast.makeText(this@MainActivity, "İsminizi boş bırakmayın!", Toast.LENGTH_LONG).show()
        } else {
            sharedPreferences.edit().putString("isim", username).apply()
            binding.textView.text = "Kaydedilen İsim : ${username}"
        }
    }

    fun sil(view: View) {
        inputUsername = sharedPreferences.getString("isim", "")
        if (inputUsername != "") {
            sharedPreferences.edit().remove("isim").apply()
        }
        binding.textView.text = "Kaydedilen İsim : ${inputUsername}"


    }

}