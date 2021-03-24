package vn.com.bravesoft.androidapp.listener

/**
 * Created by Khanh Ton on 1/7/21.
 */
open interface ItemTouchListenner {
    fun onMove(oldPosition: Int, newPosition: Int)
    fun swipe(position: Int, direction: Int)
}
