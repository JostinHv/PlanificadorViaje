package com.jostin.planificadorviaje.berpi.di

import com.google.firebase.firestore.FirebaseFirestore
import com.jostin.planificadorviaje.berpi.repository.AdminHotelRepository
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
    fun provideFirestoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideHotelRepository(firestore: FirebaseFirestore): AdminHotelRepository {
        return AdminHotelRepository(firestore)
    }
}