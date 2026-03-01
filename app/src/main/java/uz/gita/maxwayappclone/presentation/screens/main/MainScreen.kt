package uz.gita.maxwayappclone.presentation.screens.main

import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenMainBinding
import uz.gita.maxwayappclone.presentation.adapter.ViewPagerAdapter

class MainScreen: Fragment(R.layout.screen_main) {
	private var _binding: ScreenMainBinding? = null
	val binding get() = _binding!!

	fun navigateToPage(position: Int) {
		binding.viewPager.currentItem = position
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = ScreenMainBinding.bind(view)

		val adapter = ViewPagerAdapter(this)
		binding.viewPager.adapter = adapter

		binding.bottomNavigation.setOnItemSelectedListener {
			when(it.itemId) {
				R.id.home -> binding.viewPager.currentItem = 0
				R.id.basket -> binding.viewPager.currentItem = 1
				R.id.orders -> binding.viewPager.currentItem = 2
				R.id.profile -> binding.viewPager.currentItem = 3
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