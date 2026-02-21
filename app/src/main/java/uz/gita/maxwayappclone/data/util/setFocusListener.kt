package uz.gita.maxwayappclone.data.util

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.View
import android.widget.ImageView
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

fun ImageView.applyBlurEffect(radius: Float) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        this.setRenderEffect(
            RenderEffect.createBlurEffect(
                radius,
                radius,
                Shader.TileMode.CLAMP
            )
        )
    }
}