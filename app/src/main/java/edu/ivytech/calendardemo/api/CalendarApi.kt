package edu.ivytech.calendardemo.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface CalendarApi {

    @GET("calendars/{calendarId}/events")
    fun fetchEvents(@Path("calendarId") calendar : String, @QueryMap options : Map<String, String>) : Call<String>
}