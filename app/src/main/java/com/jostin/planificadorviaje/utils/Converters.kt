package com.jostin.planificadorviaje.utils

import androidx.room.TypeConverter
import com.google.firebase.Timestamp
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import com.jostin.planificadorviaje.model.Plan
import com.jostin.planificadorviaje.model.User
import java.util.Date

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromTimestampToLong(timestamp: Timestamp?): Long? {
        return timestamp?.toDate()?.time
    }

    @TypeConverter
    fun fromLongToTimestamp(time: Long?): Timestamp? {
        return time?.let { Timestamp(Date(it)) }
    }

    @TypeConverter
    fun fromDateToLong(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromLongToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun fromPlanList(plans: List<Plan>?): String {
        return gson.toJson(plans)
    }

    @TypeConverter
    fun toPlanList(data: String?): List<Plan> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<Plan>>() {}.type
        return try {
            gson.fromJson(data, listType)
        } catch (e: JsonParseException) {
            throw IllegalStateException("Error deserializando lista de planes: ${e.message}")
        }
    }

    @TypeConverter
    fun fromUserList(users: List<User>?): String {
        return gson.toJson(users)
    }

    @TypeConverter
    fun toUserList(data: String?): List<User> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromMap(value: Map<String, Any>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMap(data: String?): Map<String, Any> {
        if (data.isNullOrEmpty()) return emptyMap()
        val mapType = object : TypeToken<Map<String, Any>>() {}.type
        return gson.fromJson(data, mapType)
    }

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

}
