package vn.com.bravesoft.androidapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Daggerでconstructor injectionするために必要なFactoryクラス
 *
 * @author ex-cellpromote-ohta
 */
@Singleton
class ViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>,
        @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalArgumentException::class, RuntimeException::class)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val creator = getCreator(modelClass)
            ?: throw IllegalArgumentException("unknown model class $modelClass")

        return try {
            creator.get() as T
        } catch (e: RuntimeException) {
            throw RuntimeException(e)
        }
    }

    private fun <T : ViewModel> getCreator(modelClass: Class<T>): Provider<ViewModel>? {
        val creator: Provider<ViewModel>? = creators[modelClass]
        if (creator != null) {
            return creator
        }

        creators.forEach {
            if (modelClass.isAssignableFrom(it.key)) {
                return it.value
            }
        }

        return null
    }
}
