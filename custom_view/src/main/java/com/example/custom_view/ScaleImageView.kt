package com.example.custom_view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class ScaleImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr)
    , GestureDetector.OnGestureListener
    , GestureDetector.OnDoubleTapListener
    , Runnable {


    val bitmap: Bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.mipmap.image1)
    }
    val bSrcRectSize: Rect by lazy { Rect(0, 0, bitmap.width, bitmap.height) }
    val dBitmapSizeRectF: RectF = RectF()

    //缩放比例
    var overScaleValue = 1f //过度缩放比例
    var scaleValue = 1f
    var scaleWrapperWidthValue = 1f
    var scaleWrapperHeightValue = 1f


    //位移
    var offsetX = 0f
    var offsetY = 0f

    //过度位移
    val overScroller: OverScroller by lazy { OverScroller(context) }

    val gestureDetectorCompat: GestureDetectorCompat by lazy {
        GestureDetectorCompat(
            context,
            this
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        scaleWrapperWidthValue = width / bitmap.width.toFloat()
        scaleWrapperHeightValue = height / bitmap.height.toFloat()

        //image    width/height = view.width/h
        //
        dBitmapSizeRectF.set(
            (width - bitmap.width) / 2f,
            (height - bitmap.height) / 2f,
            (width + bitmap.width) / 2f,
            (height + bitmap.height) / 2f
        )

    }

    override fun onDraw(canvas: Canvas?) {

        canvas?.apply {


            save()
            translate(offsetX, offsetY)
            scale(
                scaleValue * overScaleValue,
                scaleValue * overScaleValue,
                width / 2f,
                height / 2f
            )

            drawBitmap(
                bitmap,
                bSrcRectSize,
                dBitmapSizeRectF,
                null
            )
            restore()

        }

    }

    /**
     * The user has performed a down [MotionEvent] and not performed
     * a move or up yet. This event is commonly used to provide visual
     * feedback to the user to let them know that their action has been
     * recognized i.e. highlight an element.
     *
     * @param e The down motion event
     */
    override fun onShowPress(e: MotionEvent?) {
    }

    /**
     * Notified when a tap occurs with the up [MotionEvent]
     * that triggered it.
     *
     * @param e The up motion event that completed the first tap
     * @return true if the event is consumed, else false
     */
    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    /**
     * Notified when a tap occurs with the down [MotionEvent]
     * that triggered it. This will be triggered immediately for
     * every down event. All other events should be preceded by this.
     *
     * @param e The down motion event.
     */
    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    /**
     * Notified of a fling event when it occurs with the initial on down [MotionEvent]
     * and the matching up [MotionEvent]. The calculated velocity is supplied along
     * the x and y axis in pixels per second.
     *
     * @param e1 The first down motion event that started the fling.
     * @param e2 The move motion event that triggered the current onFling.
     * @param velocityX The velocity of this fling measured in pixels per second
     * along the x axis.
     * @param velocityY The velocity of this fling measured in pixels per second
     * along the y axis.
     * @return true if the event is consumed, else false
     */
    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {

        overScroller.fling(
            offsetX.toInt(), offsetY.toInt(),
            velocityX.toInt(), velocityY.toInt(),
            (-abs((bitmap.width * scaleValue * overScaleValue - width)) / 2f).toInt(),
            (abs((bitmap.width * scaleValue * overScaleValue - width)) / 2f).toInt(),
            (-abs(bitmap.height * scaleValue * overScaleValue - height) / 2f).toInt(),
            (abs(bitmap.height * scaleValue * overScaleValue - height) / 2f).toInt()
        )

        ViewCompat.postOnAnimation(this, this)

        return false
    }

    /**
     * Notified when a scroll occurs with the initial on down [MotionEvent] and the
     * current move [MotionEvent]. The distance in x and y is also supplied for
     * convenience.
     *
     * @param e1 The first down motion event that started the scrolling.
     * @param e2 The move motion event that triggered the current onScroll.
     * @param distanceX The distance along the X axis that has been scrolled since the last
     * call to onScroll. This is NOT the distance between `e1`
     * and `e2`.
     * @param distanceY The distance along the Y axis that has been scrolled since the last
     * call to onScroll. This is NOT the distance between `e1`
     * and `e2`.
     * @return true if the event is consumed, else false
     */
    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {

        offsetX -= distanceX
        offsetY -= distanceY

        offsetX = max(-abs((bitmap.width * scaleValue * overScaleValue - width)) / 2f, offsetX)
        offsetX = min(abs((bitmap.width * scaleValue * overScaleValue - width)) / 2f, offsetX)

        offsetY = max(-abs(bitmap.height * scaleValue * overScaleValue - height) / 2, offsetY)
        offsetY = min(abs(bitmap.height * scaleValue * overScaleValue - height) / 2, offsetY)

        invalidate()

        return false
    }

    /**
     * Notified when a long press occurs with the initial on down [MotionEvent]
     * that trigged it.
     *
     * @param e The initial on down motion event that started the longpress.
     */
    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetectorCompat.onTouchEvent(event)
    }

    /**
     * When an object implementing interface `Runnable` is used
     * to create a thread, starting the thread causes the object's
     * `run` method to be called in that separately executing
     * thread.
     *
     *
     * The general contract of the method `run` is that it may
     * take any action whatsoever.
     *
     * @see java.lang.Thread.run
     */
    override fun run() {
        if (overScroller.computeScrollOffset()) {
            offsetX = overScroller.currX.toFloat()
            offsetY = overScroller.currY.toFloat()
            invalidate()
            ViewCompat.postOnAnimation(this, this)
        }

    }

    /**
     * Notified when a double-tap occurs.
     *
     * @param e The down motion event of the first tap of the double-tap.
     * @return true if the event is consumed, else false
     */
    override fun onDoubleTap(e: MotionEvent?): Boolean {

        println("position ${e?.x}  ${e?.y} ")

        offsetX = 0f
        offsetY = 0f
//        e?.let {
//            offsetX = it.x - width / 2
//            offsetY = it.y - height / 2
//        }

        if (scaleValue == scaleWrapperWidthValue) {
            scaleValue = scaleWrapperHeightValue
        } else {
            scaleValue = scaleWrapperWidthValue
        }

        invalidate()
        return false
    }

    /**
     * Notified when an event within a double-tap gesture occurs, including
     * the down, move, and up events.
     *
     * @param e The motion event that occurred during the double-tap gesture.
     * @return true if the event is consumed, else false
     */
    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }

    /**
     * Notified when a single-tap occurs.
     *
     *
     * Unlike [OnGestureListener.onSingleTapUp], this
     * will only be called after the detector is confident that the user's
     * first tap is not followed by a second tap leading to a double-tap
     * gesture.
     *
     * @param e The down motion event of the single-tap.
     * @return true if the event is consumed, else false
     */
    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }

}
