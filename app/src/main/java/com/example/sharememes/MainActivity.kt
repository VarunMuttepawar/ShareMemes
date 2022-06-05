package com.example.sharememes

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.json.JSONException
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var current_image_url:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Loadmeme()
    }

    fun ShareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey!! Check out this cool meme $current_image_url")
        val chooser = Intent.createChooser(intent,"Share using ......")
        startActivity(chooser)
    }
    fun NextMeme(view: View) {Loadmeme()}
    fun Loadmeme(){
        progres.visibility =  View.VISIBLE

        val url = "https://meme-api.herokuapp.com/gimme"
        val JsonRequest = JsonObjectRequest(Request.Method.GET, url,null,
       Response.Listener { response -> current_image_url = response.getString("url")
                         Glide.with(this).load(current_image_url).listener(object : RequestListener<Drawable> {
                             override fun onLoadFailed(
                                 e: GlideException?,
                                 model: Any?,
                                 target: Target<Drawable>?,
                                 isFirstResource: Boolean
                             ): Boolean {
                                 progres.visibility =  View.GONE
                                 return false
                             }

                             override fun onResourceReady(
                                 resource: Drawable?,
                                 model: Any?,
                                 target: Target<Drawable>?,
                                 dataSource: DataSource?,
                                 isFirstResource: Boolean
                             ): Boolean {
                                 progres.visibility =  View.GONE
                                 return false
                             }
                         }).into(meme)},
       Response.ErrorListener { Log.d("Fail ho gaya",it.localizedMessage) })

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(JsonRequest)
    }
}