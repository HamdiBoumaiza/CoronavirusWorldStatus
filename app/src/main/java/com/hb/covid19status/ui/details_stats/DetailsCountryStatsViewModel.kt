package com.hb.covid19status.ui.details_stats

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class DetailsCountryStatsViewModel @Inject constructor() : ViewModel() {

    fun takeScreenShot(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}