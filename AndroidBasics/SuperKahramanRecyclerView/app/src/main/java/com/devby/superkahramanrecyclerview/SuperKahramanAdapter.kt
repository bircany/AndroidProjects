package com.devby.superkahramanrecyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.devby.superkahramanrecyclerview.databinding.RecyclerRowBinding

class SuperKahramanAdapter(val superKahramanList : ArrayList<SuperKahraman>) : RecyclerView.Adapter<SuperKahramanAdapter.SuperKahramanViewHolder>() {
    class SuperKahramanViewHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperKahramanViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SuperKahramanViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return superKahramanList.size
    }

    override fun onBindViewHolder(holder: SuperKahramanViewHolder, position: Int) {
        holder.binding.textViewRecyclerView.text = superKahramanList[position].isim
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,TanitimActivity::class.java)
            MySingleton.secilenSuperKahraman = superKahramanList[position]
            //intent.putExtra("secilenKahraman",superKahramanList[position])

            holder.itemView.context.startActivity(intent)
        }
    }
}