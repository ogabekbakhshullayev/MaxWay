package uz.gita.maxwayappclone.presentation.adapter

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.maxwayappclone.data.source.remote.response.StoryData
import uz.gita.maxwayappclone.presentation.pages.PageT1

class Adapter1(private val arrayL:Array<StoryData>, fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt("pos", position)
        val fm = PageT1(arrayL)
        fm.arguments = bundle
        return fm
    }

    override fun getItemCount(): Int = arrayL.size
}