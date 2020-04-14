package com.example.myapplication

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View

class SkewView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var xSkewValue: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    var ySkewValue: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    var rotaxAngle = 0.0f
        set(value) {
            field = value
            invalidate()
        }

    val bitmap: Bitmap by lazy { BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher) }

    val paint: Paint by lazy {
        Paint().apply {
            strokeWidth = 3.dp()
            style = Paint.Style.FILL
            color = Color.YELLOW
        }
    }

    val camera: Camera by lazy {
        Camera().apply {
            setLocation(0f, 0f, -6 * resources.displayMetrics.density)
        }
    }

    init {

//        setPadding(0, 0, 0, 0)
//        val animator: ObjectAnimator = ObjectAnimator.ofFloat(this, "xSkewValue", 0f, 1f)
//        animator.apply {
//            startDelay = 5000
//            duration = 5000
//            start()
//
//        }

//        val yanimator: ObjectAnimator = ObjectAnimator.ofFloat(this, "ySkewValue", 0f, 1f)
//        yanimator.apply {
//            startDelay = 5000
//            duration = 5000
//            start()
//
//        }

        val rotateAnimator: ObjectAnimator = ObjectAnimator.ofFloat(
            this, "rotaxAngle",
            0f,
            75f,
            30f,
            80f,
            20f,
            90f,
            0f
        )
        rotateAnimator.apply {
            startDelay = 5000
            duration = 5000
            start()
        }

    }

    override fun onDraw(canvas: Canvas?) {

        canvas?.apply {

            drawBitmap(
                bitmap,
                Rect(0, 0, bitmap.width, bitmap.height / 2),
                RectF(0f, 0f, bitmap.width * 5f, bitmap.height * 2.5f),
                paint
            )

            camera.save()
            translate(
                bitmap.width * 2.5f,
                bitmap.height * 2.5f
            )


            camera.rotateX(rotaxAngle)
            camera.applyToCanvas(canvas)

            translate(
                -bitmap.width * 2.5f,
                -bitmap.height * 2.5f
            )
            drawBitmap(
                bitmap,
                Rect(0, bitmap.height / 2, bitmap.width, bitmap.height),
                RectF(
                    0f,
                    bitmap.height * 2.5f,
                    bitmap.width * 5f,
                    bitmap.height * 5f
                ),
                paint
            )
            camera.restore()

        }

    }

}
