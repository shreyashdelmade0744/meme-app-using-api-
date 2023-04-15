package com.example.memeforyouth

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
//import android.view.textclassifier.ConversationActions.Request
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

private val url="https://meme-api.com/gimme"
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.memeimage)
        loadimage()
    }

    private fun loadimage() {


// Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
// Request a string response from the provided URL.
        val request = JsonObjectRequest(Request.Method.GET, this.url, null,
            { response ->
                Log.d("result",response.toString())
                Picasso.get().load(response.get("url").toString()).into(imageView)
            },
            {
                Log.e("error" , it.toString())
                Toast.makeText(applicationContext,"Error in loading the meme from server",Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        queue.add(request)


    }

    fun nextt(view: View) {
        loadimage()
    }

    fun sharee(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,url)
        val chooser = Intent.createChooser(intent,"share using ...")
        startActivity(chooser)
    }
    fun download( view :View){
        val down = DownloadManager.Request(Uri.parse(url))
            .setTitle("Meme")
            .setDescription("Download Meme")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)

        val dm= getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(down)

    }
}