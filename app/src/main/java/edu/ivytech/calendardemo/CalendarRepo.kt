package edu.ivytech.calendardemo

import android.content.Context
import android.util.Log
import edu.ivytech.calendardemo.api.CalendarApi
import edu.ivytech.calendardemo.firestore.FirestoreUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class CalendarRepo private constructor (private val context : Context){
    companion object
    {
        private var INSTANCE:CalendarRepo? = null
        fun initialize(context: Context) {
            INSTANCE = CalendarRepo(context)
        }
        fun get():CalendarRepo {
            return INSTANCE?: throw IllegalStateException("Article Repository must be initialized.")
        }
    }
    private val calendarApi: CalendarApi
    init {
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/calendar/v3/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        calendarApi = retrofit.create(CalendarApi::class.java)
    }

    fun fetchEvents() {
        val curDate = Date()
        val timeMin = Date(curDate.time - TimeUnit.MILLISECONDS.convert(30,TimeUnit.DAYS))
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
        val optionsMap = mutableMapOf<String, String>()
        optionsMap["key"] = context.getString(R.string.calAPIKey)
        optionsMap["orderBy"] = "startTime"
        optionsMap["singleEvents"] = "true"
        optionsMap["timeMin"] = format.format(timeMin)
        optionsMap["maxResults"] = "1000"
        val calendarRequest : Call<String> = calendarApi.fetchEvents(context.getString(R.string.calID),optionsMap)
        calendarRequest.enqueue(object : Callback<String> {

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("TAG", "Failed to fetch photos", t)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                val res = response.body()
                if (res != null) {
                    Log.d("TAG", res)
                }
            }
        })
    }

    fun getUsers() {

            FirestoreUtil.getAllUsers().addOnSuccessListener {
                    documents ->
                for(document in documents) {
                    Log.d("TAG", document.get("name") as String)
                    //val fsCrime = document.toObject(FirestoreCrime::class.java)

                       // Log.i("Crime Repo Sync", "Added Crime From Firestore ${crime.title}")
                    }
                }

            }



}