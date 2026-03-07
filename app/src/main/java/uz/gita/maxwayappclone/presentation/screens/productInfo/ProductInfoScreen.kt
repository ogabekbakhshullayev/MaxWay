package uz.gita.maxwayappclone.presentation.screens.productInfo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenInfoProductBinding
import uz.gita.maxwayappclone.utils.loadImageWithGlide

class ProductInfoScreen : Fragment(R.layout.screen_info_product) {
    private val binding by viewBinding(ScreenInfoProductBinding::bind)
    private val viewModel by viewModels<ProductInfoViewModelImpl> { ProductInfoViewModelFactory() }
    private var productId: Long = -1L
    private var baseCost: Long = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.errorMessageLiveData.observe(this, errorMessageObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.allContainer) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, 0,0, systemBars.bottom)
            insets
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.countLiveData.observe(viewLifecycleOwner) { qty ->
            binding.countTv.text = qty.toString()
            binding.priceTv.text = formatPrice(baseCost * qty)
        }

        binding.minusBtn.setOnClickListener {
            viewModel.decrease()
        }

        binding.plusBtn.setOnClickListener {
            viewModel.increase()
        }

        binding.addBtn.setOnClickListener {
            viewModel.applyCount()
            findNavController().popBackStack()
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)

        val args = requireArguments()
        productId = args.getLong("arg_id", -1L)
        val name = args.getString("arg_name").orEmpty()
        val desc = args.getString("arg_desc").orEmpty()
        val image = args.getString("arg_image").orEmpty()
        val cost = args.getLong("arg_cost", 0L)
        val count = args.getInt("arg_count", 1)
        baseCost = cost

        binding.imgProduct.loadImageWithGlide(image)
        binding.titleTv.text = name
        binding.categoryName.text = name
        binding.infoTv.text = desc
        binding.priceTv.text = formatPrice(cost)

        if (productId != -1L) {
            viewModel.bind(productId,count)
        }
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
}