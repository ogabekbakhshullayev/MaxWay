package uz.gita.maxwayappclone.presentation.screens.notification

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.model.NotificationData
import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse
import uz.gita.maxwayappclone.databinding.FragmentNotificationBinding


class NotificationFragment: Fragment(R.layout.fragment_notification) {

    private val binding by viewBinding(FragmentNotificationBinding::bind)

    private val viewModel: NotificationViewModel by viewModels<NotificationViewModelImpl>{ NotificationViewModelFactory() }

    private lateinit var adapter: NotificationAdapter



//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        viewModel.notificationListLiveData.observe(this,notificationObserver)
//        viewModel.errorMessageLiveData.observe(this,)
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        setupAdapter()
        observe()
        viewModel.getNotificationsList()


    }

    private fun setupAdapter(){
        adapter = NotificationAdapter(requireContext()){item ->
            val bundle = Bundle().apply {
                putInt("id",item.id)
                putString("name",item.name)
                putString("message",item.message)
                putString("image",item.imgURL)
            }
            findNavController().navigate(
                R.id.action_notificationFragment_to_notificationDetailFragment,bundle
            )
        }
        binding.notificationRecyclerView.adapter = adapter
    }

    private fun observe(){
        viewModel.loadingLiveData.observe(viewLifecycleOwner){
            binding.progress.isVisible = it
        }

        viewModel.notificationListLiveData.observe(viewLifecycleOwner){response->
            Log.d("TAG_LIVE_DATA", "observe: $response")

            adapter.submitList(response )
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner){
            Log.d("TAG_LIVE_DATA", "observe: $it")
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }



}