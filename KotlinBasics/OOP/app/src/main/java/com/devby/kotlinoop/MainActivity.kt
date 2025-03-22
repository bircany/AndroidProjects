package com.devby.kotlinoop

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Encapsulation - Visibility Modifiers {private,public(default),protected,internal}
        val atil = Sanatci("Atil",80,"Gitar")
        println(atil.isim)
        atil.isim = "Atıl Samancioglu"
        println(atil.isim)
        atil.sing()
        val zeynep = Sanatci("Zeynep",20,"Bateri")
        zeynep.sing()
        atil.hairColor = "Sarışın"
        atil.printGenre()
        //atil.genre = "SDFSADF" ERROR
        println(zeynep.yas)
        //zeynep.yas  = "40" ERROR
        //zeynep.enstruman = "BATER" ERROR
        //atil.gozRengi = "Yesil" ERROR
        println(atil.gozRengi)
        atil.setHairColorParolali("kahverengi","atil")

        //Inheritance
        val hero = Hero("superman","uçmak")
        hero.run()
        //hero.superFunc()

        val superHero = SuperHero("batman","zengin olmak")
        superHero.run()
        println(superHero.isim)
        superHero.superFunc()


        //Polymorphism
        //STATİK Polymorphism (overloading)
        val islemler = Islemler()
        println(islemler.cikarma(10,2))
        println(islemler.cikarma(10,2,3))
        println(islemler.cikarma(10,2,3,2))

        //Dinamik Polymorphism
        val kedi = Hayvan()
        val kopek = Kopek()
        val ornekDizi = arrayOf(kedi,kopek)
        ornekDizi.forEach { it.sesCikar() }


        //Abstraction
        //Abstract Class,Interface
        //var insan = Insan() Abstract sınıflardan nesne üretilemez.
        atil.test()

    }
}