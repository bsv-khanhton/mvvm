package vn.com.bravesoft.androidapp.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url
import vn.com.bravesoft.androidapp.model.response.InstagramResponse
import vn.com.bravesoft.androidapp.model.response.LoginResponse

/**
 * Created by tonkhanh on 11/6/18.
 */

interface ApiStores {
    @GET("/posts/1")
    fun login(): Observable<LoginResponse>
    @GET()
    fun getInstagram(@Url url: String): Observable<InstagramResponse>
}
