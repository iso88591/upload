package com.example.myapplication

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationSet

class PrograssView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val radius = 200f
    var prograssStrockWidth: Float = 10.dp()
        set(value) {
            field = value
            invalidate()
        }
    private var progress = 0f
        set(value) {
            field = value
            invalidate()
        }
    val progressPaint: Paint by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            flags = Paint.ANTI_ALIAS_FLAG
            color = Color.RED
            strokeWidth = prograssStrockWidth
            //设置线头的形状。线头形状有三种：BUTT 平头、ROUND 圆头、SQUARE 方头。默认为 BUTT。
            strokeCap = Paint.Cap.ROUND
        }
    }


    val bitmap: Bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.test)
    }
    val bitmapPaint: Paint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
        }
    }

    //弹跳偏移量
    var moveHeightOffset = 0f
        set(value) {
            field = value
            invalidate()
        }


    init {

        val progressAnimator = ObjectAnimator.ofFloat(this, "progress", 0f, 100f).apply {
            duration = 5000
        }

        val prograssStrockWidth = ObjectAnimator.ofFloat(
            this, "prograssStrockWidth",
            10f,
            500f
        ).apply {
            duration = 5000
        }

        val moveHeightAnimator = ObjectAnimator.ofFloat(
            this,
            "moveHeightOffset",
            0f,
            150f,
            -50f,
            0f,
            -50f,
            0f
        ).apply {
            duration = 5000
            interpolator = AccelerateDecelerateInterpolator()
        }

        AnimatorSet().apply {

            startDelay = 1000

            playTogether(
                progressAnimator,
                moveHeightAnimator,
                prograssStrockWidth
            )
            start()
        }
//        animator.apply {
//            startDelay = 5000
//            duration = 5000
//            start()
//        }

    }

    override fun onDraw(canvas: Canvas?) {

        canvas?.apply {

            progressPaint.strokeWidth = prograssStrockWidth

            val ringWidthOffst = progressPaint.strokeWidth / 2f

            val layer = saveLayer(
                RectF(
                    width / 2f - radius - ringWidthOffst,
                    height / 2f - radius - ringWidthOffst + moveHeightOffset,
                    width / 2f + radius + ringWidthOffst,
                    height / 2f + radius + ringWidthOffst + moveHeightOffset
                ), null, Canvas.ALL_SAVE_FLAG
            )
            drawArc(
                RectF(
                    width / 2f - radius,
                    height / 2f - radius + moveHeightOffset,
                    width / 2f + radius,
                    height / 2f + radius + moveHeightOffset
                ),
                0f,
                360 * progress / 100f,
                false,
                progressPaint
            )
            bitmapPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            drawBitmap(
                bitmap,
                Rect(0, 0, bitmap.width, bitmap.height),
                RectF(
                    width / 2f - radius - ringWidthOffst,
                    height / 2f - radius - ringWidthOffst + moveHeightOffset,
                    width / 2f + radius + ringWidthOffst,
                    height / 2f + radius + ringWidthOffst + moveHeightOffset
                ),
                bitmapPaint
            )
            bitmapPaint.xfermode = null
            restoreToCount(layer)

        }

    }

}
