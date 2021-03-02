package com.udacity

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class LoadingButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0f
    private var heightSize = 0f

    // private val valueAnimator = ValueAnimator()

    /*private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

    }*/

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.colorPrimary)
    }

    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 60f
        color = Color.WHITE
        // textAlign=Paint.Align.CENTER
        //typeface= Typeface.create("", Typeface.BOLD)
    }

    init {
        isClickable = true
    }

    override fun performClick(): Boolean {
        return super.performClick()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        widthSize = w.toFloat()
        heightSize = h.toFloat()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d("Canvas", "Canvas ondraw testing")
      //  paint.color = context.getColor(R.color.colorPrimary)
        canvas?.drawRect(0f, 0f, widthSize, heightSize, paint)
        canvas?.drawText("Download", widthSize / 3, heightSize / 1.5f, paintText)
        invalidate()
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