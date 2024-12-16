package com.jostin.planificadorviaje.data.model

import androidx.annotation.DrawableRes
import com.jostin.planificadorviaje.R

enum class PlanType(@DrawableRes val iconRes: Int, val nameRes: Int) {
    FLIGHT(R.drawable.ic_flight, R.string.plan_type_flight),
    ACCOMMODATION(R.drawable.ic_hotel, R.string.plan_type_accommodation),
    CAR_RENTAL(R.drawable.ic_car, R.string.plan_type_car_rental),
    MEETING(R.drawable.ic_meeting, R.string.plan_type_meeting),
    ACTIVITY(R.drawable.ic_activity, R.string.plan_type_activity),
    RESTAURANT(R.drawable.ic_restaurant, R.string.plan_type_restaurant),
    TRANSPORT(R.drawable.ic_transport, R.string.plan_type_transport),
    PACKAGE_TRIP(R.drawable.ic_package_trip, R.string.plan_type_package_trip)
}
