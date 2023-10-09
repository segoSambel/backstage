package me.segosambel.backstage.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import me.segosambel.backstage.R
import me.segosambel.backstage.core.domain.model.Movie
import me.segosambel.backstage.databinding.FragmentDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView(args.movie)

        binding?.topAppBar?.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupView(movie: Movie) {
        binding?.run {
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500/${movie.backdropUrl}")
                .into(ivCollapsingPoster)
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w300/${movie.posterUrl}")
                .into(ivPoster)

            tvTitle.text = movie.title
            tvRating.text = String.format("%.1f/10", movie.rating)
            tvOverview.text = movie.overview

            var isFavorite = movie.isFavorite
            setFab(isFavorite)
            fab.setOnClickListener {
                isFavorite = !isFavorite
                viewModel.setFavoriteMovie(movie, isFavorite)
                setFab(isFavorite)
            }
        }
    }

    private fun setFab(isFavorite: Boolean) {
        if (isFavorite) {
            binding?.fab?.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_favorite_24
                )
            )
        } else {
            binding?.fab?.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_favorite_border_24
                )
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}