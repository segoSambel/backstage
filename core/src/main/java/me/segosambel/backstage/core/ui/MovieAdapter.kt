package me.segosambel.backstage.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.segosambel.backstage.core.R
import me.segosambel.backstage.core.domain.model.Movie

class MovieAdapter(
    private val onClickListener: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {

    private var listData: List<Movie> = emptyList()

    fun setData(newListData: List<Movie>?) {
        if (newListData.isNullOrEmpty()) return
        val diffCallback = ItemDiffUtilCallback(listData, newListData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listData = newListData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false)
        return ListViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onClickListener.invoke(listData[adapterPosition])
            }
        }

        fun bind(data: Movie) {
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w300/${data.posterUrl}")
                .into(itemView.findViewById(R.id.iv_poster))
            itemView.findViewById<TextView>(R.id.tv_title).text = data.title
        }
    }

    private inner class ItemDiffUtilCallback(
        private val oldItem: List<Movie>,
        private val newItem: List<Movie>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldItem.size

        override fun getNewListSize(): Int = newItem.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition].movieId==newItem[newItemPosition].movieId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition]==newItem[newItemPosition]
        }
    }
}
