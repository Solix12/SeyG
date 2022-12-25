package uz.salikhdev.seyg.core.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt

fun Activity.SetItemStatusBarColor(@ColorInt color: Int, darkStatusBarTint: Boolean) {

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return

    val window: Window = (this.window).also {
        it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        it.statusBarColor = color
    }

    val decor = window.decorView
    if (darkStatusBarTint) {
        decor.systemUiVisibility = decor.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        decor.systemUiVisibility = 0
    }

}
