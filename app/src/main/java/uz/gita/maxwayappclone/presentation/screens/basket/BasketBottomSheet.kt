package uz.gita.maxwayappclone.presentation.screens.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.maxwayappclone.MainActivity
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.BottomSheetBasketBinding
import uz.gita.maxwayappclone.databinding.DialogClearBasketBinding

class BasketBottomSheet : BottomSheetDialogFragment() {

    private val binding by viewBinding(BottomSheetBasketBinding::bind)
    private val viewModel: BasketViewModel by viewModels { BasketViewModelFactory() }
    private val adapter = BasketAdapter { product, newCount ->
        viewModel.setProductCount(product.id, newCount)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_basket, container, false)
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
        binding.rvBasket.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBasket.adapter = adapter

        viewModel.basketItemsLiveData.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

        viewModel.totalLiveData.observe(viewLifecycleOwner) { total ->
            binding.tvPayTotal.text = formatPrice(total)
        }

        viewModel.emptyLiveData.observe(viewLifecycleOwner) { isEmpty ->
            binding.emptyContainer.visibility = if (isEmpty) View.VISIBLE else View.GONE
            binding.rvBasket.visibility = if (isEmpty) View.GONE else View.VISIBLE
            binding.btnPay.visibility = if (isEmpty) View.GONE else View.VISIBLE
        }

//        binding.btnChoose.setOnClickListener {
//            (requireActivity() as? MainActivity)?.binding?.viewPager?.currentItem = 0
//            dismiss()
//        }

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        binding.ivTrash.setOnClickListener {
            showClearDialog()
        }

        viewModel.load()
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
        fun newInstance(): BasketBottomSheet = BasketBottomSheet()
    }
}
