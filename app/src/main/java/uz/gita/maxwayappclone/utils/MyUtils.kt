package uz.gita.maxwayappclone.utils

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import uz.gita.maxwayappclone.R

fun Long.formatPrice(): String {
    val raw = this.toString()
    val sb = StringBuilder()
    for (i in raw.indices) {
        sb.append(raw[i])
        val remaining = raw.length - i - 1
        if (remaining > 0 && remaining % 3 == 0) sb.append(' ')
    }
    return "$sb сум"
}

fun AppCompatImageView.loadImageWithGlide(path: String) {
    Glide.with(this.context)
        .load(path)
        .centerCrop()
        .placeholder(R.drawable.img_placeholder)
        .into(this)
}

