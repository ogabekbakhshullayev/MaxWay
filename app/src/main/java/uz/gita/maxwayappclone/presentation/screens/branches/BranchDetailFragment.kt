package uz.gita.maxwayappclone.presentation.screens.branches

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenBranchDetailBinding
import uz.gita.maxwayappclone.domain.model.Branch
import uz.gita.maxwayappclone.presentation.util.showGoogleMapsSheet

class BranchDetailFragment : Fragment(R.layout.screen_branch_detail) {

    private val binding by viewBinding(ScreenBranchDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = requireArguments().getString(ARG_NAME).orEmpty()
        val phone = requireArguments().getString(ARG_PHONE).orEmpty()
        val openTime = requireArguments().getString(ARG_OPEN_TIME).orEmpty()
        val closeTime = requireArguments().getString(ARG_CLOSE_TIME).orEmpty()
        val lat = requireArguments().getDouble(ARG_LAT)
        val lng = requireArguments().getDouble(ARG_LNG)

        binding.tvName.text = name
        binding.tvWorkValue.text = formatTimeRange(openTime, closeTime, useTilde = true)
        binding.tvDeliveryValue.text = formatTimeRange(openTime, closeTime, useTilde = false)
        binding.tvPhoneValue.text = phone
        binding.tvParkingValue.text = "Yo'q"
        binding.tvSeatsValue.text = "0"

        binding.btnRoute.setOnClickListener {
            showGoogleMapsSheet(lat, lng)
        }

        binding.ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun formatTimeRange(open: String, close: String, useTilde: Boolean): String {
        val cleanOpen = open.replace(" ", "")
        val cleanClose = close.replace(" ", "")
        return if (useTilde) {
            "$cleanOpen ~ $cleanClose"
        } else {
            "$cleanOpen-$cleanClose"
        }
    }

    companion object {
        private const val ARG_NAME = "arg_name"
        private const val ARG_PHONE = "arg_phone"
        private const val ARG_OPEN_TIME = "arg_open_time"
        private const val ARG_CLOSE_TIME = "arg_close_time"
        private const val ARG_LAT = "arg_lat"
        private const val ARG_LNG = "arg_lng"

        fun newInstance(branch: Branch): BranchDetailFragment {
            return BranchDetailFragment().apply {
                arguments = bundleOf(
                    ARG_NAME to branch.name,
                    ARG_PHONE to branch.phone,
                    ARG_OPEN_TIME to branch.openTime,
                    ARG_CLOSE_TIME to branch.closeTime,
                    ARG_LAT to branch.latitude,
                    ARG_LNG to branch.longitude
                )
            }
        }
    }
}
