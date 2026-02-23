package uz.gita.maxwayappclone.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.maxwayappclone.presentation.screens.orders.CurrentOrderFragment
import uz.gita.maxwayappclone.presentation.screens.orders.HistoryOrderFragment

class CategoryOrderAdapter(frag: Fragment): FragmentStateAdapter(frag) {
	override fun createFragment(position: Int): Fragment {
		return when (position) {
			0 -> CurrentOrderFragment()
			else -> HistoryOrderFragment()
		}
	}

	override fun getItemCount(): Int = 2
}