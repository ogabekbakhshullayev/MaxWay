package uz.gita.maxwayappclone.presentation.screens.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.source.local.TokenManager
import uz.gita.maxwayappclone.databinding.ScreenProfileBinding
import uz.gita.maxwayappclone.utils.formatPrice
import kotlin.getValue

class ProfileScreen : Fragment(R.layout.screen_profile) {

    private var isLoggedIn = false
    private val binding by viewBinding(ScreenProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels<ProfileViewModelImpl> { ProfileViewModelFactory() }

    override fun onStart() {
        super.onStart()
        if (isLoggedIn){
            viewModel.getProfileInfo()
            isLoggedIn = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val editeDate = findNavController().currentBackStackEntry
            ?.savedStateHandle

        editeDate?.getLiveData<String>("name")?.observe(viewLifecycleOwner) { viewModel.nameLiveData.value = it }
        editeDate?.getLiveData<String>("date")?.observe(viewLifecycleOwner) { viewModel.dateLiveData.value = it }
        editeDate?.getLiveData<String>("phone")?.observe(viewLifecycleOwner) { binding.profilePhone.text = it }

        viewModel.getInfoSuccessLiveData.observe(viewLifecycleOwner) { response ->
            viewModel.nameLiveData.value = response.name
            binding.profilePhone.text = response.phone
            viewModel.dateLiveData.value = response.birthDate
            viewModel.userResponse = response
        }
        viewModel.getInfoLoadingLiveData.observe(viewLifecycleOwner) { binding.progress.isVisible = it }
        viewModel.getInfoErrorMessageLiveData.observe(viewLifecycleOwner) { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        viewModel.nameLiveData.observe(viewLifecycleOwner){ binding.profileName.text = viewModel.nameLiveData.value }

        uiActions()
    }


    private fun uiActions(){

        binding.buttonLogin.setOnClickListener {
            isLoggedIn = true
            findNavController().navigate(R.id.action_mainScreen_to_registerPhoneScreen2)
        }

        binding.buttonLogOut.setOnClickListener {
            isLoggedIn = true
            TokenManager.token = ""
            login(false)
        }

        binding.balance.text = 0L.formatPrice()

        binding.buttonEdit.setOnClickListener {
            findNavController().navigate(
                resId =  R.id.action_mainScreen_to_editProfileBottomSheet,
                args = bundleOf("NAME" to viewModel.nameLiveData.value,"PHONE" to binding.profilePhone.text.toString(),"BIRTH" to viewModel.dateLiveData.value)
            )
        }

        toasts()

        login(!TokenManager.token.isEmpty())

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }



    private fun login(isLogin: Boolean) {
        binding.logOutContainer.isVisible = !isLogin
        binding.loginInContainer.isVisible = isLogin
        binding.buttonLogOut.isVisible = isLogin


//        if (isLogin) {
//            binding.logOutContainer.visibility = View.GONE
//            binding.loginInContainer.visibility = View.VISIBLE
//            binding.buttonLogOut.visibility = View.VISIBLE
//        } else {
//            binding.logOutContainer.visibility = View.VISIBLE
//            binding.loginInContainer.visibility = View.GONE
//            binding.buttonLogOut.visibility = View.GONE
//            binding.progress.visibility = View.GONE
//
    //
    //
    //        }
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
}


//mkjnuh