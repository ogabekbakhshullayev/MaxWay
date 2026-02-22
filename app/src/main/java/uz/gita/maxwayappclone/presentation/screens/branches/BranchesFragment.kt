package uz.gita.maxwayappclone.presentation.screens.branches

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.ScreenBranchesBinding
import uz.gita.maxwayappclone.domain.model.Branch
import uz.gita.maxwayappclone.presentation.util.showGoogleMapsSheet

class BranchesFragment : Fragment(R.layout.screen_branches) {

    private val binding by viewBinding(ScreenBranchesBinding::bind)
    private val viewModel: BranchesViewModel by viewModels { BranchesViewModelFactory() }
    private val adapter = BranchesAdapter { branch ->
        parentFragmentManager.beginTransaction()
            .replace(R.id.main, BranchDetailFragment.newInstance(branch))
            .addToBackStack(null)
            .commit()
    }
    private var googleMap: GoogleMap? = null
    private var branches: List<Branch> = emptyList()
    private var isListTab = true
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private var selectedBranch: Branch? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        setupBottomSheet()
        setupTabs()
        setupMap()
        setupSheetActions()

        viewModel.branchesLiveData.observe(viewLifecycleOwner) {
            branches = it
            adapter.submitList(it)
            val hasData = it.isNotEmpty()
            if (isListTab) {
                binding.recyclerView.isVisible = hasData
                binding.tvEmpty.isVisible = !hasData
                if (!hasData) {
                    binding.tvEmpty.text = "Filiallar topilmadi"
                }
            }
            updateMapMarkers()
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
            if (it) {
                binding.tvEmpty.isVisible = false
            }
        }
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            binding.recyclerView.isVisible = false
            binding.tvEmpty.isVisible = true
            binding.tvEmpty.text = it
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loadBranches()
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.peekHeight = 0
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun setupSheetActions() {
        binding.btnSheetRoute.setOnClickListener {
            val branch = selectedBranch ?: return@setOnClickListener
            showGoogleMapsSheet(branch.latitude, branch.longitude)
        }
        binding.btnSheetPhone.setOnClickListener {
            val branch = selectedBranch ?: return@setOnClickListener
            openDialer(branch.phone)
        }
    }

    private fun setupTabs() {
        binding.tvTabList.setOnClickListener { selectTab(isList = true) }
        binding.tvTabMap.setOnClickListener { selectTab(isList = false) }
        selectTab(isList = true)
    }

    private fun selectTab(isList: Boolean) {
        isListTab = isList
        if (isList) {
            binding.tvTabList.setBackgroundResource(R.drawable.bg_segmented_selected)
            binding.tvTabList.setTextColor(requireContext().getColor(R.color.text_primary))
            binding.tvTabList.textSize = 14f

            binding.tvTabMap.background = null
            binding.tvTabMap.setTextColor(requireContext().getColor(R.color.text_secondary))

            binding.listBackground.isVisible = true
            binding.listDivider.isVisible = true
            binding.mapContainer.isVisible = false
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            val hasData = branches.isNotEmpty()
            binding.recyclerView.isVisible = hasData
            binding.tvEmpty.isVisible = !hasData
            if (!hasData) binding.tvEmpty.text = "Filiallar topilmadi"
        } else {
            binding.tvTabMap.setBackgroundResource(R.drawable.bg_segmented_selected)
            binding.tvTabMap.setTextColor(requireContext().getColor(R.color.text_primary))
            binding.tvTabMap.textSize = 14f

            binding.tvTabList.background = null
            binding.tvTabList.setTextColor(requireContext().getColor(R.color.text_secondary))

            binding.listBackground.isVisible = false
            binding.listDivider.isVisible = false
            binding.recyclerView.isVisible = false
            binding.tvEmpty.isVisible = false
            binding.mapContainer.isVisible = true
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun setupMap() {
        val mapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.mapContainer, mapFragment)
            .commit()

        mapFragment.getMapAsync { map ->
            googleMap = map
            map.uiSettings.isZoomControlsEnabled = true
            map.setOnMarkerClickListener { marker ->
                val branch = marker.tag as? Branch
                if (branch != null) {
                    showBottomSheet(branch)
                    true
                } else {
                    false
                }
            }
            map.setOnMapClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
            updateMapMarkers()
        }
    }

    private fun updateMapMarkers() {
        val map = googleMap ?: return
        map.clear()
        if (branches.isEmpty()) return

        val boundsBuilder = LatLngBounds.Builder()
        branches.forEach { branch ->
            val position = LatLng(branch.latitude, branch.longitude)
            val marker = map.addMarker(MarkerOptions().position(position).title(branch.name))
            marker?.tag = branch
            boundsBuilder.include(position)
        }

        map.setOnMapLoadedCallback {
            val bounds = boundsBuilder.build()
            val padding = (resources.displayMetrics.density * 64).toInt()
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
        }
    }

    private fun showBottomSheet(branch: Branch) {
        selectedBranch = branch
        binding.tvSheetTitle.text = branch.name
        binding.tvSheetTimeValue.text = formatTimeRange(branch.openTime, branch.closeTime)
        binding.tvSheetAddressValue.text = branch.address
        binding.btnSheetPhone.text = branch.phone
        bottomSheetBehavior.peekHeight = (resources.displayMetrics.density * 300).toInt()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun formatTimeRange(open: String, close: String): String {
        val cleanOpen = open.replace(" ", "")
        val cleanClose = close.replace(" ", "")
        return "$cleanOpen-$cleanClose"
    }

    private fun openDialer(phone: String) {
        val uri = android.net.Uri.parse("tel:${phone.replace(" ", "")}")
        val intent = android.content.Intent(android.content.Intent.ACTION_DIAL, uri)
        startActivity(intent)
    }
}
