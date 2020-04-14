package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet

class NewsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    val measureBounds: Rect = Rect()

    var label = "NEWS"
    val labelTextPaint: Paint by lazy {
        Paint().apply {
            color = Color.WHITE
            textSize = 14.sp()
            flags = Paint.ANTI_ALIAS_FLAG
        }
    }

    val littlePointRadius = 2.dp()
    val littlePointPaint: Paint by lazy {
        Paint().apply {
            color = Color.WHITE
            flags = Paint.ANTI_ALIAS_FLAG
            style = Paint.Style.FILL
//            strokeWidth = 1.dp()
        }
    }

    var alertText = "吸纳是树大根深了多少代理公司的路上了拉德斯基隔离霜"
    val alertTextPaint: Paint by lazy {
        Paint().apply {
            textSize = 16.sp()
            color = Color.WHITE
            flags = Paint.ANTI_ALIAS_FLAG
        }
    }

    init {

        scaleType = ScaleType.CENTER_CROP

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {

            var usedWidth = 30f
            labelTextPaint.getTextBounds(label, 0, label.length, measureBounds)
            drawText(
                label,
                0,
                label.length - 1,
                usedWidth,
                (measureBounds.bottom - measureBounds.top + height) / 2f,
                labelTextPaint
            )



            usedWidth += (measureBounds.right - measureBounds.left)
            //圆点距左
            usedWidth += 10f

            //3个圆点
            for (i in 0..2) {
                drawCircle(
                    usedWidth + 1,
                    (bottom - top) / 2f,
                    littlePointRadius,
                    littlePointPaint
                )
                //2 + 间距
                usedWidth += (littlePointRadius * 2 + 2.dp())
            }

            //文字
            val breakTextNum = alertTextPaint.breakText(
                alertText,
                true,
                width - usedWidth,
                null
            )
            alertTextPaint.getTextBounds(
                alertText,
                0,
                breakTextNum,
                measureBounds
            )
            drawText(
                alertText.substring(0, breakTextNum),
                usedWidth,
                (height + alertTextPaint.textSize) * 0.43f,
                alertTextPaint
            )


        }


    }

}
