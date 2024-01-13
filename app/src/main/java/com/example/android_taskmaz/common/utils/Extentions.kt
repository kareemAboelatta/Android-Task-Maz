package com.example.android_taskmaz.common.utils

import android.os.Build
import android.os.Parcelable
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.example.android_taskmaz.R



fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}


fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

fun View.showSnackMsg(msg: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, msg, length).show()
}

fun View.showSnackMsg(msg: Int, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, context.getString(msg), length).show()
}

fun Error.showSnackMsg(view: View) {
    when (this) {
        is Error.ErrorStr -> view.showSnackMsg(this.msg)
        is Error.ErrorInt -> view.showSnackMsg(this.msg)
    }
}

fun <T : Parcelable> Fragment.getParcelableArrayList(
    key: String,
    clazz: Class<T>,
): ArrayList<T>? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
    arguments?.getParcelableArrayList(key, clazz)
else
    arguments?.getParcelableArrayList<T>(key)

fun <T : Parcelable> Fragment.getParcelable(
    key: String,
    clazz: Class<T>,
): T? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
    arguments?.getParcelable(key, clazz)
else
    arguments?.getParcelable<T>(key)




fun <T> RecyclerView.setUp(
    rvAdapter: RecyclerView.Adapter<T>? = null,
    type: LayoutType = LayoutType.LINEAR,
    colCount: Int = 2,
    isDivider: Boolean = false,
    marginDividerInDp: Int = 12,
) where T : RecyclerView.ViewHolder {
    layoutManager = when (type) {
        LayoutType.LINEAR -> LinearLayoutManager(context)
        LayoutType.LINEAR_HORIZONTAL -> LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )
        LayoutType.GRID -> GridLayoutManager(context, colCount)
        LayoutType.STAGGERED_GRID -> StaggeredGridLayoutManager(colCount, RecyclerView.VERTICAL)
        LayoutType.STAGGERED_GRID_HORIZONTAL -> StaggeredGridLayoutManager(
            colCount,
            RecyclerView.HORIZONTAL
        )
    }

    if (isDivider)
        addItemDecoration(
            DividerItemDecorationExceptLast(
                context,
                ResourcesCompat.getDrawable(resources, R.drawable.drawable_divider, null)!!,
                marginDividerInDp
            )
        )

    adapter = rvAdapter
}





