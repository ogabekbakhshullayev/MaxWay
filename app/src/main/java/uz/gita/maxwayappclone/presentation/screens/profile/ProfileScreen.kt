package uz.gita.maxwayappclone.presentation.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenProfileBinding
import uz.gita.maxwayappclone.presentation.screens.branches.BranchesFragment
import uz.gita.maxwayappclone.presentation.screens.notification.NotificationViewModel
import uz.gita.maxwayappclone.presentation.screens.notification.NotificationViewModelFactory
import uz.gita.maxwayappclone.presentation.screens.notification.NotificationViewModelImpl
import kotlin.getValue
import kotlin.properties.Delegates

class ProfileScreen : Fragment(R.layout.screen_profile) {
    private lateinit var name: String
    private lateinit var phone: String
    private lateinit var birth: String
    private lateinit var updateName: String
    private lateinit var updateBirth: String


    private val binding by viewBinding(ScreenProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels<ProfileViewModelImpl> { ProfileViewModelFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getBundle()
        viewModel.updateProfileInfo("39c860c4de5bbbdd87efd68e93d90995",updateName, updateBirth)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProfileInfo("39c860c4de5bbbdd87efd68e93d90995")
        getBundle()
//        viewModel.updateProfileInfo("39c860c4de5bbbdd87efd68e93d90995",updateName, updateBirth)

        observe()
        viewModel.getInfoSuccessLiveData.observe(viewLifecycleOwner) { response ->
            name = response.name
            phone = response.phone
            birth = response.birthDate
        }

        binding.buttonEdit.setOnClickListener {
            val bundle = Bundle().apply {
                putString("name", name)
                putString("phone", phone)
                putString("birth", birth)
            }

            findNavController().navigate(
                R.id.action_profileScreen_to_editProfileBottomSheet,
                bundle
            )
        }

        toasts()

        binding.buttonLogOut.setOnClickListener {
            login(false)
        }
        binding.buttonLogin.setOnClickListener {
            login(true)
        }


    }

    private fun loadView(){
        binding.profileName.text = name
        binding.profilePhone.text = phone
    }

    private fun getBundle() {
        arguments.let { bundle ->
            updateName = bundle?.getString("name") ?: ""
            updateBirth = bundle?.getString("birth") ?: ""
        }
    }

    private fun observe() {
        viewModel.getInfoLoadingLiveData.observe(viewLifecycleOwner) {
            binding.progress.isVisible = it
            if (!it) {
                loadView()
            }
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
            findNavController().navigate(R.id.action_profileScreen_to_notificationFragment)
        }
        binding.buttonBranches.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main, BranchesFragment()).commit()
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