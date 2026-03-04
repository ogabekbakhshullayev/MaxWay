package uz.gita.maxwayappclone.presentation.screens.registerVerify

import android.os.Bundle
import android.os.CountDownTimer
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
import uz.gita.maxwayappclone.databinding.ScreenRegisterVerifyBinding

class RegisterVerifyScreen : Fragment(R.layout.screen_register_verify) {
    private val binding by viewBinding(ScreenRegisterVerifyBinding::bind)
    private val viewModel by viewModels<RegisterVerifyViewModelImpl> { RegisterVerifyViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.successLiveData.observe(this, successObserver)
        viewModel.errorMessageLiveData.observe(this, errorMessageObserver)
    }

    private var code = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            v.updatePadding(bottom = imeHeight + systemInsets)
            insets
        }

        binding.backBtn.setOnClickListener {
            timer.cancel()
            findNavController().popBackStack()
        }
        timer.start()

        binding.pinview.addTextChangedListener(pinTextChangeListener)

        binding.continueBtn.setOnClickListener {
            viewModel.verify(
                arguments?.getString("phone", "") ?: "",
                binding.pinview.text.toString().toInt()
            )
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)

        viewModel.noConnectionLiveData.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                timer.cancel()
                findNavController().navigate(R.id.action_registerVerifyScreen2_to_noConnectionScreen2)
                viewModel.noConnectionLiveData.value = false
            }
        })

        binding.timerTv.setOnClickListener {
            binding.timerTv.isClickable = false
            binding.timerTv.isFocusable = false
            timer.cancel()
            timer.start()
            viewModel.repeat(arguments?.getString("phone", "") ?: "")
            binding.pinview.setText("")
        }
    }

    private val timer = object : CountDownTimer(59000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val str = getString(R.string.use_time)
            binding.timerTv.text = str + millisUntilFinished / 1000
            binding.timerTv.isClickable = false
            binding.timerTv.isFocusable = false
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
            binding.timerTv.isFocusable = true
        }
    }


    private val successObserver = Observer<String> {
        Log.d("TTT", "Success: $it")
        code = it
        if (code.length > 4) {
            timer.cancel()
            findNavController().navigate(
                R.id.action_registerVerifyScreen2_to_registerNameScreen2,
                bundleOf("token" to it)
            )
        } else {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            binding.wrongCodeTv.visibility = View.INVISIBLE
        }
    }
    private val errorMessageObserver = Observer<String> {
        if (binding.pinview.text.toString() != code) {
            binding.wrongCodeTv.visibility = View.VISIBLE
            binding.continueBtn.isEnabled = false
            binding.continueBtn.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
        }
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
    private val loadingObserver = Observer<Boolean> {
        if (it) {
            binding.pb.visibility = View.VISIBLE
        } else {
            binding.pb.visibility = View.GONE
        }
    }

    val pinTextChangeListener = object : TextWatcher {
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
            if (binding.pinview.text.toString().length == 4) {
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