package vn.com.bravesoft.androidapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.event.CallbackInstagram
import vn.com.bravesoft.androidapp.ext.load
import vn.com.bravesoft.androidapp.ext.logi
import vn.com.bravesoft.androidapp.ext.visible
import vn.com.bravesoft.androidapp.model.InstagramDTO

/**
 * Created by Khanh Ton on 5/13/20.
 */
class MediaInstagramAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var datas: ArrayList<InstagramDTO?> = arrayListOf()
    var onCallback: CallbackInstagram? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaInstagramViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_instagram, parent, false)
        return MediaInstagramViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        if (position >= datas.size) return 0
        return if (datas[position] == null) 0 else 1
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    /*fun addLoadingView() {
        //Add loading item
        Handler().post {
            datas.add(null)
            notifyItemInserted(datas.size - 1)
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        datas.isNotEmpty().let {
            if(it){
                val pos = datas.size - 1
                if(datas[datas.size - 1] == null){
                    datas.removeAt(pos)
                    notifyItemRemoved(pos)
                }
            }
        }
    }*/

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MediaInstagramViewHolder).bind(datas[position])
    }

    inner class MediaInstagramViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: InstagramDTO?) {
            item ?: return
            if (item!!.mediaType == "IMAGE") {
                itemView.findViewById<ImageView>(R.id.ivThumbnail).load(item.mediaUrl)
                itemView.findViewById<ImageView>(R.id.ivVideo).visible(false)
            } else {
                "item.thumbnailUrl: ${item.thumbnailUrl}".logi()
                itemView.findViewById<ImageView>(R.id.ivThumbnail).load(item.thumbnailUrl)
                itemView.findViewById<ImageView>(R.id.ivVideo).visible(true)
            }
            itemView.setOnClickListener {
                onCallback?.callbackSearch(item)
            }
            itemView.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    itemView.findViewById<View>(R.id.viewThumbnail).setBackgroundResource(R.color.colorAccent)
                } else {
                    itemView.findViewById<View>(R.id.viewThumbnail).setBackgroundResource(R.color.white)
                }
            }
        }
    }
}
