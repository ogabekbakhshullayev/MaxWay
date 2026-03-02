package uz.gita.maxwayappclone.presentation.screens.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.source.local.TokenManager
import uz.gita.maxwayappclone.databinding.ScreenProfileBinding
import kotlin.getValue

class ProfileScreen : Fragment(R.layout.screen_profile) {

    private val binding by viewBinding(ScreenProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels<ProfileViewModelImpl> { ProfileViewModelFactory() }

    var date = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editeDate = findNavController().currentBackStackEntry
            ?.savedStateHandle

                editeDate?.getLiveData<String>("name")?.observe(viewLifecycleOwner){
                    binding.profileName.text = it
                }
        editeDate?.getLiveData<String>("date")?.observe(viewLifecycleOwner){
            date = it
        }
        editeDate?.getLiveData<String>("phone")?.observe(viewLifecycleOwner){
            binding.profilePhone.text = it
        }




//        parentFragmentManager.setFragmentResultListener("edit_profile_result",viewLifecycleOwner){
//            _,bundle->
//            binding.profileName.text = bundle.getString("name")
//        }



        binding.buttonEdit.setOnClickListener {

            val bundle = Bundle().apply {
                if (viewModel.userResponse != null) {
                    putString("name", binding.profileName.text.toString())
                    putString("phone", binding.profilePhone.text.toString())
                    putString("birth", date)
                }
            }
            findNavController().navigate(R.id.action_mainScreen_to_editProfileBottomSheet,bundle)

        }
        toasts()
        binding.buttonLogOut.setOnClickListener {
            TokenManager.token = ""
            login(false)
        }
        binding.buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreen_to_registerPhoneScreen2)
        }
        login(!TokenManager.token.isEmpty())
    }
//    private fun loadView(response: EditProfileResponse){
//        binding.profileName.text = response.name
//        binding.profilePhone.text = response.phone
//    }

    private fun observe() {

        viewModel.getInfoSuccessLiveData.observe(viewLifecycleOwner) { response ->

            binding.profileName.text = response.name
            binding.profilePhone.text = response.phone
            date = response.birthDate
            viewModel.userResponse = response

        }

        viewModel.getInfoLoadingLiveData.observe(viewLifecycleOwner) {
            binding.progress.isVisible = it
        }

        viewModel.getInfoErrorMessageLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun login(isLogin: Boolean) {
        if (isLogin) {
            binding.logOutContainer.visibility = View.GONE
            binding.loginInContainer.visibility = View.VISIBLE
            binding.buttonLogOut.visibility = View.VISIBLE
        } else {
            binding.logOutContainer.visibility = View.VISIBLE
            binding.loginInContainer.visibility = View.GONE
            binding.buttonLogOut.visibility = View.GONE
        }
    }

    fun toasts() {
        binding.buttonAddress.setOnClickListener {
            Toast.makeText(requireContext(), "address clicked", Toast.LENGTH_SHORT).show()
        }
        binding.buttonStock.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreen_to_notificationFragment)
        }
        binding.buttonBranches.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreen_to_branchesFragment)
        }
        binding.buttonNews.setOnClickListener {
            Toast.makeText(requireContext(), "news clicked", Toast.LENGTH_SHORT).show()
        }
        binding.vacancies.setOnClickListener {
            Toast.makeText(requireContext(), "vacancies clicked", Toast.LENGTH_SHORT).show()
        }
        binding.buttonGallery.setOnClickListener {
            Toast.makeText(requireContext(), "gallery clicked", Toast.LENGTH_SHORT).show()
        }
        binding.buttonRecipes.setOnClickListener {
            Toast.makeText(requireContext(), "recipes clicked", Toast.LENGTH_SHORT).show()
        }
        binding.buttonLanguage.setOnClickListener {
            Toast.makeText(requireContext(), "language clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProfileInfo(TokenManager.token)
        Log.d("TTT", "onResume: $")
        observe()
    }



}