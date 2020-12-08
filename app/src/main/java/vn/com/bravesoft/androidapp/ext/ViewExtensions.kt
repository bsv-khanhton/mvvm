package vn.com.bravesoft.androidapp.ext

import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.Disposable
import vn.com.bravesoft.androidapp.utils.Constants
import java.util.concurrent.TimeUnit

/**
 * Created by Khanh Ton on 10/27/20.
 */

fun View.reactiveClick(block: () -> Unit): Disposable {
    return RxView.clicks(this)
        .throttleFirst(Constants.RX_CLICK_INTERVEL, TimeUnit.MILLISECONDS)
        .subscribe(
            {
                block()
            },
            {}
        )
}
