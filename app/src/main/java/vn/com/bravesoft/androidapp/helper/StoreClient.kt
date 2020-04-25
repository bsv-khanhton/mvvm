package vn.com.bravesoft.androidapp.helper

import android.content.SharedPreferences
import android.util.Base64
import java.lang.Exception
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * @author KhanhTon
 */
@Singleton
class StoreClient @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val KEY = "SHARED_PREFS"
        private const val ENCRYPT_STRING = "ECRYPTION"
        private const val CHARSET_NAME = "UTF8"
        private const val ALGORITHM = "DES"

        private fun encrypt(normalStr: String): String {
            return try {
                val keySpec = DESKeySpec(ENCRYPT_STRING.toByteArray(charset(CHARSET_NAME)))
                val keyFactory = SecretKeyFactory.getInstance(ALGORITHM)
                val key = keyFactory.generateSecret(keySpec)
                val clearText = normalStr.toByteArray(charset(CHARSET_NAME))
                val cipher = Cipher.getInstance(ALGORITHM)
                cipher.init(Cipher.ENCRYPT_MODE, key)
                Base64.encodeToString(cipher.doFinal(clearText), Base64.DEFAULT)
            } catch (e: Exception) {
                e.printStackTrace()
                normalStr
            }
        }

        private fun decrypt(encryptStr: String?): String? {
            return try {
                val keySpec = DESKeySpec(ENCRYPT_STRING.toByteArray(charset(CHARSET_NAME)))
                val keyFactory = SecretKeyFactory.getInstance(ALGORITHM)
                val key = keyFactory.generateSecret(keySpec)

                val encrypedPwdBytes = Base64.decode(encryptStr, Base64.DEFAULT)

                val cipher = Cipher.getInstance(ALGORITHM)// cipher is not thread safe
                cipher.init(Cipher.DECRYPT_MODE, key)
                String(cipher.doFinal(encrypedPwdBytes))
            } catch (e: Exception) {
                e.printStackTrace()
                encryptStr
            }
        }
    }

    fun saveString(key: String, token: String) {
        sharedPreferences.edit().putString(key, token).apply()
    }

    fun getString(key: String, defaultValue: String): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun saveListString(key: String, values: Set<String>?) {
        val editor = sharedPreferences.edit()

        if (values != null && values.isNotEmpty()) {
            val list = ArrayList(values)

            if (list.size > 0) {
                for (i in list.indices) {
                    list[i] = encrypt(list[i])
                }
                editor.putStringSet(key, HashSet(list))
            }
        }

        editor.putStringSet(key, values)
        editor.apply()
    }

    fun getListString(key: String): List<String> {
        return try {
            val result = ArrayList(sharedPreferences.getStringSet(key, null)!!)

            if (result.size > 0) {
                for (i in result.indices) {
                    result[i] = decrypt(result[i])
                }
            }
            result
        } catch (e: Exception) {
            e.printStackTrace()
            ArrayList()
        }
    }

    fun getListString(key: String, isDecrypt: Boolean): List<String> {
        try {
            val result = ArrayList(sharedPreferences.getStringSet(key, null)!!)

            if (result.size > 0) {
                for (i in result.indices) {
                    if (isDecrypt)
                        result[i] = decrypt(result[i])
                    else
                        result[i] = result[i]
                }
            }

            return result
        } catch (e: Exception) {
            e.printStackTrace()
            return ArrayList()
        }
    }

    fun saveBoolean(key: String, flag: Boolean) {
        sharedPreferences.edit().putBoolean(key, flag).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun saveInt(key: String, number: Int) {
        sharedPreferences.edit().putInt(key,  number).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}
