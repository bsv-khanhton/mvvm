package vn.com.bravesoft.androidapp.ext

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
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

fun Context.dpToPx(dp: Int): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        this.resources.displayMetrics
    ).toInt()

internal fun Context.getDrawableCompat(@DrawableRes drawable: Int) = ContextCompat.getDrawable(
    this,
    drawable
)

internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}
