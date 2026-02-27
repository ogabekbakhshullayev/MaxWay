package uz.gita.maxwayappclone.presentation.screens.productInfo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.source.remote.response.ProductByCategoryResponse
import uz.gita.maxwayappclone.data.source.remote.response.ProductResponse
import uz.gita.maxwayappclone.databinding.ScreenInfoProductBinding
import uz.gita.maxwayappclone.presentation.screens.registerName.RegisterNameViewModelFactory
import uz.gita.maxwayappclone.presentation.screens.registerName.RegisterNameViewModelImpl
import kotlin.getValue

class ProductInfoScreen : Fragment(R.layout.screen_info_product) {
    private val binding by viewBinding(ScreenInfoProductBinding::bind)
    private val viewModel by viewModels<ProductInfoViewModelImpl> { ProductInfoViewModelFactory() }
    private var cost = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.successLiveData.observe(this, successObserver)
        viewModel.errorMessageLiveData.observe(this, errorMessageObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allContainer.setPadding(0,34,0,dpToPx(requireContext(),84f))

        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        viewModel.productByCategory(arguments?.getInt("categoryId", 1) ?: 1,arguments?.getInt("id", 1) ?: 1)

        viewModel.countLiveData.observe(viewLifecycleOwner) { qty ->
            binding.countTv.text = qty.toString()
            binding.priceTv.text = formatPrice(cost * qty)
        }

        binding.minusBtn.setOnClickListener {
            viewModel.decrease()
        }

        binding.plusBtn.setOnClickListener {
            viewModel.increase()
        }

        binding.addBtn.setOnClickListener {
            viewModel.applyCount()
            parentFragmentManager.popBackStack()
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)
    }

    private val successObserver = Observer<ProductByCategoryResponse> {
        Log.d("TTT", "Success: $it")
        val argId = arguments?.getInt("id", 3) ?: 1
        var product = it.products[0]
        for (i in 0..<it.products.size) {
            if (it.products[i].id == argId.toLong()) {
                product = it.products[i]
                break
            }
        }

        cost = product.cost

        Glide.with(requireContext())
            .load(product.image)
            .placeholder(R.drawable.img_placeholder)
            .into(binding.imgProduct)
        binding.titleTv.text = product.name
        binding.categoryName.text = it.name
        binding.infoTv.text = product.description
        binding.priceTv.text = "${product.cost} ${getString(R.string.sum)}"
    }
    private val errorMessageObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
    private val loadingObserver = Observer<Boolean> {
        if (it) {
            binding.pb.visibility = View.VISIBLE
        } else {
            binding.pb.visibility = View.GONE
        }
    }

    private fun formatPrice(cost: Long): String {
        val raw = cost.toString()
        val sb = StringBuilder()
        for (i in raw.indices) {
            sb.append(raw[i])
            val remaining = raw.length - i - 1
            if (remaining > 0 && remaining % 3 == 0) sb.append(' ')
        }
        return "${sb} сум"
    }

    fun dpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}