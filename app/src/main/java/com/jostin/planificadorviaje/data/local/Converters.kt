package com.jostin.planificadorviaje.data.local

// data/local/Converters.kt
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jostin.planificadorviaje.data.model.Plan
import com.jostin.planificadorviaje.data.model.User
import java.util.Date

class Converters {
    private val gson = Gson()


    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromPlanList(plans: List<Plan>?): String? {
        return gson.toJson(plans)
    }

    @TypeConverter
    fun toPlanList(data: String?): List<Plan>? {
        if (data == null) return emptyList()
        val listType = object : TypeToken<List<Plan>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromUserList(users: List<User>?): String? {
        return gson.toJson(users)
    }

    @TypeConverter
    fun toUserList(data: String?): List<User>? {
        if (data == null) return emptyList()
        val listType = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromMap(value: Map<String, Any>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMap(value: String?): Map<String, Any> {
        return if (value == null) {
            emptyMap()
        } else {
            val type = object : TypeToken<Map<String, Any>>() {}.type
            gson.fromJson(value, type)
        }
    }
}