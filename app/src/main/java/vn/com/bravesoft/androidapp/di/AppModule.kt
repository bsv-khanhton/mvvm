package vn.com.bravesoft.androidapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import vn.com.bravesoft.androidapp.BuildConfig
import vn.com.bravesoft.androidapp.api.ApiStores
import vn.com.bravesoft.androidapp.api.error.RxErrorHandlingCallAdapterFactory
import vn.com.bravesoft.androidapp.ext.logi
import vn.com.bravesoft.androidapp.helper.LocalDataCtrl
import vn.com.bravesoft.androidapp.helper.StoreClient
import vn.com.bravesoft.androidapp.utils.Constants
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author khanhton
 */
@Module
class AppModule {

    @Provides
    fun provideContext(application: Application): Context = application

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideApplicationInterceptor(localDataCtrl: LocalDataCtrl): Interceptor {
        return Interceptor { chain ->
            val url = chain.request().url.newBuilder()
                .build()

            val request = chain.request().newBuilder().url(url).apply {
                val accessToken = localDataCtrl.getAccessToken()
                if (accessToken.isNotEmpty()) {
                    header("Authorization", "Bearer $accessToken")
                    accessToken.logi()
                }
            }.build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        // Debug時にはログを出力
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideSharedPreference(context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideStoreClient(sharedPreferences: SharedPreferences): StoreClient = StoreClient(sharedPreferences)

    @Provides
    @Singleton
    fun provideUserCtrl(storeClient: StoreClient): LocalDataCtrl = LocalDataCtrl(storeClient)

    @Provides
    @Singleton
    fun provideRetrofit(
        applicationInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        // Timeout時間を20秒に変更
        val client = OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(applicationInterceptor)

        if (BuildConfig.DEBUG) {
            client.addInterceptor(OkHttpProfilerInterceptor())
        }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_API)
            .client(client.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiStores = retrofit.create(ApiStores::class.java)
}
