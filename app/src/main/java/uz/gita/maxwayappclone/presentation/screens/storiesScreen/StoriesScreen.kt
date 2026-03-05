package uz.gita.maxwayappclone.presentation.screens.storiesScreen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.source.remote.response.StoryData
import uz.gita.maxwayappclone.databinding.ScreenStoriesBinding
import uz.gita.maxwayappclone.presentation.adapter.StoriesVPAdapter

class StoriesScreen : Fragment(R.layout.screen_stories) {
    private val binding by viewBinding(ScreenStoriesBinding::bind)
    private val viewModel by viewModels<StoriesViewModelImpl> { StoriesViewModelFactory() }
    private lateinit var arrayL: Array<StoryData>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getStories()
        viewModel.storiesLiveData.observe(viewLifecycleOwner, Observer<Array<StoryData>> {
            Log.d("TTT", "observe")
            arrayL = it
            binding.vp.adapter = StoriesVPAdapter(arrayL, parentFragmentManager, lifecycle)
            binding.vp.registerOnPageChangeCallback(callBack)
            binding.vp.currentItem = arguments?.getInt("POS", 0) ?: 0
        })

        viewModel.timerLiveData.observe(viewLifecycleOwner) {
            if (it == 30) {
                binding.progressBar.progress = 100
                if (binding.vp.currentItem == arrayL.size - 1) {
                    Log.d("TTT", "popBackStack")
                    findNavController().popBackStack()
                } else {
                    binding.vp.currentItem++
                }
            } else {
                val progress = it * 3
                binding.progressBar.progress = progress
            }
        }

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
            Log.d("DDD", "position = $position")
            Log.d("DDD", "positionOffset = $positionOffset")
            Log.d("DDD", "positionOffsetPixels = $positionOffsetPixels")
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewModel.endTimer()
            viewModel.startTimer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.endTimer()
    }
}