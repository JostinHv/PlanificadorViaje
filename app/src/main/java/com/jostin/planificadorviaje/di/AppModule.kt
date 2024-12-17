package com.jostin.planificadorviaje.di

import android.content.Context
import com.jostin.planificadorviaje.data.local.AppDatabase
import com.jostin.planificadorviaje.data.local.datasource.LocalDataSource
import com.jostin.planificadorviaje.data.local.datasource.implementation.*
import com.jostin.planificadorviaje.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(database: AppDatabase): LocalDataSource {
        return LocalDataSource(
            ItineraryLocalDataSource(database),
            UserLocalDataSource(database),
            PlaceLocalDataSource(database),
            HotelLocalDataSource(database),
            ReservaLocalDataSource(database)
        )
    }

    @Singleton
    @Provides
    fun provideItineraryRepository(localDataSource: LocalDataSource): ItineraryRepository {
        return ItineraryRepository(localDataSource)
    }

    @Singleton
    @Provides
    fun provideUserRepository(localDataSource: LocalDataSource): UserRepository {
        return UserRepository(localDataSource)
    }

    @Singleton
    @Provides
    fun providePlaceRepository(localDataSource: LocalDataSource): PlaceRepository {
        return PlaceRepository(localDataSource)
    }

    @Singleton
    @Provides
    fun provideHotelRepository(localDataSource: LocalDataSource): HotelRepository {
        return HotelRepository(localDataSource)
    }

    @Singleton
    @Provides
    fun provideReservaRepository(localDataSource: LocalDataSource): ReservaRepository {
        return ReservaRepository(localDataSource)
    }
}
