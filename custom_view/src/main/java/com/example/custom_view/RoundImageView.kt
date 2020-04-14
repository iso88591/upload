package com.example.custom_view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import kotlin.math.min

class RoundImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    val rClipRect=RectF()
    val clipPath=Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rClipRect.set(0f,0f,width.toFloat(),height.toFloat())
        clipPath.reset()
        clipPath.addOval(rClipRect,Path.Direction.CW)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthHeight = min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthHeight, widthHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        //使用xfermode达成圆形效果

        canvas?.apply {

            save()
            clipPath(clipPath, Region.Op.INTERSECT)

            super.onDraw(canvas)
            restore()

        }


    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        println("===onTouchEvent RoundImageView")
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        println("===dispatchTouchEvent RoundImageView")
        return super.dispatchTouchEvent(event)
    }

}
