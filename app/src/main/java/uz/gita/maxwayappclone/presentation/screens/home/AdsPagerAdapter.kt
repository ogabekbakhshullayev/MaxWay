package uz.gita.maxwayappclone.presentation.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.maxwayappclone.databinding.ItemAdBannerBinding
import uz.gita.maxwayappclone.domain.model.Ad

class AdsPagerAdapter : RecyclerView.Adapter<AdsPagerAdapter.ViewHolder>() {

    private val items = ArrayList<Ad>()

    fun submitList(list: List<Ad>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAdBannerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(private val binding: ItemAdBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Ad) {
            Glide.with(binding.root.context)
                .load(item.bannerUrl)
                .centerCrop()
                .into(binding.ivBanner)
        }
    }
}
