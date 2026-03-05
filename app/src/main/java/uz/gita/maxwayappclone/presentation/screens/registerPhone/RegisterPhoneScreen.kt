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
            findNavController().popBackStack()
        }
        binding.edPhone.setText("+998")
        binding.edPhone.setSelection(binding.edPhone.text.length)

        binding.edPhone.addTextChangedListener(textChangeListener)
        binding.edPhone.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.edPhone.setBackgroundResource(R.drawable.input_ed_bcg_selected)
            } else {
                binding.edPhone.setBackgroundResource(R.drawable.input_ed_bcg_unselected)
            }
        }

        binding.continueBtn.setOnClickListener {
            viewModel.register(binding.edPhone.text.toString().deformat())
        }

        viewModel.noConnectionLiveData.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                findNavController().navigate(R.id.noConnectionScreen)
                viewModel.noConnectionLiveData.value = false
            }
        })
        viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)
    }

    private fun String.deformat(): String {
        val sb = StringBuilder()
        sb.append("+")
        for (i in 1..<this.length) {
            if (this[i].isDigit()) sb.append(this[i])
        }
        return sb.toString()
    }

    private val successObserver = Observer<String> {
        Log.d("TTT", "Code: $it")
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

        findNavController().navigate(
            R.id.action_registerPhoneScreen2_to_registerVerifyScreen2,
            bundleOf("phone" to binding.edPhone.text.toString().deformat())
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
        var isFormatting = false
        var cursorPos = 0

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (!isFormatting) {
                cursorPos = binding.edPhone.selectionStart
            }
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val phone = s.toString()
            if (phone.length == 17 && phone[0] == '+' && phone[1] == '9' && phone[2] == '9' && phone[3] == '8') {
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

        override fun afterTextChanged(s: Editable?) {
            if (isFormatting) return
            isFormatting = true

            val digits = s.toString().filter { it.isDigit() }
            val formatted = StringBuilder("+998")

            if (digits.length > 3) {
                formatted.append("(")
                formatted.append(digits.substring(3, minOf(5, digits.length)))
                formatted.append(")")
            }
            if (digits.length > 5) {
                formatted.append(digits.substring(5, minOf(8, digits.length)))
            }
            if (digits.length > 8) {
                formatted.append("-")
                formatted.append(digits.substring(8, minOf(10, digits.length)))
            }
            if (digits.length > 10) {
                formatted.append("-")
                formatted.append(digits.substring(10, minOf(12, digits.length)))
            }

            var newCursorPos = cursorPos
            val oldText = binding.edPhone.text.toString()
            var digitsBeforeCursor = 0
            for (i in 0 until minOf(cursorPos, oldText.length)) {
                if (oldText[i].isDigit()) digitsBeforeCursor++
            }

            var digitsCount = 0
            newCursorPos = formatted.length
            for (i in formatted.indices) {
                if (formatted[i].isDigit()) digitsCount++
                if (digitsCount == digitsBeforeCursor) {
                    newCursorPos = i + 1
                    break
                }
            }

            binding.edPhone.setText(formatted)
            binding.edPhone.setSelection(binding.edPhone.text.toString().length)

            isFormatting = false
        }
    }

}