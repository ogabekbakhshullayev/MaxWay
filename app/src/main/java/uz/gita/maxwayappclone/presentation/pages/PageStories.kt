package uz.gita.maxwayappclone.presentation.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.source.remote.response.StoryData
import uz.gita.maxwayappclone.data.util.applyBlurEffect
import uz.gita.maxwayappclone.databinding.PageStoryBinding
import uz.gita.maxwayappclone.utils.loadImageWithGlide

class PageStories(private val arrayL: Array<StoryData>) : Fragment(R.layout.page_story) {
    private lateinit var binding: PageStoryBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageStoryBinding.bind(view)
        val pos = arguments?.getInt("pos", 0) ?: 0

        binding.imgBlur.applyBlurEffect(16f)

        binding.imgStory.loadImageWithGlide(arrayL[pos].url)
        binding.imgBlur.loadImageWithGlide(arrayL[pos].url)
        binding.imgAvaStory.loadImageWithGlide(arrayL[pos].url)
        binding.titleStoryTv.text = arrayL[pos].name
    }
}