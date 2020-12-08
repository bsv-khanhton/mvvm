package vn.com.bravesoft.androidapp.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 * Created by AnhVo on 2019-11-18
 */

object JsonUtil {

    private fun buildGson(): Gson {
        return Gson()
    }

    /**
     * Parse Object to String in JSON format
     *
     * @param obj the obj
     * @return String in JSON format
     */
    fun toJson(obj: Any?): String {
        val gson = buildGson()
        return gson.toJson(obj)
    }

    /**
     * Get JSON string and convert to T (Object) you need
     *
     * @param <T>  the type parameter
     * @param json the json
     * @param cls  the cls
     * @return Object filled with JSON string data
     </T> */
    fun <T> fromJson(json: String, cls: Class<T>?): T {
        val gson = buildGson()

        val parser = JsonParser()
        val element = parser.parse(json)

        // check if the Class type is array but the Json is an object
        if (cls != null && cls.isArray && element !is JsonArray) {
            val jsonArray = JsonArray()
            jsonArray.add(element)

            val listType = object : TypeToken<T>() {
            }.type
            return gson.fromJson(jsonArray, listType)
        }

        return gson.fromJson(json, cls!!)
    }

    /**
     * Build object from json and inoke to fields that are marked with @Expose
     * annotation
     *
     * @param <T>  the type parameter
     * @param json the json
     * @param cls  the cls
     * @return t
     </T> */
    fun <T> fromJsonExcludeFields(json: String, cls: Class<T>): T {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

        val parser = JsonParser()
        val element = parser.parse(json)

        // check if the Class type is array but the Json is an object
        if (cls.isArray && element !is JsonArray) {
            val jsonArray = JsonArray()
            jsonArray.add(element)

            val listType = object : TypeToken<T>() {
            }.type
            return gson.fromJson(jsonArray, listType)
        }

        return gson.fromJson(json, cls)
    }

    /**
     * Read json from asset string.
     *
     * @param context  the context
     * @param fileName the file name
     * @return the string
     */
    fun readJsonFromAsset(context: Context, fileName: String): String? {
        val json: String
        try {
            val `is` = context.assets.open(fileName)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }

    /**
     * From json to array t.
     *
     * @param <T>  the type parameter
     * @param json the json
     * @param cls  the cls
     * @return the t
     </T> */
    fun <T> fromJsonToArray(json: String, cls: Class<T>): T? {
        val gson = Gson()
        val listType = object : TypeToken<List<T>>() {
        }.type
        return gson.fromJson<T>(json, listType)
    }

    /**
     * String to array list.
     *
     * @param <T>   the type parameter
     * @param s     the s
     * @param clazz the clazz
     * @return the list
     </T> */
    fun <T> stringToArray(s: String, clazz: Class<Array<T>>): List<T> {
        val arr = Gson().fromJson(s, clazz)
        return listOf(*arr) // or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }
}
