package uz.gita.maxwayappclone.presentation.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.app.MyApp
import uz.gita.maxwayappclone.data.source.remote.response.StoryData
import uz.gita.maxwayappclone.data.util.applyBlurEffect
import uz.gita.maxwayappclone.databinding.PageStoryBinding

class PageT1(private val arrayL: Array<StoryData>) : Fragment(R.layout.page_story) {
    private lateinit var binding: PageStoryBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageStoryBinding.bind(view)
        val pos = arguments?.getInt("pos", 0) ?: 0

        binding.imgBlur.applyBlurEffect(16f)

        Glide.with(MyApp.context)
            .load(arrayL[pos].url)
            .into(binding.imgStory)
        Glide.with(MyApp.context)
            .load(arrayL[pos].url)
            .into(binding.imgBlur)
        Glide.with(MyApp.context)
            .load(arrayL[pos].url)
            .into(binding.imgAvaStory)
        binding.titleStoryTv.text = arrayL[pos].name
    }
}