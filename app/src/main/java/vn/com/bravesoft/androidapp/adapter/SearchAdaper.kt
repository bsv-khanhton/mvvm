package vn.com.bravesoft.androidapp.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.event.CallbackSearch
import vn.com.bravesoft.androidapp.ext.load
import vn.com.bravesoft.androidapp.model.VideoDTO

/**
 * Created by Khanh Ton on 5/13/20.
 */
class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var datas: ArrayList<VideoDTO?> = arrayListOf()
    var onCallbackSearch: CallbackSearch? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int{
        if(position >= datas.size) return 0
        return if(datas[position] == null)  0 else 1
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
        (holder as SearchViewHolder).bind(datas[position])
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: VideoDTO?){
            item ?: return
            itemView.findViewById<ImageView>(R.id.ivThumbnail).load("https://img.youtube.com/vi/${item.videoID}/0.jpg")
            itemView.setOnClickListener {
                onCallbackSearch?.callbackSearch(item.videoID)
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