package com.mashton.android.snookerscoreboard.screens.match
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.mashton.android.snookerscoreboard.Event
import com.mashton.android.snookerscoreboard.MatchViewModel
import com.mashton.android.snookerscoreboard.MatchViewModelFactory
import com.mashton.android.snookerscoreboard.R
import com.mashton.android.snookerscoreboard.databinding.MatchFragmentBinding
import com.mashton.android.snookerscoreboard.screens.match.MatchFragmentDirections.actionMatchToFinished
import com.mashton.android.snookerscoreboard.screens.match.MatchFragmentDirections.actionMatchToNames
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MatchFragment : Fragment() {

    private lateinit var binding :MatchFragmentBinding
    private val viewModel: MatchViewModel
            by activityViewModels {
                MatchViewModelFactory(
                    bundleArguments.playerOneName,
                    bundleArguments.playerTwoName,
                    bundleArguments.numberOfFrames
                )
            }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.match_fragment,
            container, false
        )


        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (viewModel.match.started) confirmAbandonMatch()
            else {
                abandonMatch()
            }
        }

        registerAsEventBusSubscriber()
        setStaticUiItems()
        bindLiveDataUiItems()
        return binding.root
    }

    private fun abandonMatch() {
        activity?.viewModelStore?.clear()
        actionMatchToNames().perform()
    }

    private fun confirmAbandonMatch() {
        val dialogClickListener =
            DialogInterface.OnClickListener { _, buttonClicked ->
                if (buttonClicked == DialogInterface.BUTTON_POSITIVE) abandonMatch()
            }

        val builder: android.app.AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure you want to abandon this match?").setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
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
            finished.onChangeDo {if (it == true) actionMatchToFinished().perform()}
        }
    }

    private fun NavDirections.perform() =
        NavHostFragment.findNavController(this@MatchFragment).navigate(this)

    private fun setStaticUiItems() {
        binding.playerOneName.text = viewModel.match.playerOne.name
        binding.playerTwoName.text = viewModel.match.playerTwo.name
        binding.numberOfFrames.text = viewModel.match.numberOfFrames.formattedAsFrameScore()
    }

    private val bundleArguments
        get() = MatchFragmentArgs.fromBundle(requireArguments())

    private fun Int.formattedAsFrameScore() = String.format(
        getString(R.string.frameScoreTemplate),
        this
    )

    private fun <T> LiveData<T>.onChangeDo(action: Observer<T>) =
        this.observe(viewLifecycleOwner, action)

    @Subscribe
    fun onKeyEvent(event: Event) = viewModel.processKeyPress(event.keyCode)

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }
}
