package com.gautamgelani.nasaopenapiimages.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

object Utils {

    private lateinit var toast: Toast

    inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
        crossinline bindingInflater: (LayoutInflater) -> T
    ) = lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

    fun showToast(context: Context?, message: String?) { // change it like awk
        if (message.isNullOrEmpty() || context == null) return
        if (Utils::toast.isInitialized) toast.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}
