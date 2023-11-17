package vn.com.bravesoft.androidapp.event

import vn.com.bravesoft.androidapp.model.InstagramDTO

/**
 * Created by Khanh Ton on 5/13/20.
 */
interface CallbackInstagram {
    fun callbackSearch(media: InstagramDTO)
}
