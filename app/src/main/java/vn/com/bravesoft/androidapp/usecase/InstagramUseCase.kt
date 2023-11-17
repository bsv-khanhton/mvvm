package vn.com.bravesoft.androidapp.usecase

import io.reactivex.Observable
import vn.com.bravesoft.androidapp.api.ApiStores
import vn.com.bravesoft.androidapp.model.response.InstagramResponse
import javax.inject.Inject

/**
 * Created by Khanh Ton on 2019-10-23.
 */
class InstagramUseCase @Inject constructor(private val apiService: ApiStores) {

    fun getInstagram(): Observable<InstagramResponse> {
        val url = "https://graph.instagram.com/6869707193142354/media?fields=id,caption,media_type,media_url,thumbnail_url,permalink,username,timestamp&access_token=IGQWRPWjlYcHNITmJhakpwUFV2Wk9Eandpby10WnZAUNVEzbGRaUlF3SUExcHVZAdTBKYVY5aTJBZAlR5bFNMNHdVN0thdUJPLXQwQnkzTWxZATWlJcDgxek9VNTZAHTnlFWmkzekZAUT0pLdWhQT2gtamx5eW14WGlEb0UZD"
        return apiService.getInstagram(url)
    }
}
