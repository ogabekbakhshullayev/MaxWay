package uz.gita.maxwayappclone.data.util

import android.view.View
import uz.gita.maxwayappclone.R

fun View.setFocusListener(focus: Boolean) {
    if (focus) {
        this.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                this.setBackgroundResource(R.drawable.input_ed_bcg_selected)
            } else {
                this.setBackgroundResource(R.drawable.input_ed_bcg_unselected)
            }
        }
    }
}