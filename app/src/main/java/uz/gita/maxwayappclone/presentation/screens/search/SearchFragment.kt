package uz.gita.maxwayappclone.presentation.screens.search


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.maxwayappclone.R
import uz.gita.maxwayappclone.databinding.FragmentSearchBinding

class SearchFragment: Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: SearchViewModel by viewModels <SearchViewModelImpl>{ SearchViewModelFactory() }
    private lateinit var adapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter =  SearchAdapter(requireContext())
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)
        binding.recyclerView.adapter = adapter

        observe()
        binding.search.requestFocus()


        binding.search.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    viewModel.getSearchResult(s.toString())
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            }
        )
    }
    private fun observe(){
         viewModel.loadingLiveData.observe(viewLifecycleOwner){
             binding.progress.isVisible = it
         }
        viewModel.searchLiveData.observe(viewLifecycleOwner){responses ->
            adapter.submitList(responses)
        }
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

}