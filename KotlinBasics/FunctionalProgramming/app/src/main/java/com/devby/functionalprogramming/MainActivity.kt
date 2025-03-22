package com.devby.functionalprogramming

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
        println("---------Lambda-----------") //--> anonymous func.
        yazdigimiYazdir("atil")
        val yazdiigmiYazdirLambda = { verilenString: String -> println(verilenString) }
        yazdiigmiYazdirLambda("yazdir test")
        val carpmaIslemiLambda = { a: Int, b: Int -> a * b }
        val sonuc = carpmaIslemiLambda(3, 4)
        println(sonuc)
        val carpmaIkıncıVersiyon: (Int, Int) -> Int = { a, b -> a * b }
        println(carpmaIkıncıVersiyon(3, 4))

        println("---------High Order Functions----------")
        val benimListem = arrayListOf(1, 3, 5, 7, 9, 11, 13, 15, 16, 19)
        var bosListe = arrayListOf<Int>()
        for (numara in benimListem) {
            if (numara < 10) {
                bosListe.add(numara)
            }
            println(numara)
        }
        /* Filter
        val kucukSayilarListesi = benimListem.filter{num -> num < 10}
        for(numara in kucukSayilarListesi){
            println(numara)
        }
         */

        val kucukSayılarYeniListesi = benimListem.filter { it < 10 }

        //Map
        //val karesiAlinmisSayilar = benimListem.map{num -> num * num}
        val karesiAlinmisSayilar = benimListem.map { it * it }
        for (numara in karesiAlinmisSayilar) {
            println(numara)
        }
        //Map & Filter
        val mapFilterbirArada = benimListem.filter { it < 10 }.map { it * it }
        mapFilterbirArada.forEach { println(it) }


        val sanatci1 = Sanatci("Atıl", 35, "Gitar")
        val sanatci2 = Sanatci("Zeynep", 30, "Bateri")
        val sanatci3 = Sanatci("Atlas", 5, "Piyano")
        val sanatcilar = arrayListOf<Sanatci>(sanatci1, sanatci2, sanatci3)

        val otuzdanBuyukSanatcilarinEnstrumanlari =
            sanatcilar.filter { it.yas > 30 }.map { it.enstruman }
        otuzdanBuyukSanatcilarinEnstrumanlari.forEach { println(it) }

        val otuzikiyasındanKucukSanatcilarınIsımleri =
            sanatcilar.filter { it.yas < 32 }.map { it.isim }
        otuzikiyasındanKucukSanatcilarınIsımleri.forEach { println(it) }


        //Scope Fonksiyonları
        println("---------Scope Functions----------")

        //let
        var benimInteger: Int? = null
        benimInteger = 5
        benimInteger?.let { println(it) }
        val yeniInteger = benimInteger?.let {
            it + 1
        } ?: 0
        println(yeniInteger)

        //also
        sanatcilar.filter { it.yas > 30 }.also {
            it.forEach { println(it.isim) }
        }




    }

    fun yazdigimiYazdir(string: String) {
        println(string)
    }
}