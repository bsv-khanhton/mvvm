package vn.com.bravesoft.androidapp.ui

import android.view.View
import android.viewbinding.library.fragment.viewBinding
import com.denzcoskun.imageslider.models.SlideModel
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.base.BaseFragment
import vn.com.bravesoft.androidapp.databinding.HomeLayoutBinding

class HomeFragment : BaseFragment(R.layout.home_layout) {
    private val binding: HomeLayoutBinding by viewBinding()

    val imageList = ArrayList<SlideModel>() // Create image list

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun init(view: View) {
        imageList.add(SlideModel("https://www.stfrancisanimalwelfare.co.uk/wp-content/uploads/sfaw-animals-banner.jpg", "The animal population decreased by 58 percent in 42 years."))
        imageList.add(SlideModel("https://www.shutterstock.com/image-photo/web-banner-cute-happy-healthy-260nw-1919421146.jpg", "Elephants and tigers may become extinct."))
        imageList.add(SlideModel("https://media.os.fressnapf.com/cms/2020/04/Ratgeber-Welpenentwicklung.jpg?t=cmsimg_920", "And people do that."))

        binding.imageSlider.setImageList(imageList)
    }
}