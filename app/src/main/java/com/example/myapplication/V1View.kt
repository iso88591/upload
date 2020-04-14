package com.example.myapplication

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class V1View @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    var progress: Int = 0

    val path: Path by lazy { Path() }

    val paint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 3.dp()
        }
    }

    val effectPaint: Paint by lazy {
        Paint().apply {
            color = Color.BLACK
            style = Paint.Style.FILL
            flags = Paint.ANTI_ALIAS_FLAG
            strokeWidth = 2.dp()
        }
    }

    val circlePaint: Paint by lazy {
        Paint().apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            flags = Paint.ANTI_ALIAS_FLAG
            strokeWidth = 3.dp()
        }
    }

    val centerPoint: PointF by lazy { PointF(500f, 500f) }
    val pointLineLength = 300

    init {
        path.addArc(RectF(100f, 100f, 900f, 900f), 135f, 270f)

        val pathMeasure = PathMeasure()
        pathMeasure.setPath(path, false)

        val effectPath = Path()
        effectPath.addRect(RectF(-1.dp(), 0f, 1.dp(), 45f), Path.Direction.CW)
        effectPaint.pathEffect = PathDashPathEffect(
            effectPath,
            pathMeasure.length.toFloat() / 20,
            -0.5f,
            PathDashPathEffect.Style.ROTATE
        )

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        event?.let {
            if (it.action == MotionEvent.ACTION_DOWN) {
                progress++
                if (progress > 20) {
                    progress = 0
                }
                invalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {

            it.drawPath(path, paint)
            it.drawPath(path, effectPaint)


            //指针圆环
            it.drawCircle(
                centerPoint.x,
                centerPoint.y,
                5f,
                circlePaint
            )


            //指针了
            it.drawLine(
                centerPoint.x,
                centerPoint.y,
                (centerPoint.x + Math.cos(Math.toRadians(getAngleByProgress(progress))) * pointLineLength).toFloat(),
                (centerPoint.x + Math.sin(Math.toRadians(getAngleByProgress(progress))) * pointLineLength).toFloat(),
                paint
            )


        }

    }

    //progress : 0 .. 20
    fun getAngleByProgress(progress: Int): Double {
        return 135f + 270f / 20 * progress.toDouble()
    }

}
