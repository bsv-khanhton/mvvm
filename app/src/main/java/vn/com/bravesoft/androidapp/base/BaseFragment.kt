package vn.com.bravesoft.androidapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

import com.kaopiz.kprogresshud.KProgressHUD

import vn.com.bravesoft.androidapp.MainActivity
import vn.com.bravesoft.androidapp.utils.LogUtil

abstract class BaseFragment(@LayoutRes private val rootLayout: Int) : Fragment() {

    protected var mMainActivity: MainActivity? = null
    private var kProgressHUD: KProgressHUD? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mMainActivity = activity as MainActivity?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(rootLayout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        setupActionBar()
    }

    protected abstract fun init(view: View)

    fun setupActionBar() {}

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        hideLoading()
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    fun hideLoading() {
        if (kProgressHUD != null && kProgressHUD?.isShowing == true && isAdded) {
            kProgressHUD?.dismiss()
        }
    }

    fun showLoading() {
        if (kProgressHUD == null) {
            kProgressHUD = KProgressHUD.create(mMainActivity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)

        }
        if (kProgressHUD?.isShowing == false && isAdded) {
            kProgressHUD?.show()
        }
    }

    fun loadAPIFail(message: String) {
        LogUtil.log(message)
    }

    fun loadAPIError(throwable: Throwable) {
        throwable?.message?.let{
            LogUtil.log(it)
        }
    }

}
