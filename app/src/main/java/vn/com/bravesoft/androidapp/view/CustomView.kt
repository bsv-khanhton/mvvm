package vn.com.bravesoft.androidapp.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import  androidx.appcompat.widget.AppCompatImageView

/**
 * Created by KhanhTon on 5/28/20
 */
class CustomView: FrameLayout {

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)
    }
}