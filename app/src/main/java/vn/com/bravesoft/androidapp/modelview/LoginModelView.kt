package vn.com.bravesoft.androidapp.modelview

import vn.com.bravesoft.androidapp.api.ApiConsumer
import vn.com.bravesoft.androidapp.base.BaseModelView
import vn.com.bravesoft.androidapp.model.LoginResponse
import vn.com.bravesoft.androidapp.model.UserDTO
import vn.com.bravesoft.androidapp.rx.SingleLiveEvent
import vn.com.bravesoft.androidapp.usecase.LoginUseCase
import javax.inject.Inject

class LoginModelView @Inject constructor(private val useCase: LoginUseCase) : BaseModelView() {

    val onLoginSuccess: SingleLiveEvent<String> = SingleLiveEvent()

    fun login(user: UserDTO) {
        addSubscription(
            useCase.login(user.username, user.password),
            object : ApiConsumer<LoginResponse> {
                override fun onSuccess(response: LoginResponse) {
                    if (response.status_code == 0) {
                        onLoginSuccess.setValue(response.title)
                    } else {
                        onLoadAPIFail.setValue(response.message)
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    onLoadAPIError.setValue(throwable)
                }

                override fun onLoading(isLoading: Boolean) {
                    loading(isLoading)
                }
            }
        )
    }
}
