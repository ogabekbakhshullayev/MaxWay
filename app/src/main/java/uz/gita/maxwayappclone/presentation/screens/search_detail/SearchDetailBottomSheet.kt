package uz.gita.maxwayappclone.presentation.screens.search_detail

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
import uz.gita.maxwayappclone.databinding.DialogBottomSearchDetailBinding
import uz.gita.maxwayappclone.utils.formatPrice
import uz.gita.maxwayappclone.utils.loadImageWithGlide

class SearchDetailBottomSheet: BottomSheetDialogFragment() {

    private var id = -1L
    private val binding by viewBinding (DialogBottomSearchDetailBinding::bind)
    private val viewModel: SearchDetailViewModel by viewModels<SearchDetailViewModelImpl>{ SearchDetailViewModelFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_bottom_search_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val id = requireArguments().getLong("ID")
        id = arguments?.getLong("ID")?: -1L
        viewModel.getItem(id)

        viewModel.productLiveData.observe(viewLifecycleOwner, productObserve as Observer<in ProductUIData?>)

        binding.btnPlus.setOnClickListener {
            viewModel.productLiveData.value
        }

    }

    private val productObserve = Observer<ProductUIData>{ product ->
        binding.totalPrice.text = (product.cost * product.count).formatPrice()
        binding.count.text = product.count.toString()
        binding.name.text = product.name
        binding.title.text = product.description
        binding.image.loadImageWithGlide(product.image)
        binding.btnPlus.setOnClickListener {
            product.count++
        }
        binding.btnMinus.setOnClickListener {
            if (product.count > 1){ product.count-- }
        }
    }


}