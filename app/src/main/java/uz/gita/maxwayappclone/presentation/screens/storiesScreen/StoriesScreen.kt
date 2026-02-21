package uz.gita.maxwayappclone.presentation.screens.storiesScreen

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.source.remote.response.StoryData
import uz.gita.maxwayappclone.databinding.ScreenStoriesBinding
import uz.gita.maxwayappclone.presentation.adapter.Adapter1

class StoriesScreen : Fragment(R.layout.screen_stories) {
    private val binding by viewBinding(ScreenStoriesBinding::bind)
    private val viewModel by viewModels<StoriesViewModelImpl> { StoriesViewModelFactory() }
    private lateinit var arrayL: Array<StoryData>

    val totalTime = 30000L
    val timer = object : CountDownTimer(totalTime, 10) {
        override fun onTick(millisUntilFinished: Long) {
            val progress = ((totalTime - millisUntilFinished) * 100 / totalTime).toInt()
            binding.progressBar.progress = progress
        }

        override fun onFinish() {
            binding.progressBar.progress = 100
            if (binding.vp.currentItem == arrayL.size - 1) {
                Log.d("TTT", "popBackStack")
                findNavController().popBackStack()
            } else {
                binding.vp.currentItem++
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getStories()
        viewModel.storiesLiveData.observe(viewLifecycleOwner, Observer<Array<StoryData>> {
            Log.d("TTT", "observe")
            arrayL = it
            binding.vp.adapter = Adapter1(arrayL, parentFragmentManager, lifecycle)
            binding.vp.registerOnPageChangeCallback(callBack)
        })

        binding.closeBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.rightSide.setOnClickListener {
            if (binding.vp.currentItem != arrayL.size - 1) binding.vp.currentItem++
            else {
                Log.d("TTT", "popBackStack")
                findNavController().popBackStack()
            }
        }
        binding.leftSide.setOnClickListener {
            binding.vp.currentItem--
        }
    }

    private val callBack = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            Log.d("TTT", "state = $state")
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            timer.cancel()
            timer.start()
            Log.d("DDD", "position = $position")
            Log.d("DDD", "positionOffset = $positionOffset")
            Log.d("DDD", "positionOffsetPixels = $positionOffsetPixels")
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }
}