package vn.com.bravesoft.androidapp.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import vn.com.bravesoft.androidapp.modelview.LoginModelView
import vn.com.bravesoft.androidapp.modelview.TimeLineModelView

/**
 * ViewModelを提供するModule
 *
 * ViewModelを新たに作ったときにはこちらに登録する
 * [ViewModelFactory]が使用する
 *
 * @see ViewModelKey
 * @see ViewModelFactory
 * @author ex-cellpromote-ohta
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginModelView::class)
    abstract fun bindHomeCategoryViewModel(viewModel: LoginModelView): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TimeLineModelView::class)
    abstract fun bindTimeLimeViewModel(viewModel: TimeLineModelView): ViewModel
}
