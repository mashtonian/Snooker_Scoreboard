package com.mashton.android.snookerscoreboard.screens.match
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.mashton.android.snookerscoreboard.Event
import com.mashton.android.snookerscoreboard.R
import com.mashton.android.snookerscoreboard.databinding.FragmentMatchBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MatchFragment : Fragment() {

    private lateinit var playerOneNameEditText: EditText
    private lateinit var playerTwoNameEditText: EditText

    private lateinit var binding :FragmentMatchBinding
    private val viewModel: MatchViewModel
            by viewModels {
                MatchViewModelFactory(
                    getString(R.string.PlayerOneName),
                    getString(R.string.PlayerTwoName))
            }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                           savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_match,
            container,
            false
        )

        EventBus.getDefault().register(this)

        viewModel.apply {
            shotTicker.onChangeDo {newTicker -> binding.scoreTicker.text = newTicker }
            playerOneScore.onChangeDo {newScore -> binding.playerOneScore.text = newScore.toString() }
            playerTwoScore.onChangeDo {newScore -> binding.playerTwoScore.text = newScore.toString() }
            playerOneScoreColour.onChangeDo {colour :Int -> binding.playerOneScore.setTextColor(colour) }
            playerTwoScoreColour.onChangeDo {colour :Int -> binding.playerTwoScore.setTextColor(colour) }
            matchStartedVisibility.onChangeDo {visibility -> binding.changePlayerNamesButton.visibility = visibility }
            playerOneFrameScore.onChangeDo { newScore -> binding.playerOneFrameScore.text = newScore.formattedAsFrameScore() }
            playerTwoFrameScore.onChangeDo { newScore -> binding.playerTwoFrameScore.text = newScore.formattedAsFrameScore() }
        }

        return binding.root
    }

    private fun Int.formattedAsFrameScore() =
        String.format(getString(R.string.frameScoreTemplate), this)

    private fun <T> LiveData<T>.onChangeDo (action: Observer<T>)
    {
        this.observe(viewLifecycleOwner, action)
    }

    @Subscribe
    public fun onKeyEvent(event : Event) {
        viewModel.processKeyPress(event.keyCode)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }
}
