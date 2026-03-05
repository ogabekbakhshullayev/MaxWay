package uz.gita.maxwayappclone.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.maxwayappclone.presentation.screens.home.HomeScreen
import uz.gita.maxwayappclone.presentation.screens.orders.OrdersScreen
import uz.gita.maxwayappclone.presentation.screens.profile.ProfileScreen

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
		return when(position) {
			0 -> HomeScreen()
			1 -> OrdersScreen()
			else -> ProfileScreen()
		}
	}

	override fun getItemCount(): Int = 3
}