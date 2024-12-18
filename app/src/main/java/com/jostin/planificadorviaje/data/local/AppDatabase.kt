package com.jostin.planificadorviaje.data.local

// data/local/AppDatabase.kt
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jostin.planificadorviaje.data.local.dao.HotelDao
import com.jostin.planificadorviaje.data.local.dao.ItineraryDao
import com.jostin.planificadorviaje.data.local.dao.PlaceDao
import com.jostin.planificadorviaje.data.local.dao.PlanDao
import com.jostin.planificadorviaje.data.local.dao.ReservaDao
import com.jostin.planificadorviaje.data.local.dao.UserDao
import com.jostin.planificadorviaje.data.model.Hotel
import com.jostin.planificadorviaje.data.model.Itinerary
import com.jostin.planificadorviaje.data.model.Place
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.data.model.Reserva
import com.jostin.planificadorviaje.data.model.User

@Database(
    entities = [Itinerary::class, Plan::class, User::class, Place::class, Hotel::class, Reserva::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itineraryDao(): ItineraryDao
    abstract fun planDao(): PlanDao
    abstract fun userDao(): UserDao
    abstract fun placeDao(): PlaceDao
    abstract fun hotelDao(): HotelDao
    abstract fun reservaDao(): ReservaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "planificador_viaje_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}



