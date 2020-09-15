package vn.com.bravesoft.androidapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by Khanh Ton on 9/15/20.
 */
class VpAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    private var data: List<Fragment>? = null

    fun setData(data: List<Fragment>?){
        this.data = data
    }

    override fun getCount(): Int {
        return data!!.size
    }

    override fun getItem(position: Int): Fragment? {
        return data!![position]
    }
}