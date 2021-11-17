package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface NeoWSApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(@Query("api_key") apiKey: String): String

    @GET("planetary/apod")
    suspend fun getPictureOfDay(@Query("api_key") apiKey: String) : PictureOfDay
}

object AsteroidApi {

    // Client to solve timeout in network connection
    private val client: OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val asteroidRetrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val pictureOfDayRetrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val asteroidService : NeoWSApiService by lazy  {
        asteroidRetrofit.create(NeoWSApiService::class.java)
    }

    val pictureOfDayService : NeoWSApiService by lazy {
        pictureOfDayRetrofit.create(NeoWSApiService::class.java)
    }

    /*fun getAsteroids(apiKey: String) : List<Asteroid> {
        var responseString : String
        retrofitService.getAsteroids(apiKey).enqueue( object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("ApiService", "Failure: " + t.message)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                responseString = "" + response.body()
                Log.i("ApiService", responseString)
            }
        })
        //val parseResponse = JSONObject(response)
        //return parseAsteroidsJsonResult(parseResponse)
        val fakeAsteroidList: List<Asteroid> = listOf(
            Asteroid(
                id = 1L,
                codename = "Asteroid 1",
                closeApproachDate = "today",
                absoluteMagnitude = 1.0,
                estimatedDiameter = 7.2,
                relativeVelocity = 3.4,
                distanceFromEarth = 750000.0,
                isPotentiallyHazardous = true
            ),
            Asteroid(
                id = 2L,
                codename = "Asteroid 2",
                closeApproachDate = "tomorrow",
                absoluteMagnitude = 2.3,
                estimatedDiameter = 4.7,
                relativeVelocity = 1.75,
                distanceFromEarth = 100050000.0,
                isPotentiallyHazardous = false
            )
        )

        return fakeAsteroidList
    }*/

}