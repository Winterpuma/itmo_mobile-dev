package com.example.mobile_dev

import android.content.res.AssetManager
import android.content.res.Configuration
import android.os.Build
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class JsonHelper {

    fun readJsonCatData(catName: String, assets: AssetManager, configuration: Configuration): Cat {
        val lang: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.locales.get(0).language
        } else {
            configuration.locale.language
        }
        val text: String =
            JsonHelper().loadData("catData-$lang.json", assets) ?:
            JsonHelper().loadData("catData.json", assets)!!
        val ob = JSONObject(text)
        val cat = ob.getJSONObject(catName)

        return Cat(
            name = catName,
            description = cat.getString("description"),
            imgPath = cat.getString("img"))
    }

    private fun loadData(inFile: String, assets: AssetManager): String? {
        val tContents: String?

        try {
            val stream: InputStream = assets.open(inFile)
            val size: Int = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            tContents = String(buffer)
        } catch (e: IOException) {
            return null
        }

        return tContents
    }
}