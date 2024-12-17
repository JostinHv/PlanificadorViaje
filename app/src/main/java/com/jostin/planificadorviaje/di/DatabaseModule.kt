package com.jostin.planificadorviaje.di

import com.jostin.planificadorviaje.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    fun provideUserDao(database: AppDatabase) = database.userDao()

    @Provides
    fun provideItineraryDao(database: AppDatabase) = database.itineraryDao()

    @Provides
    fun providePlaceDao(database: AppDatabase) = database.placeDao()

    @Provides
    fun provideHotelDao(database: AppDatabase) = database.hotelDao()

    @Provides
    fun provideReservaDao(database: AppDatabase) = database.reservaDao()
}
