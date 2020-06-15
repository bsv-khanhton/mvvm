package vn.com.bravesoft.androidapp.helper

import vn.com.bravesoft.androidapp.MainActivity
import vn.com.bravesoft.androidapp.di.scope.UiScope
import vn.com.bravesoft.androidapp.ui.MainTabHostFragment
import javax.inject.Inject

/**
 * 各ページへのIntentをまとめたクラス
 *
 * @author ex-cellpromote-ohta
 */
@UiScope
class FragmentAggregator @Inject constructor(private val activity: MainActivity) {
    /**
     * 記事詳細ページへ遷移
     * プロモーションページの場合はWebViewを起動する
     *
     * @param article 記事データ
     */
    fun openMainTabHost(username: String) {
        activity?.replaceFragment(MainTabHostFragment.newInstance(username), isClearBacktrack = true)
    }

}
