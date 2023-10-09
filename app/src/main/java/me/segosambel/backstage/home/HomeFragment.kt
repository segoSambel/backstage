package me.segosambel.backstage.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import me.segosambel.backstage.R
import me.segosambel.backstage.core.data.Resource
import me.segosambel.backstage.core.ui.MovieAdapter
import me.segosambel.backstage.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            val adapter = MovieAdapter { movie ->
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie)
                findNavController().navigate(action)
            }

            rvMovies.layoutManager = GridLayoutManager(requireContext(), 2)
            rvMovies.adapter = adapter

            viewModel.movies.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Success -> adapter.setData(it.data)
                    is Resource.Error -> {}
                }
            }

            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.favorite -> {
                        try {
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
                            findNavController().navigate(action)
                            true
                        } catch (e: Exception) {
                            Toast.makeText(requireContext(), "Module not found", Toast.LENGTH_SHORT)
                                .show()
                            false
                        }
                    }

                    R.id.settings -> {
                        findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
                        true
                    }

                    else -> false
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
