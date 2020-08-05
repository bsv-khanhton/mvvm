# mvvm
Base code MVVM use Kotlin:
- Dagger 2
- RxJava
- Retrofit
- Lifecycle
- Support Okhttp profiler
## Requirements

Android Studio Version | Android API Version Min
------------ | -------------
3.5+ | 21

###
---------------------------
Sent data to other fragment
---------------------------
    private var param1: Int by argument()
    private var param2: String? by argumentNullable()

    companion object {
        fun newInstance(param1: Int, param2: String?): MyFragment = MyFragment().apply {
            this.param1 = param1
            this.param2 = param2
        }
    }
---------------------------
Inject
---------------------------
* Navigation fragment:
@Inject lateinit var fragmentAggregator: FragmentAggregator

* Save data into SharedPreferences:
@Inject lateinit var userCtrl: UserCtrl
<<<<<<< HEAD
---------------------------
Debug Log
---------------------------
"Text debug".logi()

"Text debug".logd()

"Text debug".loge()
=======

>>>>>>> c10ab38e74de3f416127b55172bc9b45f4fc60e8
