package uz.gita.maxwayappclone.presentation.dialogs.bottomSheeteDatePick

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.DialogBottomEditProfileInfoBinding
import uz.gita.maxwayappclone.presentation.screens.profile.ProfileViewModel
import uz.gita.maxwayappclone.presentation.screens.profile.ProfileViewModelFactory
import uz.gita.maxwayappclone.presentation.screens.profile.ProfileViewModelImpl
import kotlin.getValue

class EditProfileBottomSheet: BottomSheetDialogFragment(R.layout.dialog_bottom_edit_profile_info) {

    private val viewModel: ProfileViewModel by viewModels<ProfileViewModelImpl> { ProfileViewModelFactory() }

    private val binding by viewBinding(DialogBottomEditProfileInfoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments.let { bundle ->
            val name = bundle?.getString("name") ?: ""
            val phone = bundle?.getString("phone") ?: ""
            val birth = bundle?.getString("birth") ?: ""
            binding.editBirth.setText(birth)
            binding.editName.setText(name)
            binding.editPhone.setText(phone)
        }

        binding.editBirth.setOnClickListener {
            dateOnClick()
        }

        binding.buttonSave.setOnClickListener {
            val name = binding.editName.text.toString()
            val birth = binding.editBirth.text.toString()
            viewModel.updateProfileInfo("39c860c4de5bbbdd87efd68e93d90995",name,birth)

            val bundle = Bundle().apply {
                putString("name", name)
                putString("birth", birth)
            }
            findNavController().navigate(R.id.action_editProfileBottomSheet_to_profileScreen, bundle)
        }

    }

    private fun dateOnClick() {
        val datePickerBottomSheet = DatePickerBottomSheet()

        datePickerBottomSheet.onDateSelectedListener = { day, month, year ->
            val selectedDate = "$day.${month + 1}.$year"
                binding.editBirth.setText(selectedDate)
        }
        datePickerBottomSheet.show(parentFragmentManager, datePickerBottomSheet.tag)

    }

}