package vn.com.bravesoft.androidapp.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * ViewModelをバインドするために使うKeyアノテーション
 *
 * @sample @ViewModelKey(XXXViewModel::class)
 *
 * @author ex-cellpromote-ohta
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
