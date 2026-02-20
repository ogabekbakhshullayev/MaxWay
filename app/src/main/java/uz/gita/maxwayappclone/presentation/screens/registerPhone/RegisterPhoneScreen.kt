package uz.gita.maxwayappclone.presentation.screens.registerPhone

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import timber.log.Timber
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenRegisterPhoneBinding

class RegisterPhoneScreen : Fragment(R.layout.screen_register_phone) {

    private val binding by viewBinding(ScreenRegisterPhoneBinding::bind)
    private val viewModel by viewModels<RegisterPhoneViewModelImpl> { RegisterPhoneViewModelFactory() }

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

        binding.backBtn.setOnClickListener {
            // back btn click
        }
        binding.edPhone.addTextChangedListener(textChangeListener)
        binding.edPhone.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.edPhone.setBackgroundResource(R.drawable.input_ed_bcg_selected)
            } else {
                binding.edPhone.setBackgroundResource(R.drawable.input_ed_bcg_unselected)
            }
        }

        binding.continueBtn.setOnClickListener {
            viewModel.register(binding.edPhone.text.toString())
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)
    }

    private val successObserver = Observer<String> {
        Log.d("TTT","Code: $it")
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.action_registerPhoneScreen_to_registerVerifyScreen,
            bundleOf("phone" to binding.edPhone.text.toString())
        )
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
    val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
        ) {}

        override fun onTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
        ) {
            val phone = p0.toString()
            if (phone.length == 13 && phone[0] == '+' && phone[1] == '9' && phone[2] == '9' && phone[3] == '8' && phone.substring(
                    4,
                    phone.length
                ).toLongOrNull() != null
            ) {
                binding.continueBtn.isEnabled = true
                binding.continueBtn.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            } else {
                binding.continueBtn.isEnabled = false
                binding.continueBtn.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
            }

        }

    }

}