package com.devby.fragmentvenavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.devby.fragmentvenavigation.databinding.FragmentBirinciBinding

class BirinciFragment : Fragment() {
    private var _binding:FragmentBirinciBinding? = null
    private val binding get() =_binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBirinciBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editText.setText("")
        binding.button.setOnClickListener {
            sonraki(it)
        }
        Toast.makeText(requireContext(),"Hoşgeldiniz",Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun sonraki(view: View){
        val name = binding.editText.text.toString()
        val action = BirinciFragmentDirections.actionBirinciFragmentToIkinciFragment(name)
        Navigation.findNavController(view).navigate(action)

    }
}