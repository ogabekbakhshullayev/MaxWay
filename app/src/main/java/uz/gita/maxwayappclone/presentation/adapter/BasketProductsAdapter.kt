package uz.gita.maxwayappclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.databinding.ItemRecyclerViewBinding
import uz.gita.maxwayappclone.presentation.adapter.BasketProductsAdapter.ViewHolder
import uz.gita.maxwayappclone.presentation.screens.home.HomeProductAdapter

class BasketProductsAdapter(private val data: Array<Int>): RecyclerView.Adapter<ViewHolder>() {
    private var basketAdapter: ListAdapter<ProductUIData, BasketAdapter.BasketViewHolder>? = null
    private var recommendProductAdapter: ListAdapter<ProductUIData, RecommendAdapter.ViewHolder>? = null

    fun setBasketAdapter(adapter: ListAdapter<ProductUIData, BasketAdapter.BasketViewHolder>) {
        basketAdapter = adapter
    }

    fun setRecommendProductAdapter(adapter: ListAdapter<ProductUIData, RecommendAdapter.ViewHolder>) {
        recommendProductAdapter = adapter
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) =
        ViewHolder(ItemRecyclerViewBinding.inflate(LayoutInflater.from(p0.context), p0, false))

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) = p0.bind()

    override fun getItemCount(): Int = 2

    inner class ViewHolder(private val binding: ItemRecyclerViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            if (absoluteAdapterPosition == 0) {
                binding.recyclerView.adapter = basketAdapter
                binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context,
                    LinearLayoutManager.VERTICAL, false)
            } else {
                binding.recyclerView.adapter = recommendProductAdapter
                binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context,
                    LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }
}

//class BasketProductsAdapter: ListAdapter<ArrayList<ProductUIData>, ViewHolder>(diff) {
//    private var basketAdapter: ListAdapter<ProductUIData, BasketAdapter.BasketViewHolder>? = null
//    private var recommendProductAdapter: RecyclerView.Adapter<HomeProductAdapter.ViewHolder>? = null
//
//    fun setBasketAdapter(adapter: ListAdapter<ProductUIData, BasketAdapter.BasketViewHolder>) {
//        basketAdapter = adapter
//    }
//
//    fun setRecommendProductAdapter(adapter: ListAdapter<ProductUIData, BasketAdapter.BasketViewHolder>) {
//        basketAdapter = adapter
//    }
//
//    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) =
//        ViewHolder(ItemRecyclerViewBinding.inflate(LayoutInflater.from(p0.context), p0, false))
//
//    override fun onBindViewHolder(p0: ViewHolder, p1: Int) = p0.bind()
//
//    inner class ViewHolder(private val binding: ItemRecyclerViewBinding): RecyclerView.ViewHolder(binding.root) {
//        fun bind() {
//            if (absoluteAdapterPosition == 0) {
//                binding.recyclerView.adapter = basketAdapter
//                binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context,
//                    LinearLayoutManager.VERTICAL, false)
//            } else {
//                binding.recyclerView.adapter = HomeProductAdapter()
//                binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context,
//                    LinearLayoutManager.HORIZONTAL, false)
//            }
//        }
//    }
//
//    companion object {
//        val diff = object: DiffUtil.ItemCallback<ArrayList<ProductUIData>>() {
//            override fun areItemsTheSame(p0: ArrayList<ProductUIData>, p1: ArrayList<ProductUIData>) =
//                p0 == p1
//
//            override fun areContentsTheSame(p0: ArrayList<ProductUIData>, p1: ArrayList<ProductUIData>) =
//                p0 == p1
//        }
//    }
//}