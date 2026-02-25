package uz.gita.maxwayappclone.presentation.screens.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenNoConnectionBinding

class NoConnectionScreen : Fragment(R.layout.screen_no_connection) {
    private var _binding: ScreenNoConnectionBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ScreenNoConnectionBinding.bind(view)

        binding.continueBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("refresh", true)
            parentFragmentManager.setFragmentResult("key", bundle)
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}