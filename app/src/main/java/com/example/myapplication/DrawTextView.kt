package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class DrawTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val paint: Paint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            color = Color.RED
            style = Paint.Style.STROKE
//            strokeWidth = 3.dp()
            textSize = 80f
        }
    }

    val targetString = """
abcdefghijklmnopqrstuvwxyz
abcdefghijklmnopqrstuvwxyz
abcdefghijklmnopqrstuvwxyz
abcdefghijklmnopqrstuvwxyz
abcdefghijklmnopqrstuvwxyz
abcdefghijklmnopqrstuvwxyz
""".trim()

    val floatArr: FloatArray = FloatArray(2)

    val textBounds = Rect()

    init {


    }

    override fun onDraw(canvas: Canvas?) {

        canvas?.apply {

            var index = 0
            var topDistance = 0f

            while (index < targetString.length) {

                val breakText = paint.breakText(targetString, true, 500f, floatArr)
                println("breakText=${breakText} floatArray=${floatArr[0]} ${floatArr[1]}")
                println("bounds = ${textBounds}")
                var endP = index + breakText
                if (endP >= targetString.length) {
                    endP = targetString.length - 1
                }
                paint.getTextBounds(targetString,index,endP,textBounds)
                topDistance += (textBounds.bottom.toFloat()-textBounds.top)
                drawText(targetString.substring(index, endP), 0f, topDistance, paint)
                index += breakText
            }


        }

    }


}
