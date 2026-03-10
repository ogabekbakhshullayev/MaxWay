package uz.gita.maxwayappclone.presentation.screens.basket

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.data.source.local.TokenManager
import uz.gita.maxwayappclone.databinding.BottomSheetBasketBinding
import uz.gita.maxwayappclone.databinding.DialogClearBasketBinding
import uz.gita.maxwayappclone.presentation.adapter.BasketAdapter
import uz.gita.maxwayappclone.presentation.adapter.BasketProductsAdapter
import uz.gita.maxwayappclone.presentation.adapter.RecommendAdapter
import uz.gita.maxwayappclone.utils.formatPrice

class BasketBottomSheetDialog : BottomSheetDialogFragment() {

    private val binding by viewBinding(BottomSheetBasketBinding::bind)
    private val viewModel: BasketViewModel by viewModels<BasketViewModelImpl> { BasketViewModelFactory() }
    private val basketAdapter by lazy { BasketAdapter() }
    private val recommendAdapter by lazy { RecommendAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_basket, container, false)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        parentFragmentManager.setFragmentResult("REFRESH_KEY", Bundle())

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvBasket.layoutManager = LinearLayoutManager(requireContext())
        val adapter = BasketProductsAdapter(arrayOf(0, 0))
        binding.rvBasket.adapter = adapter
        adapter.setBasketAdapter(basketAdapter)
        adapter.setRecommendProductAdapter(recommendAdapter)
//        binding.rvBasket.adapter = basketAdapter

        basketAdapter.setOnChangeProductCountListener {
            viewModel.load()
        }

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        binding.ivTrash.setOnClickListener {
            showClearDialog()
        }
        binding.btnChoose.setOnClickListener {
            dismiss()
        }
        binding.btnPay.setOnClickListener {
            if (TokenManager.token.isEmpty()){
                findNavController().navigate(R.id.registerPhoneScreen2)
            }
            else{
                viewModel.makeOrder()
            }
        }

        viewModel.basketItemsLiveData.observe(viewLifecycleOwner, basketItemsObserver)
        viewModel.totalLiveData.observe(viewLifecycleOwner, totalObserver)
        viewModel.emptyLiveData.observe(viewLifecycleOwner, emptyObserver)
        viewModel.loadingLiveData.observe(viewLifecycleOwner,loadingObserver)
        viewModel.orderSuccessLiveData.observe(viewLifecycleOwner,orderSuccessObserver)
        viewModel.recommendItemsLiveData.observe(viewLifecycleOwner, loadRecommendProducts)
    }

    private val loadRecommendProducts = Observer<List<ProductUIData>> {
        recommendAdapter.submitList(it)
    }

    private val loadingObserver = Observer<Boolean>{binding.progress.isVisible = it}
    private val orderSuccessObserver = Observer<String>{
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_oreder_success)

        val btnGoHome = dialog.findViewById<Button>(R.id.btn_go_home)

        btnGoHome.setOnClickListener {
            dialog.dismiss()
            dismiss()
        }
        dialog.show()

        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        viewModel.clearBasket()
    }
    private val emptyObserver = Observer<Boolean> {
        binding.emptyContainer.isVisible = it
        binding.rvBasket.isVisible = !it
        binding.btnPay.isVisible = !it
    }

    private val totalObserver = Observer<Long> {
        binding.tvPayTotal.text = it.formatPrice()
    }

    private val basketItemsObserver = Observer<List<ProductUIData>> {
        basketAdapter.submitList(it)
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

    private fun showClearDialog() {
        val dialogBinding = DialogClearBasketBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.btnKeep.setOnClickListener { dialog.dismiss() }
        dialogBinding.btnClear.setOnClickListener {
            viewModel.clearBasket()
            dialog.dismiss()
        }
        dialog.show()
    }
}