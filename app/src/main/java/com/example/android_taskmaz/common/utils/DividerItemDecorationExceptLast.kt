package com.example.android_taskmaz.common.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecorationExceptLast(
    context: Context
    , private val mDivider: Drawable, private var margin_in_dp: Int = -1)
    : RecyclerView.ItemDecoration()
{
    init
    {
        if (margin_in_dp == -1)
            margin_in_dp = convertDpToPixel(context, 26f).toInt()
        else
            margin_in_dp = convertDpToPixel(context, margin_in_dp.toFloat()).toInt()
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State)
    {
        val divider_left = margin_in_dp
        val divider_right = parent.width - divider_left

        for (i in 0 until parent.childCount)
        {
            // draws divider for every Views except last Item;
            if (i != parent.childCount - 1)
            {
                val child = parent.getChildAt(i)

                val divider_top =
                    child.bottom + (child.layoutParams as RecyclerView.LayoutParams).bottomMargin
                val divider_bottom = divider_top + mDivider.intrinsicHeight

                mDivider.setBounds(divider_left, divider_top, divider_right, divider_bottom)
                mDivider.draw(canvas)
            }
        }
    }

    fun convertDpToPixel(context: Context, dp: Float): Float =
        dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

    fun convertPixelsToDp(context: Context, px: Float): Float =
        px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

}