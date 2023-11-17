package vn.com.bravesoft.androidapp.ext

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import vn.com.bravesoft.androidapp.R

/**
 * 画像を設定する
 *
 * @param url 画像のURL
 */

fun ImageView.load(url: String?) {
    if (context.isValidContextForGlide()) {
        val requestOptions = RequestOptions()
            .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.gray_semi_trans)))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context)
            .load(url)
            .apply(requestOptions)
            .thumbnail(0.1f)
            .into(this)
    }
}

/**
 * 有効なContextであるか判定する
 */
fun Context.isValidContextForGlide(): Boolean {
    if (this is Activity) {
        if (this.isDestroyed || this.isFinishing) {
            return false
        }
    }
    return true
}
