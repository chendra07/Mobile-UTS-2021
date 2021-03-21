package umn.ac.id.chendradewangga_00000029610_if633_el_uts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_layout.view.*
import java.util.*

class RecyclerAdapters internal constructor(context: Context, audioFiles: ArrayList<AudioFiles>) :
    RecyclerView.Adapter<RecyclerAdapters.ViewHolder>() {

    private val mFiles: ArrayList<AudioFiles> = audioFiles
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val mContext: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = mInflater.inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v, this)
    }

    override fun onBindViewHolder(holder: RecyclerAdapters.ViewHolder, position: Int) {
        holder.file_name.text = mFiles[position].title
        holder.album_music.text = mFiles[position].album


        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, MusicPlayer::class.java)
            intent.putExtra("position", position)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mFiles.size
    }

    inner class ViewHolder(itemView: View, adapters: RecyclerAdapters) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var file_name: TextView = itemView.item_title
        var album_music: TextView = itemView.item_album
        var mAdapters: RecyclerAdapters = adapters

        init {

        }

        override fun onClick(v: View) {
            mAdapters.notifyDataSetChanged()
        }

    }

}