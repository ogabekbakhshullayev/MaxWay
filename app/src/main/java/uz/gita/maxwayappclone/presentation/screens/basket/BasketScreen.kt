package uz.gita.maxwayappclone.presentation.screens.basket

import androidx.fragment.app.Fragment
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.presentation.dialogs.BasketBottomSheetDialog

class BasketScreen : Fragment(R.layout.screen_basket) {

    override fun onResume() {
        super.onResume()
        BasketBottomSheetDialog().show(childFragmentManager, BasketBottomSheetDialog::class.simpleName)
    }
}

