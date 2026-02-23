package uz.gita.maxwayappclone.presentation.screens.registerName

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.datepicker.MaterialDatePicker
import timber.log.Timber
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.util.setFocusListener
import uz.gita.maxwayappclone.databinding.ScreenRegisterNameBinding
import uz.gita.maxwayappclone.presentation.dialogs.bottomSheeteDatePick.DatePickerBottomSheet

class RegisterNameScreen : Fragment(R.layout.screen_register_name) {
    private val binding by viewBinding(ScreenRegisterNameBinding::bind)
    private val viewModel by viewModels<RegisterNameViewModelImpl> { RegisterNameViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.successLiveData.observe(this, successObserver)
        viewModel.errorMessageLiveData.observe(this, errorMessageObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            v.updatePadding(bottom = imeHeight + systemInsets)
            insets
        }

        binding.edDateOfBirth.setOnClickListener {
            binding.edDateOfBirth.setBackgroundResource(R.drawable.input_ed_bcg_selected)

            val datePickerBottomSheet = DatePickerBottomSheet()

            datePickerBottomSheet.onDateSelectedListener = { day, month, year ->
                val selectedDate = "$year-$month-$day"
                binding.edDateOfBirth.setText(selectedDate)
                if (binding.edName.text.toString().isNotEmpty()) {
                    binding.continueBtn.isEnabled = true
                    binding.continueBtn.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                }
            }
            datePickerBottomSheet.show(parentFragmentManager, datePickerBottomSheet.tag)
        }

        binding.continueBtn.setOnClickListener {
            viewModel.nameDate(
                arguments?.getString("token", "") ?: "",
                binding.edName.text.toString(),
                binding.edDateOfBirth.text.toString()
            )
        }

        viewModel.noConnectionLiveData.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                findNavController().navigate(R.id.action_registerNameScreen_to_noConnectionScreen)
                viewModel.noConnectionLiveData.value = false
            }
        })
        viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)
    }

    private val successObserver = Observer<String> {
        Log.d("TTT", "Success: $it")
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
    private val errorMessageObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
    private val loadingObserver = Observer<Boolean> {
        if (it) {
            binding.pb.visibility = View.VISIBLE
        } else {
            binding.pb.visibility = View.GONE
        }
    }
}