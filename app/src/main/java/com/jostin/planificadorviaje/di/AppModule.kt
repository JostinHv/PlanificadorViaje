package com.jostin.planificadorviaje.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.jostin.planificadorviaje.data.repository.AdminHotelRepository
import com.jostin.planificadorviaje.data.local.AppDatabase
import com.jostin.planificadorviaje.data.local.datasource.FirestoreItineraryDataSource
import com.jostin.planificadorviaje.data.local.datasource.FirestorePlanDataSource
import com.jostin.planificadorviaje.data.local.datasource.FirestoreUserDataSource
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
            ReservaLocalDataSource(database),
            PlanLocalDataSource(database)
        )
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideItineraryRepository(
        remoteDataSource: FirestoreItineraryDataSource
    ): ItineraryRepository {
        return ItineraryRepository(remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideFirestoreItineraryDataSource(
        firestore: FirebaseFirestore
    ): FirestoreItineraryDataSource {
        return FirestoreItineraryDataSource(firestore)
    }

    @Provides
    @Singleton
    fun provideAdminHotelRepository(firestore: FirebaseFirestore): AdminHotelRepository {
        return AdminHotelRepository(firestore)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        localDataSource: LocalDataSource, remoteDataSource: FirestoreUserDataSource
    ): UserRepository {
        return UserRepository(localDataSource, remoteDataSource)
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

    @Singleton
    @Provides
    fun providePlanRepository(
        remoteDataSource: FirestorePlanDataSource
    ): PlanRepository {
        return PlanRepository(remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideFirestorePlanDataSource(
        firestore: FirebaseFirestore
    ): FirestorePlanDataSource {
        return FirestorePlanDataSource(firestore)
    }

    @Singleton
    @Provides
    fun provideCityRepository(): CityRepository {
        return CityRepository()
    }
}
