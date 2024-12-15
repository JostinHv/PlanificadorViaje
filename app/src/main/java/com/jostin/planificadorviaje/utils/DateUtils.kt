package com.jostin.planificadorviaje.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateUtils {
    companion object {
        fun formatDateRange(startDate: Date, endDate: Date): String {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return "${dateFormat.format(startDate)} - ${dateFormat.format(endDate)}"
        }
    }
}