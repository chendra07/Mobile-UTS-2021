package umn.ac.id.chendradewangga_00000029610_if633_el_uts

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_music_player.*
import umn.ac.id.chendradewangga_00000029610_if633_el_uts.MusicList.Companion.audioFiles
import java.io.File


class MusicPlayer : AppCompatActivity() {

    var position = -1

    companion object {
        lateinit var music_list: ArrayList<AudioFiles>
        lateinit var uri: Uri
        var mediaPlayer: MediaPlayer = MediaPlayer()
    }

    private var handler: Handler = Handler()
    private var threadPlay: Thread? = null
    private var threadPrev: Thread? = null
    private var threadNext: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        getIntentExtra()

        mediaplayer_title.text = audioFiles.get(position).title

        seektime.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        this.runOnUiThread(object : Runnable {
            override fun run() {
                if (mediaPlayer != null) {
                    // Get seekbar's time and convert into readable format
                    val mCurrentPosition = mediaPlayer.currentPosition.div(1000)
                    seektime.progress = mCurrentPosition
                    mediaplayer_time_start.setText(formattedTime(mCurrentPosition))
                }
                handler.postDelayed(this, 1000)
            }
        })

        mediaplayer_title.isSelected = true
    }

    private fun formattedTime(currPos: Int): String {
        var totalout = ""
        var totalNew = ""
        val seconds = (currPos % 60).toString()
        val minutes = (currPos / 60).toString()
        totalout = "$minutes:$seconds"
        totalNew = "$minutes:0$seconds"
        return if (seconds.length == 1) {
            totalNew
        } else {
            totalout
        }
    }

    private fun getIntentExtra() {
        position = intent.getIntExtra("position", -1)
        music_list = audioFiles

        if (music_list != null) {
            play_pause_button.setImageResource(R.drawable.ic_pause)
            uri = Uri.fromFile(File(music_list[position].path))
        }

        if (mediaPlayer != null) {
            mediaPlayer.stop()
            mediaPlayer.release()
            mediaPlayer = MediaPlayer.create(this, uri)
            mediaPlayer.start()
        } else {
            mediaPlayer = MediaPlayer.create(this, uri)
            mediaPlayer.start()
        }
        seektime.max = mediaPlayer.duration / 1000
        //durationTotal()
    }

    override fun onResume() {
        btnThreadPlay()
        btnThreadNext()
        btnThreadPrev()
        super.onResume()
    }

    private fun btnThreadPlay() {
        threadPlay = object : Thread() {
            override fun run() {
                super.run()
                play_pause_button.setOnClickListener(View.OnClickListener { play_pause_btnAction() })
            }
        }
        (threadPlay as Thread).start()
    }

    private fun play_pause_btnAction() {
        if (!mediaPlayer.isPlaying) {
            play_pause_button.setImageResource(R.drawable.ic_pause)
            mediaPlayer.start()
            seektime.max = mediaPlayer.duration / 1000
            this.runOnUiThread(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) {
                        val currPos = mediaPlayer.currentPosition / 1000
                        seektime.progress = currPos
                    }
                    handler.postDelayed(this, 1000)
                }
            })
        } else {
            play_pause_button.setImageResource(R.drawable.ic_play)
            mediaPlayer.pause()
            seektime.max = mediaPlayer.duration / 1000
            this.runOnUiThread(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) {
                        val currPos = mediaPlayer.currentPosition / 1000
                        seektime.progress = currPos
                    }
                    handler.postDelayed(this, 1000)
                }
            })
        }
    }

    private fun btnThreadNext() {
        threadNext = object : Thread() {
            override fun run() {
                super.run()
                next_button.setOnClickListener { next_btnAction() }
            }
        }
        (threadNext as Thread).start()
    }

    private fun next_btnAction() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
            position = (position + 1) % music_list.size
            uri = Uri.parse(music_list[position].path)
            mediaPlayer = MediaPlayer.create(this, uri)
            mediaplayer_title.text = music_list[position].title
            seektime.max = mediaPlayer.duration / 1000
            this.runOnUiThread(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) {
                        val currPos = mediaPlayer.currentPosition / 1000
                        seektime.progress = currPos
                    }
                    handler.postDelayed(this, 1000)
                }
            })
            play_pause_button.setImageResource(R.drawable.ic_pause)
            mediaPlayer.start()
        } else {
            mediaPlayer.stop()
            mediaPlayer.release()
            position = (position + 1) % music_list.size
            uri = Uri.parse(music_list[position].path)
            mediaPlayer = MediaPlayer.create(this, uri)
            mediaplayer_title.text = music_list[position].title
            seektime.max = mediaPlayer.duration / 1000
            this.runOnUiThread(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) {
                        val currPos = mediaPlayer.currentPosition / 1000
                        seektime.progress = currPos
                    }
                    handler.postDelayed(this, 1000)
                }
            })
            play_pause_button.setImageResource(R.drawable.ic_play)
        }
    }

    private fun btnThreadPrev() {
        threadPrev = object : Thread() {
            override fun run() {
                super.run()
                prev_button.setOnClickListener { prev_btnAction() }
            }
        }
        (threadPrev as Thread).start()

    }

    private fun prev_btnAction() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
            position = if (position - 1 < 0) music_list.size - 1 else position - 1
            uri = Uri.parse(music_list[position].path)
            mediaPlayer = MediaPlayer.create(applicationContext, uri)
            mediaplayer_title.text = music_list[position].title ?: "default"
            seektime.max = mediaPlayer.duration / 1000
            this.runOnUiThread(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) {
                        var currPos = mediaPlayer.currentPosition / 1000
                        seektime.progress = currPos
                    }
                    handler.postDelayed(this, 1000)
                }
            })
            play_pause_button.setImageResource(R.drawable.ic_pause)
        } else {
            mediaPlayer.stop()
            mediaPlayer.release()
            position = if (position - 1 < 0) music_list.size - 1 else position - 1
            uri = Uri.parse(music_list[position].path)
            mediaPlayer = MediaPlayer.create(applicationContext, uri)
            mediaplayer_title.text = music_list[position].title ?: "default"
            seektime.max = mediaPlayer.duration / 1000
            this.runOnUiThread(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) {
                        var currPos = mediaPlayer.currentPosition / 1000
                        seektime.progress = currPos
                    }
                    handler.postDelayed(this, 1000)
                }
            })
            play_pause_button.setImageResource(R.drawable.ic_play)
            mediaPlayer.start()
        }
    }


}

