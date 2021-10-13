package com.example.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.tictactoe.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    lateinit var gameButton: Button
    private var _binding : FragmentStartBinding? = null
    val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStartBinding.inflate(layoutInflater, container, false)
        mBinding.newGameBtn.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_startFragment_to_firstGameFragment))
        return mBinding.root
    }
}