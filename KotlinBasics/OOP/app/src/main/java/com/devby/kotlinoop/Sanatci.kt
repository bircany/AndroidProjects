package com.devby.kotlinoop

class Sanatci(var isim:String,val yas:Int,val enstruman:String) : Insan(),Dans,Sarki {



    var hairColor = ""
    private var genre = "Insan"

    var gozRengi = ""
        private set
        public get

    fun setHairColorParolali(newHairColor : String,parola:String){
        if(parola == "atıl")
            this.hairColor = newHairColor
        else
            println("parolan yanlış")
    }
    fun getHairColor(){
        println(this.hairColor)
    }

    fun printGenre(){ //GETTER
        println(this.genre)
    }
    fun printEnstruman(){
        println(enstruman)
    }

    fun sing(){
        println("${this.isim} singing.")
    }
    init {
        println("init() called")
    }

    override fun test() {
        //bu abstract sınıftan gelen abstract fonksiyonun override edilmiş hali.
    }

    override fun dansEtmeFunc() {
        TODO("Not yet implemented")
    }

    override fun sarkiSoyleFunc() {
        TODO("Not yet implemented")
    }

}