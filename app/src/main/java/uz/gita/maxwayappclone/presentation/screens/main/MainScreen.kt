package uz.gita.maxwayappclone.presentation.screens.main

import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenMainBinding
import uz.gita.maxwayappclone.presentation.adapter.ViewPagerAdapter
import uz.gita.maxwayappclone.presentation.screens.basket.BasketBottomSheetDialog

class MainScreen: Fragment(R.layout.screen_main) {

    val binding by viewBinding ( ScreenMainBinding::bind )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (binding.viewPager.adapter == null) {
            binding.viewPager.adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        }
        binding.viewPager.isUserInputEnabled = false

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> binding.viewPager.currentItem = 0
                R.id.basket -> {
                    BasketBottomSheetDialog().show(childFragmentManager, BasketBottomSheetDialog::class.simpleName)
                }
                R.id.orders -> binding.viewPager.currentItem = 1
                R.id.profile -> binding.viewPager.currentItem = 2
            }
            true
        }

        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.bottomNavigation.menu[position].isChecked = true
                }
            }
        )

        binding.bottomNavigation.itemIconTintList = null
    }
}


