package com.example.week_6_project_own_api // Changed package name for clarity



import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
// NO Jetpack Compose or Wear Compose imports here if this is a View-based Activity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException
import org.json.JSONObject
import kotlin.random.Random

// ... rest of your MainActivity class

class MainActivity : AppCompatActivity() {

    private lateinit var apodImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var explanationTextView: TextView
    private lateinit var fetchButton: Button

    private var apodDataList: List<JSONObject> = emptyList() // To store the list of APODs
    private var currentApodIndex = -1 // To keep track of the current APOD being shown

    // NASA APOD API URL
    // Fetches 100 random APOD entries.
    // Replace DEMO_KEY with your actual key for more requests.
    private val nasaApodApiUrl = "https://api.nasa.gov/planetary/apod?count=100&api_key=DEMO_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Make sure your layout file is named activity_main.xml or change this
        setContentView(R.layout.activity_main)

        // Initialize Views
        // Ensure these IDs match your activity_main.xml
        apodImageView = findViewById(R.id.Image_UR_x) // Assuming this is your ImageView ID
        titleTextView = findViewById(R.id.title_x)     // Assuming this is your Title TextView ID
        dateTextView = findViewById(R.id.date_x)       // Assuming this is your Date TextView ID
        explanationTextView = findViewById(R.id.explanation_x) // Assuming this is your Explanation TV ID
        fetchButton = findViewById(R.id.button_x)    // Assuming this is your Button ID

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupButton()
        fetchApodDataList() // Fetch the list of APODs when the activity starts
    }

    private fun fetchApodDataList() {
        val client = AsyncHttpClient()
        client[nasaApodApiUrl, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("NASA_APOD_API", "Response successful: $json")
                try {
                    // The response is a JSON array of APOD objects
                    val jsonArray = json.jsonArray
                    val tempList = mutableListOf<JSONObject>()
                    for (i in 0 until jsonArray.length()) {
                        tempList.add(jsonArray.getJSONObject(i))
                    }
                    apodDataList = tempList
                    Log.d("NASA_APOD_API", "Fetched ${apodDataList.size} APOD entries.")

                    // Display the first APOD from the fetched list
                    if (apodDataList.isNotEmpty()) {
                        currentApodIndex = 0
                        displayApod(apodDataList[currentApodIndex])
                    }

                } catch (e: JSONException) {
                    Log.e("NASA_APOD_API_Parse", "Error parsing JSON array", e)
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.e("NASA_APOD_API_Error", "Status: $statusCode, Response: $errorResponse")
                throwable?.message?.let { Log.e("NASA_APOD_API_Throw", it) }
                // Optionally, show an error message to the user in a TextView
                titleTextView.text = "Error loading data"
                explanationTextView.text = "Could not fetch APOD data. Please check your connection or API key."
            }
        }]
    }

    private fun displayApod(apodJson: JSONObject) {
        try {
            // Only load if it's an image. APOD can also return videos.
            val mediaType = apodJson.optString("media_type", "image")

            if (mediaType == "image") {
                val imageUrl = apodJson.getString("url") // Or "hdurl" for higher resolution
                val title = apodJson.getString("title")
                val date = apodJson.getString("date")
                val explanation = apodJson.getString("explanation")
                // val copyright = apodJson.optString("copyright", "N/A") // Optional

                Log.d("NASA_APOD_Display", "Displaying: $title, URL: $imageUrl")

                titleTextView.text = title
                dateTextView.text = date
                explanationTextView.text = explanation
                // copyrightTextView.text = "Copyright: $copyright" // If you have a copyright TextView

                Glide.with(this@MainActivity)
                    .load(imageUrl)
                    .placeholder(R.drawable.nasa_image) // Optional: a placeholder image
                    .error(R.drawable.nasa_image) // Optional: an image to show on error
                    .fitCenter()
                    .into(apodImageView)
            } else {
                Log.d("NASA_APOD_Display", "Skipping non-image media type: $mediaType - Title: ${apodJson.optString("title")}")
                // Handle non-image types (e.g., show a placeholder or message, or fetch next)
                titleTextView.text = apodJson.optString("title", "Non-Image Content")
                dateTextView.text = apodJson.optString("date", "")
                explanationTextView.text = "This APOD entry is a '$mediaType', not an image. Click button for next."
                apodImageView.setImageResource(R.drawable.nasa_image) // Show placeholder
                // Optionally, automatically fetch the next image
                // showNextApod()
            }
        } catch (e: JSONException) {
            Log.e("NASA_APOD_DisplayErr", "Error extracting data from APOD JSON object", e)
        }
    }


    private fun showNextApod() {
        if (apodDataList.isEmpty()) {
            Log.d("NASA_APOD_Next", "APOD list is empty. Fetching new list.")
            fetchApodDataList() // Attempt to re-fetch if list is empty
            return
        }

        // Increment index and loop back if at the end, or pick randomly
        // currentApodIndex = (currentApodIndex + 1) % apodDataList.size // Sequential
        currentApodIndex = Random.nextInt(apodDataList.size) // Random from the fetched list

        Log.d("NASA_APOD_Next", "Showing APOD at index: $currentApodIndex")
        displayApod(apodDataList[currentApodIndex])
    }

    private fun setupButton() {
        fetchButton.setOnClickListener {
            Log.d("ButtonClick", "Button clicked, showing next APOD.")
            showNextApod()
        }
    }
}
