package com.jostin.planificadorviaje.di

import com.google.firebase.firestore.FirebaseFirestore
import com.jostin.planificadorviaje.data.remote.FirestoreItineraryDataSource
import com.jostin.planificadorviaje.data.remote.FirestorePlanDataSource
import com.jostin.planificadorviaje.data.remote.FirestoreUserDataSource
import com.jostin.planificadorviaje.data.repository.AdminHotelRepository
import com.jostin.planificadorviaje.data.repository.CityRepository
import com.jostin.planificadorviaje.data.repository.ItineraryRepository
import com.jostin.planificadorviaje.data.repository.PlanRepository
import com.jostin.planificadorviaje.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
        remoteDataSource: FirestoreUserDataSource
    ): UserRepository {
        return UserRepository(remoteDataSource)
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
