package com.zoomx.zoomx.ui.requestdetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView

import com.pddstudio.highlightjs.HighlightJsView
import com.pddstudio.highlightjs.models.Language
import com.pddstudio.highlightjs.models.Theme
import com.zoomx.zoomx.R

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener


class JsonViewActivity : AppCompatActivity() {

    internal var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_body)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(R.string.json_viewer_screen_title)

        if (intent != null && intent.extras != null && intent.extras!!.containsKey(RequestDetailsFragment.BODY_URL_KEY)) {
            val url = intent.extras!!.get(RequestDetailsFragment.BODY_URL_KEY) as String
            val body = intent.extras!!.get(RequestDetailsFragment.BODY_JSON_KEY) as String
            initUi(url, body)
        }
    }

    fun initUi(url: String, body: String) {
        val urlTextView = findViewById<TextView>(R.id.body_url_txt)
        urlTextView.text = url
        val highlightJsView = findViewById<View>(R.id.highlight_view) as HighlightJsView
        //change theme and set language to auto detect
        highlightJsView.theme = Theme.MONOKAI
        highlightJsView.highlightLanguage = Language.JSON
        //load the source (can be loaded by String, File or URL)
        highlightJsView.setZoomSupportEnabled(false)
        highlightJsView.setSource(formatJsonPretty(body))
    }

    /**
     * Convert a JSON string to pretty  version
     *
     * @param jsonString
     * @return
     */
    fun formatJsonPretty(jsonString: String): String {
        var json: Any? = null
        try {
            json = JSONTokener(jsonString).nextValue()
            return if (json is JSONObject) {
                json.toString(1)
            } else if (json is JSONArray) {
                json.toString(1)
            } else {
                jsonString
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            return ""
        }

    }
}
