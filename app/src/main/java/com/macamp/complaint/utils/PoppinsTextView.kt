package com.macamp.complaint.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class PoppinsTextView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet) : this(
        context = context,
        attrs = attrs,
        defStyleAttr = 0
    ) {
        init()
    }

    constructor(context: Context) : this(
        context = context,
        attrs = null,
        defStyleAttr = 0
    ) {
        init()
    }

    private fun init() {
        val font = Typeface.createFromAsset(context.assets, "poppins_medium.ttf")
        this.typeface = font
    }
}