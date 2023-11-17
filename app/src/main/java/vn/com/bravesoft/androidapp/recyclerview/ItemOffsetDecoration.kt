package vn.com.bravesoft.androidapp.recyclerview

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Khanh Ton on 5/13/20.
 */
class ItemOffsetDecoration(private val mItemOffset: Int) : RecyclerView.ItemDecoration() {

    constructor(@NonNull context: Context, @DimenRes itemOffsetId: Int) : this(context.resources.getDimensionPixelSize(itemOffsetId)) {}

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if(position  == 0 || position  == 1 || position  == 2|| position  == 3|| position  == 4){
            when {
                position % 5 == 0 -> {
                    outRect.set(mItemOffset, mItemOffset, mItemOffset/2, mItemOffset)
                }
                position % 5 == 1 || position % 5 == 2|| position % 5 == 3-> {
                    outRect.set(mItemOffset/2, mItemOffset, mItemOffset/2, mItemOffset)
                }
                else -> {
                    outRect.set(mItemOffset/2, mItemOffset, mItemOffset, mItemOffset)
                }
            }
        }else{
            when {
                position % 5 == 0 -> {
                    outRect.set(mItemOffset, 0, mItemOffset/2, mItemOffset)
                }
                position % 5 == 1 || position % 5 == 2|| position % 5 == 3 -> {
                    outRect.set(mItemOffset/2, 0, mItemOffset/2, mItemOffset)
                }
                else -> {
                    outRect.set(mItemOffset/2, 0, mItemOffset, mItemOffset)
                }
            }
        }
    }
}