package vn.com.bravesoft.androidapp.usecase

import io.reactivex.Observable
import org.json.JSONObject
import vn.com.bravesoft.androidapp.api.ApiStores
import vn.com.bravesoft.androidapp.ext.logi
import vn.com.bravesoft.androidapp.helper.LocalDataCtrl
import vn.com.bravesoft.androidapp.model.LoginResponse
import javax.inject.Inject

/**
 * Created by Khanh Ton on 2019-10-23.
 * Update by Linh Nguyen.
 */
class LoginUseCase @Inject constructor(private val apiService: ApiStores, private val localDataCtrl: LocalDataCtrl) {

    fun login(email: String = "", password: String = ""): Observable<LoginResponse> {
        "token: ${localDataCtrl.getAccessToken()}".logi()
        localDataCtrl.saveAccessToken("Test")
        val json = JSONObject()
        if (email.isNotEmpty()) {
            json.put("mail_address", email)
        }
        if (password.isNotEmpty()) {
            json.put("password", password)
        }
        return apiService.login()
    }
}
