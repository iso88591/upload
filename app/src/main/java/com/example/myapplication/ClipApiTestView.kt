package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class ClipApiTestView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


//    val bitmap: Bitmap by lazy { BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher) }

    val rect = RectF(0f, 0f, 500f, 500f)

    val paint: Paint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            strokeWidth = 3.dp()
        }
    }

    override fun onDraw(canvas: Canvas?) {

        canvas?.apply {

            save()
            val path = Path()
            path.addOval(
                RectF(
                    300f,
                    300f,
                    400f,
                    400f
                ), Path.Direction.CCW
            )
            clipPath(path, Region.Op.DIFFERENCE)
            drawRect(rect, paint)
            restore()

        }

    }

}
