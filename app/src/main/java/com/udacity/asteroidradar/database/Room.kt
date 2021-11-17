package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {

    @Query("select * from databaseAsteroid ORDER BY closeApproachDate ASC")
    fun getAllAsteroids() : LiveData<List<DatabaseAsteroid>>

    @Query("select * from databaseAsteroid WHERE closeApproachDate >= :today ORDER BY closeApproachDate ASC")
    fun getAsteroids(today: String) : LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)
}

@Database(entities = [DatabaseAsteroid::class], version = 1)
abstract class AsteroidsDatabase : RoomDatabase() {

    abstract val asteroidDao : AsteroidDao

}

private lateinit var INSTANCE : AsteroidsDatabase

fun getDatabase(context: Context) : AsteroidsDatabase {
    synchronized(AsteroidsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            AsteroidsDatabase::class.java,
            "asteroidDB").build()
        }
    }
    return INSTANCE
}
