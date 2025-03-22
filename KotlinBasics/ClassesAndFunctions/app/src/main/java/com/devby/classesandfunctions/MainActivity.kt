package com.devby.classesandfunctions

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var count = 0
    lateinit var benimKahraman: SuperKahraman


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        println("onCreate() called")
        firstFunction()
        firstFunction()
        firstFunction()
        secondFunction()
        firstFunction() //wrong value for global variable
        substract(10,15)
        add(10,15)

        val result = substract(3,5) //* 5
        val otherResult = add(3,5) * 5
        println(result) //returns kotlin.Unit
        println(otherResult)

        val benimString = "bircan"
        val superman = SuperKahraman("Clark Kent",30,"Journalist")
        //superman.age = 30
        //superman.name = "Clark Kent"
        //superman.job = "Journalist"
        println(superman.name + superman.age + superman.job)
        val batman = SuperKahraman("Bruce Wayne",35,"Rich")
        //batman.age = 35
        //batman.name = "Bruce Wayne"
        //batman.job = "Rich"  //ctor
        println(batman.name + batman.age + batman.job) //-> override toString()

        //Nullability
        val input = "20"  //try with "bircan" NumberFormatException //(always come with str)
        val IntInput = input.toIntOrNull()
        if(IntInput != null){
            println(IntInput * 2)
        }


        var benimDouble : Double? = null
        val userInputDouble = input.toDoubleOrNull()
        // !!
        userInputDouble!!.div(2)

        // ?
        userInputDouble?.div(2)

        if(userInputDouble != null){
            println(userInputDouble / 2)
        }
        //elvis operator
        println(userInputDouble?.div(2) ?: 20)

        userInputDouble?.let {
            println(it * 2)
        }
        benimKahraman = SuperKahraman("Atıl",35,"Yazılım")




    }
    fun firstFunction(){
        count++
        println("firstFuction called this time : ${count}")
        println("firstFunction() called")
        var x = 20
    }
    fun secondFunction(){
        count *= 2
        println("secondFunction() called")
    }
    fun substract(a:Int,b:Int){
        println("Result : ${a-b}") //Args Function  (void type -> Unit)
    }
    fun add(a:Int,b:Int) : Int {
        return a + b //return Type (Int,Bool,Float vs..) Function
    }
}