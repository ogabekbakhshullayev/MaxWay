package uz.gita.maxwayappclone

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uz.gita.maxwayappclone.databinding.ActivityMainBinding
import uz.gita.maxwayappclone.presentation.screens.branches.BranchesFragment
import uz.gita.maxwayappclone.databinding.ActivityMainBinding
import uz.gita.maxwayappclone.presentation.screens.basket.BasketScreen
import uz.gita.maxwayappclone.presentation.screens.home.HomeScreen
import uz.gita.maxwayappclone.presentation.screens.orders.OrdersScreen
import uz.gita.maxwayappclone.presentation.screens.profile.ProfileScreen

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var screen = "HOME"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.screens, HomeScreen())
            .replace(R.id.main, BranchesFragment())
            .commit()

        setAction()
    }

    fun setAction() {
        binding.btnHome.setOnClickListener {
            if (screen != "HOME"){
                when (screen) {
                    "BASKET" -> {
                        binding.textBasket.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
                    }
                    "ORDERS" -> {
                        binding.textOrders.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
                        binding.imgOrders.setImageResource(R.drawable.img_orders)
                    }
                    "PROFILE" -> {
                        binding.textProfile.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
                        binding.imgProfile.setImageResource(R.drawable.img_profile)
                    }
                }
                screen = "HOME"
                binding.imgHome.setImageResource(R.drawable.img_home_active)
                binding.textHome.setTextColor(ContextCompat.getColor(this, R.color.black))
                supportFragmentManager.beginTransaction()
                    .replace(R.id.screens, HomeScreen())
                    .commit()
            }
        }
        binding.btnBasket.setOnClickListener {
            if (screen != "BASKET") {
                when (screen) {
                    "HOME" -> { binding.textHome.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
                        binding.imgHome.setImageResource(R.drawable.img_home)
                    }
                    "ORDERS" -> {
                        binding.textOrders.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
                        binding.imgOrders.setImageResource(R.drawable.img_orders)
                    }
                    "PROFILE" -> {
                        binding.textProfile.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
                        binding.imgProfile.setImageResource(R.drawable.img_profile)
                    }
                }
                screen = "BASKET"
                binding.textBasket.setTextColor(ContextCompat.getColor(this, R.color.black))
                supportFragmentManager.beginTransaction()
                    .replace(R.id.screens, BasketScreen())
                    .commit()
            }
        }
        binding.btnOrders.setOnClickListener {
            if (screen != "ORDERS") {
                when (screen) {
                    "HOME" -> { binding.textHome.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
                        binding.imgHome.setImageResource(R.drawable.img_home)
                    }
                    "BASKET" -> {
                        binding.textBasket.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
                    }
                    "PROFILE" -> {
                        binding.textProfile.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
                        binding.imgProfile.setImageResource(R.drawable.img_profile)
                    }
                }
                screen = "ORDERS"
                binding.imgOrders.setImageResource(R.drawable.img_orders_active)
                binding.textOrders.setTextColor(ContextCompat.getColor(this, R.color.black))
                supportFragmentManager.beginTransaction()
                    .replace(R.id.screens, OrdersScreen())
                    .commit()
            }
        }
        binding.btnProfile.setOnClickListener {
            if (screen != "PROFILE") {
                when (screen) {
                    "HOME" -> { binding.textHome.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
                        binding.imgHome.setImageResource(R.drawable.img_home)
                    }
                    "BASKET" -> {
                        binding.textBasket.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
                    }
                    "ORDERS" -> {
                        binding.textOrders.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
                        binding.imgOrders.setImageResource(R.drawable.img_orders)
                    }
                }
                screen = "PROFILE"
                binding.imgProfile.setImageResource(R.drawable.img_profile_active)
                binding.textProfile.setTextColor(ContextCompat.getColor(this, R.color.black))
                supportFragmentManager.beginTransaction()
                    .replace(R.id.screens, ProfileScreen())
                    .commit()
            }
        }
    }
}
