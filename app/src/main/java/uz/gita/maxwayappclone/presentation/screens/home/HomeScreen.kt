package uz.gita.maxwayappclone.presentation.screens.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenHomeBinding
import uz.gita.maxwayappclone.domain.model.Category
import uz.gita.maxwayappclone.domain.model.Product
import uz.gita.maxwayappclone.presentation.screens.productInfo.ProductInfoScreen
import uz.gita.maxwayappclone.presentation.screens.storiesScreen.StoriesScreen

class HomeScreen : Fragment(R.layout.screen_home) {

    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels { HomeViewModelFactory() }

    private val filterAdapter = FilterChipAdapter { category ->
        scrollToCategory(category)
    }
    private val storyAdapter = StoryAdapter {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main, StoriesScreen())
            .addToBackStack(null)
            .commit()
    }
    private val adsPagerAdapter = AdsPagerAdapter()
    private val sectionAdapter = HomeSectionAdapter(
        onProductClick = { product ->
            openProductDetail(product)
        },
        onCountChange = { product, newCount ->
            viewModel.setProductCount(product.id, newCount)
        }
    )

    private var categories: List<Category> = emptyList()
    private var products: List<Product> = emptyList()
    private var selectedCategoryId: Long? = null
    private var isProgrammaticScroll = false
    private var pendingCategoryId: Long? = null

    private val adsHandler = Handler(Looper.getMainLooper())
    private val adsRunnable = object : Runnable {
        override fun run() {
            val count = adsPagerAdapter.itemCount
            if (count > 1) {
                val next = (binding.vpAds.currentItem + 1) % count
                binding.vpAds.setCurrentItem(next, true)
            }
            adsHandler.postDelayed(this, 30_000)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvStories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvStories.adapter = storyAdapter

        binding.rvFilters.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFilters.adapter = filterAdapter
        binding.rvFilters.setOnTouchListener { v, event ->
            when (event.actionMasked) {
                android.view.MotionEvent.ACTION_DOWN,
                android.view.MotionEvent.ACTION_MOVE -> v.parent?.requestDisallowInterceptTouchEvent(
                    true
                )

                android.view.MotionEvent.ACTION_UP,
                android.view.MotionEvent.ACTION_CANCEL -> v.parent?.requestDisallowInterceptTouchEvent(
                    false
                )
            }
            false
        }

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (sectionAdapter.getItemViewType(position) == 0) 2 else 1
            }
        }
        binding.rvProducts.layoutManager = gridLayoutManager
        binding.rvProducts.adapter = sectionAdapter
        binding.rvProducts.itemAnimator = null
        binding.rvProducts.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                if (isProgrammaticScroll) return
                val lm = recyclerView.layoutManager as GridLayoutManager
                val first = lm.findFirstVisibleItemPosition()
                if (first != RecyclerView.NO_POSITION) {
                    val catId = sectionAdapter.getCategoryForPosition(first)
                    if (catId != null && catId != selectedCategoryId) {
                        selectedCategoryId = catId
                        filterAdapter.submitList(categories, selectedCategoryId)
                    }
                }
            }

            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && isProgrammaticScroll) {
                    isProgrammaticScroll = false
                    pendingCategoryId?.let { id ->
                        selectedCategoryId = id
                        filterAdapter.submitList(categories, selectedCategoryId)
                    }
                    pendingCategoryId = null
                }
            }
        })

        binding.vpAds.adapter = adsPagerAdapter
        binding.vpAds.offscreenPageLimit = 3
        binding.vpAds.getChildAt(0)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        viewModel.adsLiveData.observe(viewLifecycleOwner) { ads ->
            adsPagerAdapter.submitList(ads)
        }
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { list ->
            categories = list
            if (selectedCategoryId == null) {
                selectedCategoryId = list.firstOrNull()?.id
            }
            filterAdapter.submitList(list, selectedCategoryId)
            updateSections()
        }
        viewModel.productsLiveData.observe(viewLifecycleOwner) { list ->
            products = list
            updateSections()
            binding.tvEmpty.isVisible = list.isEmpty()
        }
        viewModel.productCountsLiveData.observe(viewLifecycleOwner) { counts ->
            sectionAdapter.updateCounts(counts)
        }
        viewModel.storiesLiveData.observe(viewLifecycleOwner) { list ->
            storyAdapter.submitList(list)
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loadHome()
    }

    private fun scrollToCategory(category: Category) {
        selectedCategoryId = category.id
        filterAdapter.submitList(categories, selectedCategoryId)
        val pos = sectionAdapter.getPositionForCategory(category.id)
        if (pos >= 0) {
            isProgrammaticScroll = true
            pendingCategoryId = category.id
            binding.rvProducts.stopScroll()
            binding.rvProducts.post {
                val lm = binding.rvProducts.layoutManager as? GridLayoutManager
                if (lm != null) {
                    lm.scrollToPositionWithOffset(pos, 0)
                } else {
                    binding.rvProducts.scrollToPosition(pos)
                }
                binding.rvProducts.postDelayed({
                    isProgrammaticScroll = false
                    pendingCategoryId = null
                }, 200)
            }
        }
    }

    private fun updateSections() {
        val items = ArrayList<HomeItem>()
        categories.forEach { category ->
            items.add(HomeItem.Header(category.id, category.name))
            products.filter { it.categoryId == category.id }
                .forEach { items.add(HomeItem.ProductItem(it)) }
        }
        sectionAdapter.submitList(items)
    }

    private fun openProductDetail(product: Product) {
        parentFragmentManager.beginTransaction().replace(
            R.id.main,
            ProductInfoScreen::class.java,
            bundleOf(
                "arg_id" to product.id,
                "arg_name" to product.name,
                "arg_desc" to product.description,
                "arg_image" to product.image,
                "arg_cost" to product.cost
            ),
            "tag"
        ).addToBackStack(null).commit()
    }


    override fun onResume() {
        super.onResume()
        adsHandler.postDelayed(adsRunnable, 30_000)
    }

    override fun onPause() {
        super.onPause()
        adsHandler.removeCallbacks(adsRunnable)
    }
}
