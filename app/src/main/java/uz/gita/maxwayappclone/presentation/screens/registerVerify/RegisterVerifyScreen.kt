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
import timber.log.Timber
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.util.setFocusListener
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

        binding.timerTv.setOnClickListener {
            binding.timerTv.isClickable = false
            binding.timerTv.isFocusable = false
            timer.cancel()
            timer.start()
            viewModel.repeat(arguments?.getString("phone", "") ?: "")
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
        Log.d("TTT","Success: $it")
        code = it
        if (code.length > 4) {
            timer.cancel()
            findNavController().navigate(
                R.id.action_registerVerifyScreen_to_registerNameScreen,
                bundleOf("token" to it)
            )
        }else{
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            binding.ed1.setText("")
            binding.ed1.setBackgroundResource(R.drawable.input_ed_bcg_unselected)
            binding.ed2.setText("")
            binding.ed2.setBackgroundResource(R.drawable.input_ed_bcg_unselected)
            binding.ed3.setText("")
            binding.ed3.setBackgroundResource(R.drawable.input_ed_bcg_unselected)
            binding.ed4.setText("")
            binding.ed4.setBackgroundResource(R.drawable.input_ed_bcg_unselected)
        }
    }
    private val errorMessageObserver = Observer<String> {
        val sb = StringBuilder()
        sb.append(binding.ed1.text.toString())
        sb.append(binding.ed2.text.toString())
        sb.append(binding.ed3.text.toString())
        sb.append(binding.ed4.text.toString())
        if (sb.toString() != code) {
            binding.ed1.setBackgroundResource(R.drawable.input_ed_bcg_wrong)
            binding.ed2.setBackgroundResource(R.drawable.input_ed_bcg_wrong)
            binding.ed3.setBackgroundResource(R.drawable.input_ed_bcg_wrong)
            binding.ed4.setBackgroundResource(R.drawable.input_ed_bcg_wrong)
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