package vn.com.bravesoft.androidapp.listener

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * Created by Khanh Ton on 1/7/21.
 */
class SimpleItemTouchHelperCallback(mListenner: ItemTouchListenner, list: ArrayList<String>) :
    ItemTouchHelper.Callback() {

    private val mListenner: ItemTouchListenner
    private val list: ArrayList<String>

    override fun isLongPressDragEnabled(): Boolean {
        return super.isLongPressDragEnabled()
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlag = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(
            dragFlag,
            swipeFlag
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        mListenner.onMove(viewHolder.adapterPosition, target.adapterPosition)
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return super.isItemViewSwipeEnabled()
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        mListenner.swipe(viewHolder.adapterPosition, direction)
    }

    init {
        this.mListenner = mListenner
        this.list = list
    }
}
