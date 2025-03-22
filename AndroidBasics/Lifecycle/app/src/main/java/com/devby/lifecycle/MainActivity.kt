package com.devby.lifecycle

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devby.lifecycle.databinding.ActivityMainBinding
//LifeCycle,EditText,Intent,Context,Alert
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        println("onCreate() called")
    }

    override fun onStart() {
        super.onStart()
        println("onStart() called")
    }

    override fun onResume() {
        super.onResume()
        println("onResume() called")
    }

    override fun onPause() {
        super.onPause()
        println("onPause() called")
    }
    override fun onStop() {
        super.onStop()
        println("onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy()  called")
    }
    fun sonrakiSayfa(view : View){
        val userInput = binding.editText.text.toString()
        binding.textView.text = "İsim: ${userInput}"

        //intent
        val intent = Intent(this,SecondActivity::class.java)
        intent.putExtra("isim",userInput)
        startActivity(intent)
            //finish()






    }




}