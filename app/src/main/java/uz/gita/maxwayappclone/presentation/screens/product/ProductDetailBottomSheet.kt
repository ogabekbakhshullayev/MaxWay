package uz.gita.maxwayappclone.presentation.screens.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.BottomSheetProductDetailBinding
import uz.gita.maxwayappclone.domain.model.Product

class ProductDetailBottomSheet : BottomSheetDialogFragment() {

    private val binding by viewBinding(BottomSheetProductDetailBinding::bind)
    private val viewModel: ProductDetailViewModel by viewModels { ProductDetailViewModelFactory() }
    private var productId: Long = -1L
    private var baseCost: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_product_detail, container, false)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog as? BottomSheetDialog ?: return
        val sheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            ?: return
        val screenHeight = resources.displayMetrics.heightPixels
        val topGap = (80 * resources.displayMetrics.density).toInt()
        sheet.layoutParams.height = screenHeight - topGap
        sheet.requestLayout()
        val behavior = BottomSheetBehavior.from(sheet)
        sheet.setBackgroundResource(android.R.color.transparent)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.skipCollapsed = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = requireArguments()
        productId = args.getLong(ARG_ID, -1L)
        val name = args.getString(ARG_NAME).orEmpty()
        val desc = args.getString(ARG_DESC).orEmpty()
        val image = args.getString(ARG_IMAGE).orEmpty()
        val cost = args.getLong(ARG_COST, 0L)
        baseCost = cost

        Glide.with(binding.root.context)
            .load(image)
            .centerInside()
            .into(binding.ivProduct)

        binding.tvName.text = name
        binding.tvDesc.text = desc
        binding.tvPriceValue.text = formatPrice(cost)
        binding.tvWeight.visibility = View.GONE

        if (productId != -1L) {
            viewModel.bind(productId)
        }

        viewModel.qtyLiveData.observe(viewLifecycleOwner) { qty ->
            binding.tvQty.text = qty.toString()
            binding.tvPriceValue.text = formatPrice(baseCost * qty)
        }

        binding.btnMinus.setOnClickListener {
            viewModel.decrease()
        }

        binding.btnPlus.setOnClickListener {
            viewModel.increase()
        }

        binding.btnAdd.setOnClickListener {
            viewModel.applyCurrentCount()
            dismiss()
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

    companion object {
        private const val ARG_ID = "arg_id"
        private const val ARG_NAME = "arg_name"
        private const val ARG_DESC = "arg_desc"
        private const val ARG_IMAGE = "arg_image"
        private const val ARG_COST = "arg_cost"

        fun newInstance(product: Product): ProductDetailBottomSheet {
            return ProductDetailBottomSheet().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ID, product.id)
                    putString(ARG_NAME, product.name)
                    putString(ARG_DESC, product.description)
                    putString(ARG_IMAGE, product.image)
                    putLong(ARG_COST, product.cost)
                }
            }
        }
    }
}
