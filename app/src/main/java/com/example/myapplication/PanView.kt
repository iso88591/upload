package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class PanView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    val panColors = arrayOf(
        "#FF2D2D",
        "#F2FF6E",
        "#FF5DE5",
        "#4831FF"
    )

    val angle = arrayOf(
        40,
        80,
        100,
        140
    )

    val arcPaint: Paint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            style = Paint.Style.FILL
            strokeWidth = 2.dp()
        }
    }

    init {


    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            var startAngle = 0f
            for (i in 0 until panColors.size) {

                arcPaint.color = Color.parseColor(panColors[i])
                save()
                if (i == 2) {
                    translate(
                        (Math.cos(Math.toRadians(startAngle.toDouble() + angle[i] / 2)) * 30).toFloat(),
                        (Math.sin(Math.toRadians(startAngle.toDouble() + angle[i] / 2)) * 30).toFloat()
                    )
                }
                drawArc(
                    RectF(100f, 100f, 500f, 500f),
                    startAngle,
                    angle[i].toFloat(),
                    true,
                    arcPaint
                )
                restore()

                startAngle += angle[i]

            }
        }


    }

}
