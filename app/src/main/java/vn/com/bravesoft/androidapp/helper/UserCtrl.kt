package vn.com.bravesoft.androidapp.helper

import com.google.gson.Gson
import vn.com.bravesoft.androidapp.model.UserDTO
import javax.inject.Inject

/**
 * Created by AnhVo on 2019-11-18
 */
class UserCtrl @Inject constructor(private val storeClient: StoreClient) {

    private var userEntity: UserDTO? = null

    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val USER_INFO = "user_info"
    }

    fun saveUser(userEntity: UserDTO?) {
        userEntity ?: return
        storeClient.saveString(USER_INFO, Gson().toJson(userEntity))
        this.userEntity = userEntity
    }

    fun getUser(): UserDTO {
        if (userEntity == null)
            userEntity = getUserStore(USER_INFO, "")
        return userEntity ?: UserDTO()
    }

    fun getUserStore(key: String, defaultValue: String): UserDTO {
        val jsonUser = storeClient.getString(key, defaultValue)
        return if (!jsonUser.isNullOrEmpty())
            Gson().fromJson(jsonUser, UserDTO::class.java)
        else
            UserDTO()
    }

    fun isUserLogged(): Boolean {
        return getAccessToken().isNotEmpty()
    }

    fun saveAccessToken(token: String) {
        storeClient.saveString(ACCESS_TOKEN, token)
    }

    fun getAccessToken(): String {
        val accessToken = storeClient.getString(ACCESS_TOKEN, "")!!
        return accessToken
    }

    // Reset UserCtrl
    // Remove local data
    // Clear all notification
    fun userLogout() {
        this.userEntity = null
        storeClient.remove(USER_INFO)
        storeClient.remove(ACCESS_TOKEN)
    }
}
