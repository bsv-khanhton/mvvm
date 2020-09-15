package vn.com.bravesoft.androidapp.views

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.input_view_layout.view.*
import vn.com.bravesoft.androidapp.R

/**
 * Created by Hao Tran on 9/15/2020.
 */
class InputView : LinearLayout {

    private var label: String? = null
    private var hint: String? = null
    private var value: String? = null
    private var typeView: Int = 0
    private var inputType: Int? = 0
    private var labelResource: Int = 0

    constructor(context: Context) : super(context) {
        init(context, null, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, null)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int?) {
        val arr = R.styleable.AttrInputView
        val type = getContext().obtainStyledAttributes(attrs, arr, defStyleAttr ?: 0, 0)
        label = type.getString(R.styleable.AttrInputView_label)
        typeView = type.getInt(R.styleable.AttrInputView_value_type, 0)
        hint = type.getString(R.styleable.AttrInputView_hint)
        inputType = type.getInt(R.styleable.AttrInputView_input_type, 0)
        value = type.getString(R.styleable.AttrInputView_value)
        labelResource = type.getInt(R.styleable.AttrInputView_backgroundResource, 0)

        type.recycle()
        View.inflate(context, R.layout.input_view_layout, this)
        initView()
    }

    private fun initView() {
        tvLabel.text = label
        ivLabel.visibility = View.GONE
        switchPassword.visibility = View.GONE

        if (labelResource != 0) {
            ivLabel.visibility = View.VISIBLE
            ivLabel.setBackgroundResource(labelResource)
        }

        when (typeView) {
            /*Edit Text*/
            0 -> {
                tvValue.visibility = View.GONE
                etValue.hint = hint
                etValue.text = value?.toEditable()
                when (inputType) {
                    /*Text*/
                    0 -> etValue.inputType = InputType.TYPE_CLASS_TEXT
                    /*Number*/
                    1 -> etValue.inputType = InputType.TYPE_CLASS_NUMBER
                    /*Phone*/
                    2 -> {
                        etValue.inputType = InputType.TYPE_CLASS_PHONE
                        val maxLength = 13
                        val filterArray = arrayOfNulls<InputFilter>(1)
                        filterArray[0] = InputFilter.LengthFilter(maxLength)
                        etValue.filters = filterArray
                        etValue.addTextChangedListener(PhoneNumberFormattingTextWatcher("JP"))
                    }
                    /*Password*/
                    3 -> {
                        etValue.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                        etValue.transformationMethod = PasswordTransformationMethod.getInstance()
                        switchPassword.visibility = View.VISIBLE
                        switchPassword.setOnCheckedChangeListener { _, b ->
                            if(b){
                                etValue.transformationMethod = null
                            }else{
                                etValue.transformationMethod = PasswordTransformationMethod.getInstance()
                            }
                            etValue.setSelection(etValue.text.length)
                        }
                    }
                    4 -> {
                        etValue.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    }
                }
            }
            /*TextView*/
            1 -> {
                tvValue.visibility = View.VISIBLE
                tvLabel.text = label
                ivLabel.visibility = View.GONE
                etValue.visibility = View.GONE
                tvValue.text = value
            }
        }
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    fun setTextViewValue(value: String) {
        tvValue.text = value
    }

    fun setEditTextValue(value: String) {
        etValue.text = value.toEditable()
    }

    fun setError(value: String? = null) {
        if (value.isNullOrEmpty()) {
            tvError.visibility = View.INVISIBLE
            tvLabel.setTextColor(ContextCompat.getColor(tvLabel.context, android.R.color.black))
            etValue.setTextColor(ContextCompat.getColor(etValue.context, android.R.color.black))
        } else {
            tvError.visibility = View.VISIBLE
            tvError.text = value
            tvLabel.setTextColor(ContextCompat.getColor(tvLabel.context, android.R.color.holo_red_light))
            etValue.setTextColor(ContextCompat.getColor(etValue.context, android.R.color.holo_red_light))
        }
    }

    fun setError(@StringRes idString: Int){
        setError(resources.getString(idString))
    }

    fun setValue(value : String){
        if(typeView == 0){
            etValue.text = value.toEditable()
        }else{
            tvValue.text = value
        }
    }

    fun getValue(): String{
        return if(typeView == 0){
            etValue.text.toString()
        }else{
            tvValue.text.toString()
        }
    }

    fun setLabelValue(value: String) {
        tvLabel.text = value
    }

    fun getLabelValue() = tvLabel.text.toString()

    fun setEditHint(value: String) {
        etValue.hint = value
    }

    fun setMaxLength(max:Int){
        etValue.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(max))
    }

    fun getEditText() = etValue

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.value = etValue.text.toString()
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SavedState
        super.onRestoreInstanceState(ss.superState)
        etValue.setText(ss.value)
    }


    class SavedState : BaseSavedState {
        var value: String? = null

        internal constructor(superState: Parcelable?) : super(superState) {}
        constructor(source: Parcel) : super(source) {
            value = source.readString()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeString(value)
        }

        companion object {
            val CREATOR: Parcelable.Creator<SavedState?> = object : Parcelable.Creator<SavedState?> {
                override fun createFromParcel(`in`: Parcel): SavedState? {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}