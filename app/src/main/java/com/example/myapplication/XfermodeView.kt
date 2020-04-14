package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class XfermodeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    val paint: Paint by lazy {
        Paint().apply {
            strokeWidth = 3.dp()
            style = Paint.Style.FILL
            color=Color.YELLOW
        }
    }

    val paint2: Paint by lazy {
        Paint().apply {
            strokeWidth = 3.dp()
            style = Paint.Style.FILL
            color=Color.BLUE
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        /**
         * Canvs.saveLayer() 把绘制区域拉到单独的离屏缓冲里
        绘制 A 图形
        用 Paint.setXfermode() 设置 Xfermode
        绘制 B 图形
        用 Paint.setXfermode(null) 恢复 Xfermode
        用 Canvas.re
         */
        canvas?.apply {

            val layer: Int = saveLayer(RectF(0f, 0f, 500f, 500f), paint, Canvas.ALL_SAVE_FLAG)

            drawRect(0f,0f,300f,300f,paint)

            paint2.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            drawRect(200f,200f,500f,500f,paint2)

            restoreToCount(layer)

        }


    }


}
