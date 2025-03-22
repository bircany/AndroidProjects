package com.devby.kotlinoop

class Kopek : Hayvan() {
    fun havla(){
        println("köpek havladı")
        //super.sesCikar()
    }
    override fun sesCikar(){
        println("kopek ses çıkardı")
    }

}