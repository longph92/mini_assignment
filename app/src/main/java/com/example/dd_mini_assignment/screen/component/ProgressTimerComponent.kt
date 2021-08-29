package com.example.dd_mini_assignment.screen.component

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.dd_mini_assignment.R
import com.example.dd_mini_assignment.domain.model.OrderModel
import java.util.concurrent.TimeUnit

@BindingAdapter("app:bindingProgressComponent")
fun bindingProgressComponent(view: ProgressTimerComponent, orderModel: OrderModel) {
    view.setOrder(orderModel)
}

class ProgressTimerComponent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var total = 0
    private var current = 0
    private var order: OrderModel? = null
    private var mWidth = 0
    private var timeTrigger = false
    private val paintRectangleGrey = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.parseColor("#7E7E7E")
    }
    private val paintRectangleBlue = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.orange)
    }
    private val corners = floatArrayOf(
        10f, 10f, // Top left radius in px
        10f, 10f, // Top right radius in px
        10f, 10f, // Bottom right radius in px
        10f, 10f, // Bottom left radius in px
    )
    private var space = 0

    fun setOrder(orderModel: OrderModel) {
        order = orderModel
        total =
            ((orderModel.expiredAtInMilliseconds - orderModel.createdAtInMilliseconds) / 60000).toInt()
        invalidate()
    }

    fun updateProgress(currentTime: Long) {
        val timeDiff = currentTime - (order?.createdAtInMilliseconds ?: 0L)
        val min = TimeUnit.MINUTES.convert(timeDiff, TimeUnit.MILLISECONDS).toInt()

        timeTrigger = timeDiff > 0

        if (min > current) {
            current = min
            invalidate()
        }
    }

    fun timeOut() {
        current = total
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        space = resources.getDimensionPixelSize(R.dimen._3sdp)
        canvas?.let {
            canvas.save()
            (0 until total).forEach { index ->
                drawRectangle(canvas, index)
            }
            canvas.restore()
        }
    }

    private fun drawRectangle(
        canvas: Canvas,
        index: Int
    ) {
        mWidth = canvas.width / total - space
        val startX = (mWidth * index).toFloat() + space*index
        val endX = startX + mWidth
        val rect = RectF(startX, 0f, endX, canvas.height.toFloat())
        val path = Path().apply { addRoundRect(rect, corners, Path.Direction.CW) }
        val paint = if (index < current) paintRectangleGrey else paintRectangleBlue
        canvas.drawPath(path, paint)
    }
}