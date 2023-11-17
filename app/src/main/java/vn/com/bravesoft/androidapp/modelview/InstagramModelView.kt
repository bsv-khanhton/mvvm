package vn.com.bravesoft.androidapp.modelview

import vn.com.bravesoft.androidapp.api.ApiConsumer
import vn.com.bravesoft.androidapp.base.BaseModelView
import vn.com.bravesoft.androidapp.ext.logi
import vn.com.bravesoft.androidapp.model.response.InstagramResponse
import vn.com.bravesoft.androidapp.model.response.LoginResponse
import vn.com.bravesoft.androidapp.rx.SingleLiveEvent
import vn.com.bravesoft.androidapp.usecase.InstagramUseCase
import javax.inject.Inject

class InstagramModelView @Inject constructor(val useCase: InstagramUseCase) : BaseModelView() {

    val onLoadInstagramSuccessed: SingleLiveEvent<InstagramResponse> = SingleLiveEvent()

    fun getInstagram() {
        addSubscription(
            useCase.getInstagram(),
            object : ApiConsumer<InstagramResponse> {
                override fun onSuccess(response: InstagramResponse) {
                    "onSuccess: ${response.data.size}".logi()
                    if (response.status_code == 0) {
                        onLoadInstagramSuccessed.setValue(response)
                    } else {
                        onLoadAPIFail.setValue(response.message)
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    "onFailure: ${throwable.message}".logi()
                    onLoadAPIError.setValue(throwable)
                }

                override fun onLoading(isLoading: Boolean) {
                    loading(isLoading)
                }
            }
        )
    }
}
