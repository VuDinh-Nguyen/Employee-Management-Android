package com.example.servicemedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentMedia : Fragment() {
    private val listAdapter by lazy {
        ListMusicAdapter(MUSIC, onClick = ::playSong)
    }

    private fun playSong(song: Song) {
        val intent = Intent(this.requireContext(), ServiceMedia::class.java)
        intent.putExtra("song_res_id", song.audioResId)
        requireContext().startForegroundService(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = listAdapter
        val btnPause = view.findViewById<Button>(R.id.btnPause)
        btnPause.setOnClickListener {
            if (btnPause.text.toString() == "pause") {
                btnPause.text = "Resum"
            } else {
                btnPause.text = "Pause"
            }
            sendActionToService("PAUSE")
        }
        view.findViewById<Button>(R.id.btnNext).setOnClickListener {
            if (btnPause.text.toString() == "pause") {
                btnPause.text = "Resum"
            }
            if (btnPause.text.toString() == "Resum") {
                btnPause.text = "Pause"
            }
            sendActionToService("NEXT")
        }
        view.findViewById<Button>(R.id.btnPre)
            .setOnClickListener {
                if (btnPause.text.toString() == "pause") {
                    btnPause.text = "Resum"
                } else {
                    btnPause.text = "Pause"
                }
                sendActionToService("PREVIOUS")
            }
    }

    private fun sendActionToService(action: String) {
        val intent =
            Intent(requireContext(), ServiceMedia::class.java).apply { this.action = action }
        requireContext().startForegroundService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}

val MUSIC = listOf(
    Song("Lan Man", "Ronbonz", R.raw.lanman),
    Song("Wrongs time", "Young Puppy", R.raw.wrongtimes),
    Song("24H", "LyLy", R.raw.lyly24h)
)
