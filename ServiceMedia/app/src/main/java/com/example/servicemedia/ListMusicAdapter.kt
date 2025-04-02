package com.example.servicemedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView.inflate

class ListMusicAdapter(
    private val listMusic: List<Song>,
    private val onClick: (Song) -> Unit
) :
    Adapter<ListMusicAdapter.MusicViewHolder>() {
    class MusicViewHolder(
        itemView: View,
        onClick: (Song) -> Unit
    ) : ViewHolder(itemView) {
        private var song: Song? = null
        init {
            itemView.setOnClickListener { song?.let { it1 -> onClick.invoke(it1) } }
        }

        fun bind(
            song: Song
        ) {
            this.song = song
            itemView.findViewById<TextView>(R.id.txSong).text = song.title
            itemView.findViewById<TextView>(R.id.tvNameSingle).text = song.artist
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
        return MusicViewHolder(view, onClick)
    }

    override fun getItemCount(): Int = listMusic.size

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music = listMusic[position]
        holder.bind(music)
    }
}