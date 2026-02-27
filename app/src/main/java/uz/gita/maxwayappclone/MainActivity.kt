package uz.gita.maxwayappclone

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import uz.gita.maxwayappclone.databinding.ActivityMainBinding
import uz.gita.maxwayappclone.presentation.adapter.ViewPagerAdapter
import androidx.core.view.get
import uz.gita.maxwayappclone.presentation.screens.branches.BranchesFragment

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val adapter = ViewPagerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.viewPager) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
     //   supportFragmentManager.beginTransaction().replace(R.id.main, BranchesFragment()).commit()
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
