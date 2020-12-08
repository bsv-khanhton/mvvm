package vn.com.bravesoft.androidapp.api

import io.reactivex.Observable
import retrofit2.http.GET
import vn.com.bravesoft.androidapp.model.LoginResponse

/**
 * Created by tonkhanh on 11/6/18.
 */

interface ApiStores {
    @GET("/posts/1")
    fun login(): Observable<LoginResponse>
}
