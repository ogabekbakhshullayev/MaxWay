package uz.gita.maxwayappclone.presentation.screens.profile_dialog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.source.local.TokenManager
import uz.gita.maxwayappclone.databinding.DialogBottomEditProfileInfoBinding
import uz.gita.maxwayappclone.presentation.dialogs.DatePickerBottomSheet

class EditProfileBottomSheet: BottomSheetDialogFragment(R.layout.dialog_bottom_edit_profile_info) {

    private val viewModel: EditProfileViewModel by viewModels<EditProfileViewModelImpl> { EditProfileViewModelFactory() }

    private val binding by viewBinding(DialogBottomEditProfileInfoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isSuccess.observe(viewLifecycleOwner){
            binding.progress.isVisible = it
        }

        arguments.let { bundle ->
            val name = bundle?.getString("NAME") ?: ""
            val phone = bundle?.getString("PHONE") ?: ""
            val birth = bundle?.getString("BIRTH") ?: ""
            binding.editBirth.setText(birth)
            Toast.makeText(requireContext(), birth, Toast.LENGTH_SHORT).show()
            binding.editName.setText(name)
            binding.editPhone.text = phone
        }

        binding.editBirth.setOnClickListener {
            dateOnClick()
        }

        binding.buttonSave.setOnClickListener {
            binding.progress.visibility = View.VISIBLE



            val name = binding.editName.text.toString()
            val birth = binding.editBirth.text.toString()
            Toast.makeText(requireContext(), birth, Toast.LENGTH_SHORT).show()
            viewModel.updateProfileInfo(TokenManager.token,name,birth)
            viewModel.isSuccess.observe(viewLifecycleOwner){
                if (it) {
//                    val  result = Bundle().apply {
//                        putString("name",binding.editName.text.toString())
//                    }
//                    parentFragmentManager.setFragmentResult(
//                        "edit_profile_result",result
//                    )
                    findNavController().previousBackStackEntry
                        ?.savedStateHandle
                        ?.apply {
                            set("name",binding.editName.text.toString())
                            set("date",binding.editBirth.text.toString())
                            set("phone",binding.editPhone.text.toString())

                        }


                    dismiss()
                    findNavController().popBackStack()

                }
            }




        }
    }
    private fun dateOnClick() {
        val datePickerBottomSheet = DatePickerBottomSheet()

        datePickerBottomSheet.onDateSelectedListener = { day, month, year ->
            val selectedDate = "$day.$month.$year"
                binding.editBirth.setText(selectedDate)
        }
        datePickerBottomSheet.show(parentFragmentManager, datePickerBottomSheet.tag)

    }

}