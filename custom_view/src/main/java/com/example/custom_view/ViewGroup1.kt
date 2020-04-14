package com.example.custom_view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import java.util.ArrayList
import kotlin.math.max

class ViewGroup1 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    val rectList: ArrayList<Rect> by lazy { ArrayList<Rect>() }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var widthSum: Int = 0
        var heightSum: Int = 0
        var everyLineUsedWidth: Int = 0
        var everyLineUsedHeight: Int = 0

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightValue = MeasureSpec.getSize(widthMeasureSpec)

        for (i in 0 until childCount) {
            val childView = getChildAt(i)

            var rect: Rect = if (i >= rectList.size) {
                Rect().apply { rectList.add(this) }
            } else {
                rectList[i];
            }

            //测量 子view
            measureChildWithMargins(
                childView,
                widthMeasureSpec,
                0,
                heightMeasureSpec,
                heightSum
            )

            //如果超出边界（不是MeasureSpec.UnU） 得换行
            if (widthMode != MeasureSpec.UNSPECIFIED && everyLineUsedWidth + childView.measuredWidth > heightValue) {
                heightSum += everyLineUsedHeight
                everyLineUsedHeight = 0
                everyLineUsedWidth = 0

                //测量 子view
                measureChildWithMargins(
                    childView,
                    widthMeasureSpec,
                    0,
                    heightMeasureSpec,
                    heightSum
                )
            }

            rect.set(
                everyLineUsedWidth,
                heightSum,
                everyLineUsedWidth + childView.measuredWidth,
                heightSum + childView.measuredHeight
            )
            everyLineUsedWidth += childView.measuredWidth
            widthSum = max(widthSum, everyLineUsedWidth)

            everyLineUsedHeight = max(everyLineUsedHeight, childView.measuredHeight)

        }

        heightSum += everyLineUsedHeight

        //我的宽高
        setMeasuredDimension(widthSum, heightSum)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val rect = rectList[i]
            getChildAt(i).layout(rect.left, rect.top, rect.right, rect.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }


    /***
     *
     * viewGroup method
     *
     *
     */

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        println("===onInterceptTouchEvent")
        ev?.apply {
            return when (actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    true
                }
                else -> {
                    false
                }
            }
        }

        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        println("===onTouchEvent")

        event?.apply {
            when (actionMasked) {
                MotionEvent.ACTION_DOWN -> {

                }

            }

        }

        return super.onTouchEvent(event)
    }


}
