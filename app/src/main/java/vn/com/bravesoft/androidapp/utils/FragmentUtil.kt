package vn.com.bravesoft.androidapp.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import vn.com.bravesoft.androidapp.R

class FragmentUtil {

    /**
     * Replace fragment.
     *
     * @param fragmentManager the transaction
     * @param fragment        the fragment
     * @param viewContainer   the view container
     */
    fun replaceFragment(fragmentManager: FragmentManager, fragment: Fragment, viewContainer: Int) {
        val FRAGMENT_TAG = fragment.javaClass.simpleName

        val transaction = fragmentManager.beginTransaction()
        transaction
            .addToBackStack(FRAGMENT_TAG)
            .setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.exit_to_right)
            .replace(viewContainer, fragment, FRAGMENT_TAG)
            .commitAllowingStateLoss()
    }

    fun replaceFragmentImmediate(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        viewContainer: Int
    ) {
        val FRAGMENT_TAG = fragment.javaClass.simpleName

        val transaction = fragmentManager.beginTransaction()
        transaction
            .replace(viewContainer, fragment, FRAGMENT_TAG)
            .addToBackStack(FRAGMENT_TAG)
            // .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .commitAllowingStateLoss()
    }

    fun addFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        viewContainer: Int,
        enterAnim: Int,
        exitAnim: Int
    ) {
        val FRAGMENT_TAG = fragment.javaClass.simpleName

        val transaction = fragmentManager.beginTransaction()
        getCurrentFragment(fragmentManager)?.let {
            transaction
                .setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim)
                .hide(it)
                .add(viewContainer, fragment, FRAGMENT_TAG)
                .addToBackStack(FRAGMENT_TAG)
                .commit()
        }
    }

    /**
     * Replace child fragment.
     *
     * @param parentFragment the parent fragment
     * @param childFragment  the child fragment
     * @param viewContainer  the view container
     */
    fun replaceChildFragment(
        parentFragment: Fragment,
        childFragment: Fragment,
        viewContainer: Int
    ) {

        val FRAGMENT_TAG = childFragment.javaClass.simpleName

        parentFragment
            .childFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .addToBackStack(FRAGMENT_TAG)
            .replace(viewContainer, childFragment, FRAGMENT_TAG)
            .commit()
    }

    fun addChildFragment(parentFragment: Fragment, childFragment: Fragment, viewContainer: Int) {

        val FRAGMENT_TAG = childFragment.javaClass.simpleName

        parentFragment
            .childFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .addToBackStack(FRAGMENT_TAG)
            .add(viewContainer, childFragment, FRAGMENT_TAG)
            .commit()
    }

    /**
     * Replace child fragment without add to back stack.
     *
     * @param parentFragment the parent fragment
     * @param childFragment  the child fragment
     * @param viewContainer  the view container
     */
    fun replaceChildFragmentWithoutAddToBackStack(
        parentFragment: Fragment,
        childFragment: Fragment,
        viewContainer: Int
    ) {

        val FRAGMENT_TAG = childFragment.javaClass.simpleName

        parentFragment
            .childFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(viewContainer, childFragment, FRAGMENT_TAG)
            .commit()
    }

    fun getFragmentBackStack(fragmentManager: FragmentManager, index: Int): Fragment? {
        val FRAGMENT_TAG = fragmentManager.getBackStackEntryAt(index).name
        return fragmentManager.findFragmentByTag(FRAGMENT_TAG)
    }

    fun replaceFragmentAfterResetBackstack(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        viewContainer: Int
    ) {
        resetBackstack(fragmentManager)
        replaceFragmentImmediate(fragmentManager, fragment, viewContainer)
    }

    fun resetBackstack(fragmentManager: FragmentManager) {
        while (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStackImmediate()
        }
    }

    fun backStackToMain(fragmentManager: FragmentManager) {
        for (i in 0 until fragmentManager.backStackEntryCount - 1) {
            fragmentManager.popBackStack()
        }
        fragmentManager.beginTransaction().commitAllowingStateLoss()
    }

    private fun getCurrentFragment(fragmentManager: FragmentManager?): Fragment? {
        if (fragmentManager != null && fragmentManager.backStackEntryCount > 0) {
            val FRAGMENT_TAG =
                fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1)
                    .name
            return fragmentManager.findFragmentByTag(FRAGMENT_TAG)
        }
        return null
    }

    companion object {
        /**
         * Get instance fragment util.
         *
         * @return the fragment util
         */
        val instance = FragmentUtil()
    }
}
