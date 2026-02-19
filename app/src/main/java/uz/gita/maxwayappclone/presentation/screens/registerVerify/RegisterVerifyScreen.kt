package uz.gita.maxwayappclone.presentation.screens.registerVerify

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
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
import timber.log.Timber
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenRegisterVerifyBinding

class RegisterVerifyScreen : Fragment(R.layout.screen_register_verify) {
    private val binding by viewBinding(ScreenRegisterVerifyBinding::bind)
    private val viewModel by viewModels<RegisterVerifyViewModelImpl> { RegisterVerifyViewModelFactory() }

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
        binding.ed1.setFocusListener()
        binding.ed2.setFocusListener()
        binding.ed3.setFocusListener()
        binding.ed4.setFocusListener()
        binding.ed4.addTextChangedListener(textChangeListener)

        binding.continueBtn.setOnClickListener {
            val sb = StringBuilder()
            sb.append(binding.ed1.text.toString())
            sb.append(binding.ed2.text.toString())
            sb.append(binding.ed3.text.toString())
            sb.append(binding.ed4.text.toString())
            viewModel.verify(arguments?.getString("phone", "") ?: "", sb.toString().toInt())
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)

        object : CountDownTimer(59000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val str = getString(R.string.use_time)
                binding.timerTv.text = str + millisUntilFinished / 1000
            }

            override fun onFinish() {
                binding.continueBtn.isEnabled = false
                binding.continueBtn.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                val str = getString(R.string.send_again)
                binding.timerTv.text = str
                binding.timerTv.isClickable = true
            }
        }.start()
        binding.timerTv.setOnClickListener {
            // resend code
        }
    }

    private val successObserver = Observer<String> {
        Timber.tag("TTT").d("Success: $it")
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

    private fun View.setFocusListener() {
        this.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                this.setBackgroundResource(R.drawable.input_ed_bcg_selected)
            } else {
                this.setBackgroundResource(R.drawable.input_ed_bcg_unselected)
            }
        }
    }

    val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
        ) {
        }

        override fun onTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
        ) {
            val sb = StringBuilder()
            sb.append(binding.ed1.text.toString())
            sb.append(binding.ed2.text.toString())
            sb.append(binding.ed3.text.toString())
            sb.append(binding.ed4.text.toString())
            if (sb.toString().length == 4) {
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