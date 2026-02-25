package uz.gita.maxwayappclone.presentation.screens.notification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenNotificationBinding
import uz.gita.maxwayappclone.presentation.adapter.NotificationAdapter


class NotificationFragment: Fragment(R.layout.screen_notification) {

    private val binding by viewBinding(ScreenNotificationBinding::bind)

    private val viewModel: NotificationViewModel by viewModels<NotificationViewModelImpl>{ NotificationViewModelFactory() }

    private lateinit var adapter: NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        setupAdapter()
        observe()
        viewModel.getNotificationsList()
    }

    private fun setupAdapter(){
        adapter = NotificationAdapter(requireContext()) { item ->
            val bundle = Bundle().apply {
                putString("name", item.name)
                putString("message", item.message)
                putString("image", item.imgURL)
            }
            findNavController().navigate(
                R.id.action_notificationFragment_to_notificationDetailFragment, bundle
            )
        }
        binding.notificationRecyclerView.adapter = adapter
    }

    private fun observe(){
        viewModel.loadingLiveData.observe(viewLifecycleOwner){
            binding.progress.isVisible = it
        }

        viewModel.notificationListLiveData.observe(viewLifecycleOwner){response->
            adapter.submitList(response )

        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

}