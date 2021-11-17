package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.asDatabaseAsteroids
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.asAsteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class AsteroidsRepository(private val database: AsteroidsDatabase) {

    private fun getToday() : String {
        val calendar = Calendar.getInstance()
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentTime)
    }

    val asteroids : LiveData<List<Asteroid>> = Transformations.map(
        database.asteroidDao.getAsteroids(getToday())) {
        it.asAsteroid()
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val result = AsteroidApi.asteroidService.getAsteroids(Constants.API_KEY)
            val asteroidList = parseAsteroidsJsonResult(JSONObject(result))
            database.asteroidDao.insertAll(*asteroidList.asDatabaseAsteroids())
        }
    }

}
