package hu.zsof.gitsearchapp.util

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun NavController.safeNavigate(direction: NavDirections, args: Bundle? = null) {
    currentDestination?.getAction(direction.actionId)?.run {
        if (args == null) {
            navigate(direction)
        } else {
            navigate(direction.actionId, args)
        }
    }
}

fun Fragment.safeNavigate(direction: NavDirections, args: Bundle? = null) {
    if (isAdded) {
        findNavController().safeNavigate(direction, args)
    }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}
