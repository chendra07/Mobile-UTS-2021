package umn.ac.id.chendradewangga_00000029610_if633_el_uts

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_music_list.*

class MusicList : AppCompatActivity() {

    companion object {
        lateinit var audioFiles: ArrayList<AudioFiles>
    }

    val REQUEST_CODE = 1
    private val permissionStorage = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_list)
        popUp()
        permission()

        recyclerView.adapter = RecyclerAdapters(this, audioFiles)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun popUp() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Welcome!")
        builder.setMessage("Chendra Dewangga - 00000029610")
        builder.setPositiveButton("Ok", { dialogInterface: DialogInterface, i: Int -> })
        builder.show()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.)
//    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                audioFiles = getAllAudio(this)

            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE
            )
        }
    }

    private fun permission() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                permissionStorage,
                REQUEST_CODE
            )
        } else {
            Toast.makeText(this, "Permission Granted!", Toast.LENGTH_LONG).show()
            audioFiles = getAllAudio(this)
        }
    }

    fun getAllAudio(context: Context): ArrayList<AudioFiles> {
        val tempAudioList: ArrayList<AudioFiles> = ArrayList()
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ARTIST
        )
        val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val album: String = cursor.getString(0)
                val title: String = cursor.getString(1)
                val duration: Int = cursor.getInt(2)
                val path: String = cursor.getString(3)
                val artist: String = cursor.getString(4)
                val musicFiles = AudioFiles(path, title, album, duration)
                Log.e("Path : $path", " Duration : $duration")
                tempAudioList.add(musicFiles)
            }
            cursor.close()
        }
        return tempAudioList
    }
}