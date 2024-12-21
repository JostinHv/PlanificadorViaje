package com.jostin.planificadorviaje.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat

class ImagenUtils {

    companion object {
        fun vectorToBitmap(vectorResId: Int, context: Context): Bitmap {
            val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
                ?: throw IllegalArgumentException("El recurso vectorial no se encontró.")
            vectorDrawable.setBounds(
                0,
                0,
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight
            )
            val bitmap = Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            vectorDrawable.draw(canvas)
            return bitmap
        }
    }

}