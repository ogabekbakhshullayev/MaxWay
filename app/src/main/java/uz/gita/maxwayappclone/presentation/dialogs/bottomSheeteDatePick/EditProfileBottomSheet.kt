package uz.gita.maxwayappclone.presentation.dialogs.bottomSheeteDatePick

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.DialogBottomEditProfileInfoBinding

class EditProfileBottomSheet: BottomSheetDialogFragment(R.layout.dialog_bottom_edit_profile_info) {

    private val binding by  viewBinding ( DialogBottomEditProfileInfoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments.let { bundle ->
            val name = bundle?.getString("name")
            val phone = bundle?.getString("phone")
            val birth = bundle?.getString("birth")
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

            val bundle = Bundle().apply {
                putString("name",name)
                putString("birth",birth)
            }
            findNavController().navigate(R.id.action_editProfileBottomSheet_to_profileScreen,bundle)
        }

    }


    private fun dateOnClick(){

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dataPicker = DatePickerDialog(
            requireContext(),
            {_,y,m,d->
                val selectedDate = "$d.${m+1}.$y"
                binding.editBirth.setText(selectedDate)

            },year,month,day

        ).show()

    }


}