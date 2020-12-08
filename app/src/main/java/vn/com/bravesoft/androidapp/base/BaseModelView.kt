package vn.com.bravesoft.androidapp.base

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import vn.com.bravesoft.androidapp.rx.SingleLiveEvent
import vn.com.bravesoft.androidapp.api.ApiConsumer as ApiConsumer

abstract class BaseModelView : ViewModel() {
    private var mCompositeDisposable: CompositeDisposable? = null

    val loadCompletionSubject: PublishSubject<Any> = PublishSubject.create()
    val onShowLoading = SingleLiveEvent<Boolean>()
    val onLoadAPIFail = SingleLiveEvent<String>()
    val onLoadAPIError = SingleLiveEvent<Throwable>()
    val onSentMessage = SingleLiveEvent<Int>()

    fun hideLoading() {
        onShowLoading.setValue(false)
    }

    fun showLoading() {
        onShowLoading.setValue(true)
    }

    fun loading(isLoading: Boolean) {
        onShowLoading.setValue(isLoading)
    }

    fun detachView() {
        onUnsubscribe()
    }

    // RXjava
    fun onUnsubscribe() {
        if (mCompositeDisposable != null && mCompositeDisposable?.size() ?: 0 > 0) {
            mCompositeDisposable?.clear()
            mCompositeDisposable = null
        }
        if (mCompositeDisposable != null && mCompositeDisposable?.isDisposed == false) {
            mCompositeDisposable?.dispose()
        }
    }

    open fun addSubscription(
        observable: Observable<*>?,
        response: ApiConsumer<*>
    ) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        response.onLoading(true)
        observable?.let {
            mCompositeDisposable?.add(
                it
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally {
                        loadCompletionSubject.onNext("load finish")
                    }
                    .subscribe(
                        { success ->
                            response.onLoading(false)
                            response.onSuccess(success)
                        },
                        { throwable ->
                            loadCompletionSubject.onNext("load error")
                            response.onFailure(throwable as Throwable)
                        }
                    )
            )
        }
    }
}
