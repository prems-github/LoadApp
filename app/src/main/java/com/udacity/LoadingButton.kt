package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //custom attributes
    private var backGroundColor = 0
    private var textColor = 0
    private var backgroundAnimationColor = 0


    // variable for drawing primitive shapes
    private var widthSize = 0f
    private var widthAnim = 0f
    private var heightSize = 0f
    private var sweepAngle = 0f
    lateinit var rectF: RectF

    private var statusText: String


    private var valueAnimator = ValueAnimator()

    // onDraw called on state change
    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { _, oldValue, newValue ->
        if (newValue == ButtonState.Loading) {
            statusText = context.getString(R.string.downloading_text)
            loadingAnimation()
            valueAnimator.start()
        } else {
            statusText = context.getString(R.string.download_text)
            invalidate()
        }
    }

    /*
    * making button into clickable and caching custom attributes*/

    init {
        isClickable = true
        statusText = context.getString(R.string.download_text)
        context.withStyledAttributes(attrs, R.styleable.LoadingButton) {
            backGroundColor = getColor(R.styleable.LoadingButton_backgroundColor, 0)
            textColor = getColor(R.styleable.LoadingButton_textColor, 0)
            backgroundAnimationColor =
                getColor(R.styleable.LoadingButton_backgroundAnimationColor, 0)
        }
    }

    private val paintRect: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = backGroundColor
    }

    private val paintRectAnim: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = backgroundAnimationColor
    }

    private val paintArc: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.colorAccent)
    }

    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 60f
        color = textColor
    }

//initialization of value animator for animating rectangle width and circle
    private fun loadingAnimation() {
        valueAnimator = ValueAnimator.ofFloat(0f, widthSize).apply {
            duration = 1500
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            addUpdateListener {
                widthAnim = it.animatedValue as Float
                sweepAngle = it.animatedValue as Float / 2
                invalidate()
            }
        }

    }

    //helper functions to notify start and stop animation
    fun startDownload() {
        buttonState = ButtonState.Loading
    }

    fun endDownload() {
        buttonState = ButtonState.Completed
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        widthSize = w.toFloat()
        heightSize = h.toFloat()
        rectF = RectF(widthSize * 0.7f, heightSize * .3f, widthSize * .8f, heightSize * .8f)
        invalidate()
    }

   /* draws background, text and circle
   *  with calculated positions*/

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(0f, 0f, widthSize, heightSize, paintRect)
        canvas?.drawText(statusText, widthSize / 3, heightSize / 1.5f, paintText)
        if (buttonState == ButtonState.Loading) {

            canvas?.drawRect(0f, 0f, widthAnim, heightSize, paintRectAnim)
            canvas?.drawArc(rectF, 0f, sweepAngle, true, paintArc)
            canvas?.drawText(statusText, widthSize / 3, heightSize / 1.5f, paintText)
        }
    }
}