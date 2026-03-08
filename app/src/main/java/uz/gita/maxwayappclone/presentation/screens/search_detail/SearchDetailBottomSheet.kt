package uz.gita.maxwayappclone.presentation.screens.search_detail

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.databinding.BottomSheetProductDetailBinding
import uz.gita.maxwayappclone.databinding.DialogBottomSearchDetailBinding
import uz.gita.maxwayappclone.presentation.screens.productInfo.ProductInfoViewModelFactory
import uz.gita.maxwayappclone.presentation.screens.profile.ProfileViewModel
import uz.gita.maxwayappclone.presentation.screens.profile.ProfileViewModelImpl
import uz.gita.maxwayappclone.presentation.screens.search.SearchViewModel
import uz.gita.maxwayappclone.utils.formatPrice
import uz.gita.maxwayappclone.utils.loadImageWithGlide

class SearchDetailBottomSheet: BottomSheetDialogFragment() {


    companion object {
        fun create(onDismiss: (productId: Long) -> Unit): SearchDetailBottomSheet {
            return  SearchDetailBottomSheet().apply {
                onDismissListener = onDismiss
            }
        }
    }

    private var  onDismissListener: ((productId: Long) -> Unit)? = null
    private var id = -1L
    private val binding by viewBinding (BottomSheetProductDetailBinding::bind)
    private val viewModel by viewModels<SearchDetailViewModelImpl>{ SearchDetailViewModelFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.bottom_sheet_product_detail, container, false)
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val screenHeight = resources.displayMetrics.heightPixels
            val desiredLineHeight = (screenHeight * 0.78).toInt()
            val layoutParams = it.layoutParams
            layoutParams.height = desiredLineHeight
            it.layoutParams = layoutParams
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = requireArguments()

        id = args.getLong("ID")
        val cost = args.getLong("COST")
        var count = args.getInt("COUNT", 1)
        val name = args.getString("NAME")
        val title = args.getString("TITLE")
        val image = args.getString("IMAGE")

        if (count == 0){count = 1}

        binding.tvName.text = name
        binding.tvDesc.text = title
        binding.ivProduct.loadImageWithGlide(image.toString())

        viewModel.countLiveData.observe(viewLifecycleOwner,countObserver)
        viewModel.totalPriceLiveData.observe(viewLifecycleOwner,productPriceObserve)

        viewModel.countLiveData.value = count
        viewModel.setPrice(cost)

        binding.btnPlus.setOnClickListener { viewModel.increase() }
        binding.btnMinus.setOnClickListener { viewModel.decrease() }
        binding.btnAdd.setOnClickListener {
            viewModel.saveCount(id)
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke(id)
    }
    private val countObserver = Observer<Int>{ binding.tvQty.text = it.toString() }
    private val productPriceObserve = Observer<Long>{binding.tvPriceValue.text = it.formatPrice()}


    }
