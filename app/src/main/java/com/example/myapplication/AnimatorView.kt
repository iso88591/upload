package com.example.myapplication

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class AnimatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    val bitmapSizeScale = 4f
    val bitmap: Bitmap by lazy {
        BitmapFactory.decodeResource(
            resources,
            R.mipmap.ic_launcher,
            null
        )
    }

    val targetBitmapSrcRect: Rect by lazy {
        Rect(0, 0, bitmap.width * bitmapSizeScale.toInt(), bitmap.height * bitmapSizeScale.toInt())
    }

    val targetBitmapDrawRectF: RectF by lazy {
        RectF(
            0f + 100,
            0f + 200,
            targetBitmapSrcRect.width().toFloat() + 100,
            targetBitmapSrcRect.height().toFloat() + 200
        )
    }

    val camera: Camera by lazy {
        Camera().apply {
            this.setLocation(0f, 0f, (-6 * Resources.getSystem().displayMetrics.density))
        }
    }


    //annimator field
    var bottomDeg = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {

        ObjectAnimator.ofFloat(this,"bottomDeg",0f,360f).apply {
            startDelay=1000
            duration = 5000
            start()
        }


    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {

            //top
            save()
            translate(
                +targetBitmapDrawRectF.left+targetBitmapDrawRectF.width()/2,
                +targetBitmapDrawRectF.top+targetBitmapDrawRectF.height()/2
            )
            rotate(-bottomDeg)

            camera.rotateX(bottomDeg)
            camera.applyToCanvas(this)



            clipRect(
                -width,
                -height,
                width,
                0
            )
            rotate(bottomDeg)



            translate(
                -targetBitmapDrawRectF.left-targetBitmapDrawRectF.width()/2,
                -targetBitmapDrawRectF.top-targetBitmapDrawRectF.height()/2
            )

            drawBitmap(
                bitmap,
                targetBitmapSrcRect,
                targetBitmapDrawRectF,
                null
            )
            restore()

            //bottom
//
            save()

            translate(
                +targetBitmapDrawRectF.left+targetBitmapDrawRectF.width()/2,
                +targetBitmapDrawRectF.top+targetBitmapDrawRectF.height()/2
            )
            rotate(-bottomDeg)

            camera.rotateX(-bottomDeg)
            camera.applyToCanvas(this)


            clipRect(
                -width,
                0,
                width,
                height
            )
            rotate(bottomDeg)

            translate(
                -targetBitmapDrawRectF.left-targetBitmapDrawRectF.width()/2,
                -targetBitmapDrawRectF.top-targetBitmapDrawRectF.height()/2
            )

            drawBitmap(
                bitmap,
                targetBitmapSrcRect,
                targetBitmapDrawRectF,
                null
            )
            restore()



        }


    }


}
