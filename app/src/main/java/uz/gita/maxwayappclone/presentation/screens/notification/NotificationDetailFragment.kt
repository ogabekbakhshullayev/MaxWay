package uz.gita.maxwayappclone.presentation.screens.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenNotificationDetailBinding

class NotificationDetailFragment : Fragment(R.layout.screen_notification_detail) {

    private val binding by viewBinding(ScreenNotificationDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            val name = bundle.getString("name")
            val message = bundle.getString("message")
            val image = bundle.getString("image")

            binding.notificationName.text = name
            binding.notificationMassage.text = message

            if (!image.isNullOrEmpty()) {
                Glide.with(binding.root.context)
                    .load(image)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(binding.detailImage)
            } else {
                binding.detailImage.setImageResource(R.drawable.placeholder)
            }
        }
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}