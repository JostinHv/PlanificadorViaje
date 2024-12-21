package com.jostin.planificadorviaje.data.local.datasource

import com.jostin.planificadorviaje.data.local.datasource.interfaces.HotelDataSource
import com.jostin.planificadorviaje.data.local.datasource.interfaces.ItineraryDataSource
import com.jostin.planificadorviaje.data.local.datasource.interfaces.PlaceDataSource
import com.jostin.planificadorviaje.data.local.datasource.interfaces.PlanDataSource
import com.jostin.planificadorviaje.data.local.datasource.interfaces.ReservaDataSource
import com.jostin.planificadorviaje.data.local.datasource.interfaces.UserDataSource

class LocalDataSource(
    private val itineraryDataSource: ItineraryDataSource,
    private val userDataSource: UserDataSource,
    private val placeDataSource: PlaceDataSource,
    private val hotelDataSource: HotelDataSource,
    private val reservaDataSource: ReservaDataSource,
    private val planDataSource: PlanDataSource

) : ItineraryDataSource by itineraryDataSource,
    UserDataSource by userDataSource,
    PlaceDataSource by placeDataSource,
    HotelDataSource by hotelDataSource,
    ReservaDataSource by reservaDataSource,
    PlanDataSource by planDataSource