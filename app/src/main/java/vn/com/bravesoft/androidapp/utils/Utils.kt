package vn.com.bravesoft.androidapp.utils

/**
 * Created by Khanh Ton on 2019-05-28.
 */
object Utils {
    fun doAction(a: Int, b: Int, func: ExecuteFunction): Int {
        return func.execute(a, b)
    }

    @FunctionalInterface
    interface ExecuteFunction {
        fun execute(a: Int, b: Int): Int
    }
}
