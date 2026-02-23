package uz.gita.maxwayappclone.presentation.screens.noConnection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenNoConnectionBinding

class NoConnectionScreen : Fragment(R.layout.screen_no_connection) {
private val binding by viewBinding(ScreenNoConnectionBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.continueBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}