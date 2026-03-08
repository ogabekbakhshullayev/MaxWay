package uz.gita.maxwayappclone.presentation.screens.notification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse
import uz.gita.maxwayappclone.databinding.ScreenNotificationBinding
import uz.gita.maxwayappclone.presentation.adapter.NotificationAdapter

class NotificationScreen : Fragment(R.layout.screen_notification) {

    private val binding by viewBinding(ScreenNotificationBinding::bind)
    private val viewModel: NotificationViewModel by viewModels<NotificationViewModelImpl> { NotificationViewModelFactory() }
    private val adapter by lazy { NotificationAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiActions()
        viewModel.loadingLiveData.observe(viewLifecycleOwner, loadingObserver)
        viewModel.notificationListLiveData.observe(viewLifecycleOwner, notificationListObserver)
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
    }

    private fun uiActions() {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecyclerView.adapter = adapter

        adapter.setOnItemClickListener { item ->
            findNavController().navigate(
                resId = R.id.action_notificationFragment_to_notificationDetailFragment,
                args = bundleOf("NAME" to item.name, "MESSAGE" to item.message, "IMAGE" to item.imgURL)
            )
        }
    }

    private val errorMessageObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val notificationListObserver = Observer<List<NotificationResponse>> {
        adapter.submitList(it)
    }

    private val loadingObserver = Observer<Boolean> {
        binding.progress.isVisible = it
    }
}

