package me.segosambel.backstage.util

import me.segosambel.backstage.core.domain.model.Movie

object DummyData {
    fun generateDummyNewsEntity(): List<Movie> {
        val movieList = ArrayList<Movie>()
        for (i in 0..10) {
            val movie = Movie(
                i,
                "Title $i",
                "overview $i",
                "Poster URL $i",
                "Backdrop URL $i",
                i.toDouble(),
                false
            )
            movieList.add(movie)
        }
        return movieList
    }
}