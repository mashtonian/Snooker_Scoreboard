package com.mashton.android.snookerscoreboard.screens.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mashton.android.snookerscoreboard.MatchViewModel
import com.mashton.android.snookerscoreboard.R
import com.mashton.android.snookerscoreboard.databinding.FinishedFragmentBinding

class FinishedFragment : Fragment() {

    private lateinit var binding : FinishedFragmentBinding
    private val viewModel : MatchViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                            savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.finished_fragment,
            container,false)

        binding.textView.text = viewModel.match.winner?.name

        return binding.root
    }

}