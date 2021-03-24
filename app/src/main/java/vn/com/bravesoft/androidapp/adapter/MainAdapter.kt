package vn.com.bravesoft.androidapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import vn.com.bravesoft.androidapp.R
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Khanh Ton on 1/7/21.
 */

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private var mInflater: LayoutInflater? = null
    private var mData: ArrayList<String>? = ArrayList()
    fun addData(data: ArrayList<String>?) {
        data?.let {
            this.mData?.clear()
            mData?.addAll(data)
            notifyDataSetChanged()
        }
    }

    @NonNull
    override fun onCreateViewHolder(
        @NonNull parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.context)
        }
        val view: View = mInflater!!.inflate(R.layout.item_recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    fun onMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(mData, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(mData, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    fun swipe(position: Int, direction: Int) {
        mData!!.removeAt(position)
        notifyItemRemoved(position)
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mTextTitle: TextView
        fun bindData(data: String?) {
            if (data == null) {
                return
            }
            mTextTitle.text = data
        }

        init {
            mTextTitle = itemView.findViewById(R.id.text_title)
        }
    }

    init {
        mData = ArrayList()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bindData(mData!![position])
    }
}
