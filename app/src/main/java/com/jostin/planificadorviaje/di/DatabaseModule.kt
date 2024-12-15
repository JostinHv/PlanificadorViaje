package com.jostin.planificadorviaje.di

import android.content.Context
import androidx.room.Room
import com.jostin.planificadorviaje.data.local.AppDatabase
import com.jostin.planificadorviaje.data.local.ItineraryDao
import com.jostin.planificadorviaje.data.local.PlanDao
import com.jostin.planificadorviaje.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "planificador_viaje_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideItineraryDao(database: AppDatabase): ItineraryDao {
        return database.itineraryDao()
    }

    @Provides
    fun providePlanDao(database: AppDatabase): PlanDao {
        return database.planDao()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}
