package com.mashton.android.snookerscoreboard.screens.match
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.mashton.android.snookerscoreboard.Event
import com.mashton.android.snookerscoreboard.MatchViewModel
import com.mashton.android.snookerscoreboard.MatchViewModelFactory
import com.mashton.android.snookerscoreboard.R
import com.mashton.android.snookerscoreboard.databinding.MatchFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MatchFragment : Fragment() {

    private lateinit var binding :MatchFragmentBinding
    private val viewModel: MatchViewModel
            by activityViewModels {
                MatchViewModelFactory(
                    bundleArguments.playerOneName,
                    bundleArguments.playerTwoName,
                    bundleArguments.numberOfFrames)
            }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                           savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.match_fragment,
            container,false)

        registerAsEventBusSubscriber()
        setStaticUiItems()
        bindLiveDataUiItems()
        return binding.root
    }

    private fun registerAsEventBusSubscriber() = EventBus.getDefault().register(this)

    private fun bindLiveDataUiItems() {
        viewModel.run {
            shotTicker.onChangeDo { binding.scoreTicker.text = it }
            playerOneScore.onChangeDo { binding.playerOneScore.text = it.toString() }
            playerTwoScore.onChangeDo { binding.playerTwoScore.text = it.toString() }
            playerOneScoreColour.onChangeDo { binding.playerOneScore.setTextColor(it) }
            playerTwoScoreColour.onChangeDo { binding.playerTwoScore.setTextColor(it) }
            playerOneFrameScore.onChangeDo { binding.playerOneFrameScore.text = it.formattedAsFrameScore() }
            playerTwoFrameScore.onChangeDo { binding.playerTwoFrameScore.text = it.formattedAsFrameScore() }
            finished.onChangeDo {if (it == true) navigateToFinishedFragment()}
        }
    }

    private fun navigateToFinishedFragment() {
        val action = MatchFragmentDirections.actionMatchToFinished()
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun setStaticUiItems() {
        binding.playerOneName.text = viewModel.match.playerOne.name
        binding.playerTwoName.text = viewModel.match.playerTwo.name
        binding.numberOfFrames.text = viewModel.match.numberOfFrames.formattedAsFrameScore()
    }

    private val bundleArguments
        get() = MatchFragmentArgs.fromBundle(requireArguments())

    private fun Int.formattedAsFrameScore() = String.format(getString(R.string.frameScoreTemplate), this)

    private fun <T> LiveData<T>.onChangeDo (action: Observer<T>) =
        this.observe(viewLifecycleOwner, action)

    @Subscribe
    fun onKeyEvent(event : Event) = viewModel.processKeyPress(event.keyCode)

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }
}
