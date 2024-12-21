package com.jostin.planificadorviaje

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class RequestQueueSingleton private constructor(context: Context) {

    fun addToRequestQueue(stringRequest: StringRequest) {
        requestQueue.add(stringRequest)
    }

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context.applicationContext)

    companion object {
        @Volatile
        private var INSTANCE: RequestQueueSingleton? = null

        fun getInstance(context: Context): RequestQueueSingleton {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: RequestQueueSingleton(context).also { INSTANCE = it }
            }
        }
    }
}
