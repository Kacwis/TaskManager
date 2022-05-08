package com.example.prm_project_1

import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.shapes.RectShape

class PieProgressDrawable(progress: Double) : Drawable() {

    val startAngle: Float = 0.0F

    val paint: Paint = setPaint()

    var boundsF: RectF = RectF()

    var innerBounds: RectF = RectF()

    var mDrawTo: Float = calculateArc(progress)

    override fun draw(canvas: Canvas) {
        canvas.rotate(-90f, bounds.centerX().toFloat(), bounds.centerX().toFloat())
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK
        canvas.drawOval(boundsF, paint)
        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#8600b3")
        canvas.drawArc(innerBounds, startAngle, mDrawTo, true, paint)
    }

    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        innerBounds = RectF(bounds)
        boundsF = RectF(bounds)
        var halfBorder = (paint.strokeWidth/ (2F + 0.5F))
        innerBounds.inset(halfBorder, halfBorder)
    }

    override fun onLevelChange(level: Int): Boolean {
        val drawTo = startAngle + (360*level/100F)
        val update = drawTo != mDrawTo
        mDrawTo = drawTo
        return update
    }

    private fun setPaint(): Paint{
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLUE
        paint.strokeWidth = 5F
        return paint
    }

    private fun calculateArc(progress: Double): Float{
        return (360f * (progress/100)).toFloat()
    }


    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return paint.alpha
    }
}