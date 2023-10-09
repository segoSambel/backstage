package me.segosambel.backstage.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import me.segosambel.backstage.core.ui.MovieAdapter
import me.segosambel.backstage.databinding.FragmentFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(favoriteModule)

        binding?.run {
            val adapter = MovieAdapter {
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }

            rvMovies.layoutManager = GridLayoutManager(requireContext(), 2)
            rvMovies.adapter = adapter

            viewModel.favoriteMovies.observe(viewLifecycleOwner, adapter::setData)

            topAppBar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}