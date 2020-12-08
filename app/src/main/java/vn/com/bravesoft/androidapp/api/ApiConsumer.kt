package vn.com.bravesoft.androidapp.api

import vn.com.bravesoft.androidapp.model.BaseResponse

/**
 * Created by Khanh Ton on 2019-05-30.
 */

open interface ApiConsumer<M : BaseResponse> {

    fun onSuccess(o: Any) {
        onSuccess(o as M)
    }

    fun onSuccess(response: M)

    fun onFailure(throwable: Throwable)

    fun onLoading(isLoading: Boolean)
}
