package com.example.custom_view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class AnimatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    val bitmap: Bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
    }
    val bitmapSrcRect: Rect by lazy {
        Rect(0, 0, bitmap.width, bitmap.height)
    }

    val drawXOffset = 100
    val drawYOffset = 200
    val drawSizeScale = 4f
    val dBitmapRect: RectF by lazy {
        RectF(
            0f + drawXOffset,
            0f + drawYOffset,
            bitmap.width * drawSizeScale + drawXOffset,
            bitmap.height * drawSizeScale + drawYOffset
        )
    }

   var canvasRotateValue = 0f
        set(value) {
            field = value
            invalidate()
        }
    var cameraXRotateValue = 0f
        set(value) {
            field = value
            invalidate()
        }
    val camera: Camera by lazy {
        Camera().apply {

            this.setLocation(0f, 0f, -6 * Resources.getSystem().displayMetrics.density)
        }
    }

    init {


//        ObjectAnimator.ofFloat(this, "canvasRotateValue", 0f, 20f).apply {
//            this.duration = 3000
//            this.startDelay = 1000
//            start()
//        }

        ObjectAnimator.ofFloat(this, "cameraXRotateValue", 0f, 20f).apply {
            this.duration = 3000
            this.startDelay = 1000
            start()
        }

//        AnimatorSet().apply {
//            startDelay = 1000
//            playTogether(
//                ObjectAnimator.ofFloat(this, "canvasRotateValue", 0f, 20f).apply {
//                    this.duration = 3000
//                },
//                ObjectAnimator.ofFloat(this, "cameraXRotateValue", 0f, 20f).apply {
//                    this.duration = 3000
//                }
//            )
//
//            start()
//
//        }

    }

    override fun onDraw(canvas: Canvas?) {

        canvas?.apply {

            //top
            save()
            translate(
                dBitmapRect.left + dBitmapRect.width() / 2f,
                dBitmapRect.top + dBitmapRect.height() / 2f
            )

            rotate(-canvasRotateValue)

            clipRect(
                -width,
                -height,
                width,
                0
            )

            camera.rotateX(cameraXRotateValue)
            camera.applyToCanvas(this)

            rotate(canvasRotateValue)

            translate(
                -dBitmapRect.left - dBitmapRect.width() / 2f,
                -dBitmapRect.top - dBitmapRect.height() / 2f
            )
            drawBitmap(
                bitmap,
                bitmapSrcRect,
                dBitmapRect,
                null
            )
            restore()


            //bottom
            save()
            translate(
                +dBitmapRect.left + dBitmapRect.width() / 2,
                +dBitmapRect.top + dBitmapRect.height() / 2
            )

            rotate(-canvasRotateValue)

            clipRect(
                -width,
                0,
                width,
                height
            )

            camera.rotateX(cameraXRotateValue)
            camera.applyToCanvas(this)

            rotate(canvasRotateValue)

            translate(
                -dBitmapRect.left - dBitmapRect.width() / 2,
                -dBitmapRect.top - dBitmapRect.height() / 2
            )
            drawBitmap(
                bitmap,
                bitmapSrcRect,
                dBitmapRect,
                null
            )
            restore()


        }


    }


}
