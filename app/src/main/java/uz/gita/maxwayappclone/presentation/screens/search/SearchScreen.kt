package uz.gita.maxwayappclone.presentation.screens.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.databinding.ScreenSearchBinding
import uz.gita.maxwayappclone.presentation.adapter.SearchAdapter
import uz.gita.maxwayappclone.presentation.screens.search_detail.SearchDetailBottomSheet

class SearchScreen : Fragment(R.layout.screen_search) {

    private val binding by viewBinding(ScreenSearchBinding::bind)
    private val viewModel: SearchViewModel by viewModels<SearchViewModelImpl> { SearchViewModelFactory() }
    private val adapter by lazy { SearchAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.productsLiveData.observe(viewLifecycleOwner,productObserver)
        viewModel.emptyResultLiveData.observe(viewLifecycleOwner,emptyResultObserver)

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        binding.search.post {
            binding.search.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.search, InputMethodManager.SHOW_IMPLICIT)
        }

        binding.search.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    if (s.toString() != "") {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.textTitle.visibility = View.VISIBLE
                        viewModel.getSearchResult(s.toString())
                    } else {
                        adapter.submitList(emptyList<ProductUIData>())
                        adapter.notifyDataSetChanged()
                        binding.textTitle.visibility = View.GONE
                    }
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            }
        )

        adapter.setOnItemClickListener{item ->
            val bottomSheet = SearchDetailBottomSheet()
            bottomSheet.arguments = bundleOf("ID" to item.id)
            bottomSheet.show(parentFragmentManager,"SearchDetailBottomSheet")

//            findNavController().navigate(
//                resId = R.id.action_searchFragment_to_searchDetailScreen,
//                args = bundleOf("ID" to item.id)
//            )
        }
    }
    private val productObserver = Observer<List<ProductUIData>>{adapter.submitList(it)}

    private val emptyResultObserver = Observer<Boolean>{
        if (it) {
            binding.textTitle.visibility = View.GONE
            binding.imageNotFound.visibility = View.VISIBLE
        } else {
            binding.imageNotFound.visibility = View.GONE
        }
    }
}

