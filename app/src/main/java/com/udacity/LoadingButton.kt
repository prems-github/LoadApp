package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0f
    private var widthAnim = 0f
    private var heightSize = 0f
    lateinit var statusText: String
    lateinit var rectF: RectF
    private var sweepAngle = 0f

    private var valueAnimator = ValueAnimator()

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

    private val paintRect: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.colorPrimary)
    }

    private val paintRectAnim: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.colorPrimaryDark)
    }

    private val paintArc: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.colorAccent)
    }

    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 60f
        color = Color.WHITE
    }

    init {
        isClickable = true
        statusText = context.getString(R.string.download_text)
    }

    private fun loadingAnimation() {
        valueAnimator = ValueAnimator.ofFloat(0f, widthSize).apply {
            duration = 3000
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            addUpdateListener {
                widthAnim = it.animatedValue as Float
                sweepAngle = it.animatedValue as Float / 2
                invalidate()
            }
        }

    }

   /* override fun performClick(): Boolean {
       // if (super.performClick()) return true
        buttonState = if (buttonState == ButtonState.Completed) ButtonState.Loading else ButtonState.Completed
        return true

    }*/

    fun startDownload(){
        buttonState=ButtonState.Loading
    }

    fun endDownload(){
        buttonState=ButtonState.Completed
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        widthSize = w.toFloat()
        heightSize = h.toFloat()
        rectF = RectF(widthSize * 0.7f, heightSize * .3f, widthSize * .8f, heightSize * .8f)
        invalidate()
    }

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

    /*  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
          val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
          val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
          val h: Int = resolveSizeAndState(
              MeasureSpec.getSize(w),
              heightMeasureSpec,
              0
          )
          widthSize = w
          heightSize = h
          setMeasuredDimension(w, h)
      }
  */
}