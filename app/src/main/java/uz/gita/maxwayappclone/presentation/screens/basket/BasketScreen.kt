package uz.gita.maxwayappclone.presentation.screens.basket

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.gita.maxwayappclone.R

class BasketScreen : Fragment(R.layout.screen_basket) {

    override fun onResume() {
        super.onResume()
        showSheetIfNeeded()
    }

    private fun showSheetIfNeeded() {
        if (parentFragmentManager.isStateSaved) return
        val existing = parentFragmentManager.findFragmentByTag(TAG_BASKET_SHEET)
            as? com.google.android.material.bottomsheet.BottomSheetDialogFragment
        if (existing == null) {
            BasketBottomSheet.newInstance()
                .show(parentFragmentManager, TAG_BASKET_SHEET)
            return
        }
        if (existing.isAdded && existing.dialog?.isShowing == true) return
        parentFragmentManager.beginTransaction()
            .remove(existing)
            .commitAllowingStateLoss()
        parentFragmentManager.executePendingTransactions()
        BasketBottomSheet.newInstance()
            .show(parentFragmentManager, TAG_BASKET_SHEET)
    }

    companion object {
        private const val TAG_BASKET_SHEET = "basket_sheet"
    }
}
