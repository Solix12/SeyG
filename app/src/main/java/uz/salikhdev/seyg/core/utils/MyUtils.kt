package uz.salikhdev.seyg.core.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.setFullScreen() {
    requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
}

fun Fragment.clearFullScreen() {
    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
}

fun Fragment.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


fun View.visible() {
    visibility = VISIBLE
}

fun View.gone() {
    visibility = GONE
}
