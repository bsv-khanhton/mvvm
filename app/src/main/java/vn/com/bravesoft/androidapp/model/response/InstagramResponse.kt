package vn.com.bravesoft.androidapp.model.response

import com.google.gson.annotations.SerializedName
import vn.com.bravesoft.androidapp.model.InstagramDTO

class InstagramResponse : BaseResponse() {
    @SerializedName("data")
    var data: ArrayList<InstagramDTO> = ArrayList()
}
