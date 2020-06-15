package vn.com.bravesoft.androidapp.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import vn.com.bravesoft.androidapp.MyApplication
import vn.com.bravesoft.androidapp.delegate.FragmentArgumentDelegate
import vn.com.bravesoft.androidapp.delegate.FragmentNullableArgumentDelegate
import vn.com.bravesoft.androidapp.di.AppComponent
import vn.com.bravesoft.androidapp.di.FragmentComponent
import vn.com.bravesoft.androidapp.di.FragmentModule
import vn.com.bravesoft.androidapp.di.ViewModelFactory
import kotlin.properties.ReadWriteProperty

/**
 * 親コンポーネントを取得する
 *
 * 具体的には[ConobieApplication.component]を取得する
 *
 * @receiver [Fragment]
 * @return ActivityComponent
 */
private fun Fragment.parentComponent(): AppComponent? = (requireContext().applicationContext as? MyApplication)?.component

/**
 * FragmentComponentを取得する
 *
 * @receiver [Fragment]
 * @return FragmentComponent
 */
@Suppress("UNCHECKED_CAST")
fun <C : FragmentComponent> Fragment.bindComponent(): Lazy<C> = lazy {
    parentComponent()
        ?.fragmentComponentBuilder()
        ?.fragmentModule(FragmentModule(this))
        ?.build() as C? ?: throw NullPointerException("Component Not Found.")
}

fun <T : Any> Fragment.argument(): ReadWriteProperty<Fragment, T> =
    FragmentArgumentDelegate()

fun <T : Any> Fragment.argumentNullable(): ReadWriteProperty<Fragment, T?> =
    FragmentNullableArgumentDelegate()

/**
 * ViewModelを取得する
 *
 * @receiver [Fragment]
 * @param viewModelFactory [ViewModelFactory]
 * @return ViewModel
 */
inline fun <reified T : ViewModel> Fragment.getViewModel(viewModelFactory: ViewModelFactory): T {
    return ViewModelProviders.of(this, viewModelFactory).get(T::class.java)
}
