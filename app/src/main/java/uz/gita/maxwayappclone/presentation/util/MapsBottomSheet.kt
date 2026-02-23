package uz.gita.maxwayappclone.presentation.util

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.gita.maxwayappclone.R

fun Fragment.showGoogleMapsSheet(lat: Double, lng: Double) {
    val dialog = BottomSheetDialog(requireContext())
    val view = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_maps, null, false)
    view.findViewById<android.view.View>(R.id.rowGoogleMaps).setOnClickListener {
        openGoogleMapsDirections(lat, lng)
        dialog.dismiss()
    }
    dialog.setContentView(view)
    dialog.show()
}

private fun Fragment.openGoogleMapsDirections(lat: Double, lng: Double) {
    val uri = Uri.parse("google.navigation:q=$lat,$lng")
    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage("com.google.android.apps.maps")
    }
    val pm = requireContext().packageManager
    if (intent.resolveActivity(pm) != null) {
        startActivity(intent)
    } else {
        val webUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$lat,$lng")
        startActivity(Intent(Intent.ACTION_VIEW, webUri))
    }
}
