package uz.gita.maxwayappclone.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.maxwayappclone.presentation.screens.basket.BasketScreen
import uz.gita.maxwayappclone.presentation.screens.home.HomeScreen
import uz.gita.maxwayappclone.presentation.screens.main.MainScreen
import uz.gita.maxwayappclone.presentation.screens.orders.OrdersScreen
import uz.gita.maxwayappclone.presentation.screens.profile.ProfileScreen

class ViewPagerAdapter(frag: Fragment): FragmentStateAdapter(frag) {
	override fun createFragment(position: Int): Fragment {
		return when(position) {
			0 -> HomeScreen()
			1 -> BasketScreen()
			2 -> OrdersScreen()
			else -> ProfileScreen()
		}
	}

	override fun getItemCount(): Int = 4
}