package uz.gita.maxwayappclone.presentation.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.maxwayappclone.data.source.remote.response.StoryData
import uz.gita.maxwayappclone.presentation.pages.PageStories

class StoriesVPAdapter(private val arrayL:Array<StoryData>, fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt("pos", position)
        val fm = PageStories(arrayL)
        fm.arguments = bundle
        return fm
    }

    override fun getItemCount(): Int = arrayL.size
}
