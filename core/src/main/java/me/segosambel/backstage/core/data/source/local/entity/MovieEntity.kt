package me.segosambel.backstage.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo("movieId")
    var movieId: Int,

    @ColumnInfo("title")
    var title: String,

    @ColumnInfo("overview")
    var overview: String,

    @ColumnInfo("posterUrl")
    var posterUrl: String,

    @ColumnInfo("backdropUrl")
    var backdropUrl: String,

    @ColumnInfo("rating")
    var rating: Double,

    @ColumnInfo("isFavorite")
    var isFavorite: Boolean
)
