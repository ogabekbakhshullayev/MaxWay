package uz.gita.maxwayappclone.presentation.screens.registerName

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenRegisterNameBinding
import uz.gita.maxwayappclone.presentation.dialogs.DatePickerBottomSheet

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

        binding.infoTv.movementMethod = LinkMovementMethod.getInstance()
        binding.edName.addTextChangedListener(textChangeListener)
        binding.edDateOfBirth.setOnClickListener {
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
                findNavController().navigate(R.id.noConnectionScreen)
                viewModel.noConnectionLiveData.value = false
            }
        })
        viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)
    }

    private val successObserver = Observer<String> {
        findNavController().popBackStack()
    }

    private val errorMessageObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val loadingObserver = Observer<Boolean> {
        binding.pb.isVisible = it
    }

    val textChangeListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            s?.length?.let {
                if (it > 0) {
                    if (binding.edDateOfBirth.text.isNotEmpty()){
                        binding.continueBtn.isEnabled = true
                        binding.continueBtn.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                        return
                    }
                    return
                }
            }
            binding.continueBtn.isEnabled = false
            binding.continueBtn.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text_secondary
                )
            )
        }
    }
}