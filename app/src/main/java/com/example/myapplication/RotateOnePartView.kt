package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationSet

class RotateOnePartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    var rotateValue = 30f

    val paint: Paint by lazy {
        Paint().apply {
            color = Color.BLUE
        }
    }

    val camera: Camera by lazy {
        Camera().apply {

        }
    }

    init {


    }

    override fun onDraw(canvas: Canvas?) {

        canvas?.apply {


            camera.save()
            camera.rotateY(30f)
            camera.applyToCanvas(canvas)
            drawRect(
                Rect(0, 0, 500, 500),
                paint
            )
            camera.restore()




        }

    }

}
