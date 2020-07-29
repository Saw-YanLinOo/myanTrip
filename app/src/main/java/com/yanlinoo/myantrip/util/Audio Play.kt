package com.yanlinoo.myantrip.util

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri

class AudioPlay{
    public fun StreamAudio(context : Context, uri : Uri){
        val mediaPlayer: MediaPlayer? = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(context,uri)
            prepare()
            start()
        }
    }
}