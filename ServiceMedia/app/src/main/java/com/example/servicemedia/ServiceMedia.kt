package com.example.servicemedia


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat


class ServiceMedia : Service() {


    private var notificationManager: NotificationManager? = null
    private var mediaPlayer: MediaPlayer? = null
    private var currentSongIndex: Int = 0
    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            "CHANNEL_ID",
            "MyForegroundService",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        notificationManager?.createNotificationChannel(channel)
    }

    private fun nextSong() {
        val songs =
            if (currentSongIndex == MUSIC.size - 1) MUSIC[0] else MUSIC[currentSongIndex + 1]
        currentSongIndex = MUSIC.indexOf(songs)
        Log.d("currentSongIndex", "$currentSongIndex")
        playSong(MUSIC[currentSongIndex])
    }

    private fun preSong() {
        val songs =
            if (currentSongIndex == 0) MUSIC[MUSIC.size - 1] else MUSIC[currentSongIndex - 1]
        currentSongIndex = MUSIC.indexOf(songs)
        playSong(MUSIC[currentSongIndex])
    }

    private fun pauseSong() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        } else {
            mediaPlayer?.start()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action != null) {
            when (intent.action) {
                "NEXT" -> nextSong()
                "PREVIOUS" -> preSong()
                "PAUSE" -> pauseSong()
            }
        } else {
            val songResId = intent?.getIntExtra("song_res_id", -1)
            val song = MUSIC.filter { music -> music.audioResId == songResId }
            currentSongIndex = MUSIC.indexOf(song.firstOrNull())
            Log.d("alo", "$currentSongIndex")
            if (songResId != -1) {
                if (mediaPlayer != null) {
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                }
                mediaPlayer = MediaPlayer.create(this, song.first().audioResId)
                mediaPlayer?.isLooping = true
                mediaPlayer?.start()
            }
            val notification = NotificationCompat.Builder(this, "CHANNEL_ID")
                .setContentTitle(song.firstOrNull()?.title)
                .setContentText(song.firstOrNull()?.artist)
                .setSmallIcon(R.drawable.img)
                .setOngoing(true)
                .setSound(null)
                .addAction(R.drawable.img_1, "Previous", getPendingIntent("PREVIOUS"))
                .addAction(R.drawable.img_2, "Play", getPendingIntent("PAUSE"))  // Có thể là Play/Pause
                .addAction(R.drawable.img_3, "Next", getPendingIntent("NEXT"))
                .build()
            ServiceCompat.startForeground(
                this,
                100,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            )
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun playSong(song: Song) {

        mediaPlayer?.stop()
        mediaPlayer?.release()

        mediaPlayer = MediaPlayer.create(this, song.audioResId).apply {
            isLooping = true
            start()
        }
        val notification = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setContentTitle(song.title)
            .setContentText(song.artist)
            .setSmallIcon(R.drawable.img)
            .setOngoing(true)
            .setSound(null)
            .addAction(R.drawable.img_1, "Previous", getPendingIntent("PREVIOUS"))
            .addAction(R.drawable.img_2, "Pause", getPendingIntent("PAUSE"))
            .addAction(R.drawable.img_3, "Next", getPendingIntent("NEXT"))
            .build()

        ServiceCompat.startForeground(
            this,
            100,
            notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
        )
    }
    private fun getPendingIntent(action: String): PendingIntent {
        val intent = Intent(this, ServiceMedia::class.java).apply {
            this.action = action
        }
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
