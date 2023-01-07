package com.example.apicalling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.apicalling.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val url: String = "https://meme-api.com/gimme"

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ShareMeme()
        getMemeData()
        NextMeme()


    }


     fun getMemeData() {


// Instantiate the RequestQueue.
         val queue = Volley.newRequestQueue(this)
         val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
         val stringRequest = StringRequest(Request.Method.GET, url,
             { response ->
                Log.e("Response", "getMemeData: " + response.toString())

                 var responseObject = JSONObject(response)


                 responseObject.get("postLink")


                 Glide.with(this@MainActivity).load( responseObject.get("url")).into(binding.memeImage)

             },
             { error ->
                 Toast.makeText(this@MainActivity, "${error.localizedMessage}", Toast.LENGTH_SHORT)
                     .show()
             })

// Add the request to the RequestQueue.
         queue.add(stringRequest)
     }

    fun NextMeme(){
        binding.btnNewMeme.setOnClickListener{
            getMemeData()
        }
    }

    fun ShareMeme() {
        binding.btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Hey Checkout this cool meme I got from Reddit $url")
            val chooser = Intent.createChooser(intent, "Share this meme using...")
            startActivity(chooser)
        }
    }
}