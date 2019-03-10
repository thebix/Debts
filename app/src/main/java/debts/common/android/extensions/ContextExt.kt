package okb.common.android.extension

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.view.View
import timber.log.Timber

// region Resources
///////////////////////////////////////////////////////////////////////////

@ColorInt
fun Context.getColorCompat(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Context.getDimensionCompat(@DimenRes resId: Int): Float = resources.getDimension(resId)

/**
 * Size conversion involves rounding the base value, and ensuring that a non-zero base value
 * is at least one pixel in size.
 */
fun Context.getDimensionPixelSizeCompat(@DimenRes resId: Int): Int = resources.getDimensionPixelSize(resId)

/**
 * An offset conversion involves simply truncating the base value to an integer.
 */
fun Context.getDimensionPixelOffsetCompat(@DimenRes resId: Int): Int = resources.getDimensionPixelOffset(resId)

fun Context.getIntCompat(@IntegerRes resId: Int): Int = resources.getInteger(resId)

fun Context.getIntArrayCompat(@ArrayRes resId: Int): IntArray = resources.getIntArray(resId)

fun Context.getDrawableCompat(@DrawableRes resId: Int): Drawable = AppCompatResources.getDrawable(this, resId)!!

fun Context.getBoolean(@BoolRes resId: Int): Boolean = resources.getBoolean(resId)

// endregion

// region Activity
///////////////////////////////////////////////////////////////////////////

fun Context.tryToFindActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        } else {
            context = context.baseContext
        }
    }

    return null
}

fun Context.tryToGoBack() {
    val activity = this.tryToFindActivity()
    activity?.onBackPressed()
}

// endregion

// region Permissions
///////////////////////////////////////////////////////////////////////////
/**
 * Returns true if passed [permission] is granted.
 */
fun Context.checkPermissionIsGranted(permission: String): Boolean {
    val permissionResult = try {
        ContextCompat.checkSelfPermission(this, permission)
    } catch (unexpected: Throwable) { // Unknown exception code: 1 msg null
        // issue discussions:
        //    https://github.com/permissions-dispatcher/PermissionsDispatcher/issues/107
        //    https://github.com/Karumi/Dexter/issues/86
        Timber.e("Unexpected exception occurred while checking $permission permission.")
        PackageManager.PERMISSION_DENIED
    }
    return permissionResult == PackageManager.PERMISSION_GRANTED
}
// endregion

// region AlertDialog
///////////////////////////////////////////////////////////////////////////

fun <T : View> Context.showAlert(
    customView: T,
    isCancelable: Boolean = true,
    @StringRes titleResId: Int = 0,
    @StringRes messageId: Int = 0,
    @StringRes positiveButtonResId: Int = 0,
    @StringRes negativeButtonResId: Int = 0,
    actionPositive: (() -> Unit)? = null,
    actionNegative: (() -> Unit)? = null
): AlertDialog {
    val builder = AlertDialog
        .Builder(this)
        .setView(customView)
    if (titleResId != 0) {
        builder.setTitle(titleResId)
    }
    if (messageId != 0) {
        builder.setMessage(messageId)
    }
    if (positiveButtonResId != 0) {
        builder.setPositiveButton(positiveButtonResId) { _, _ ->
            actionPositive?.invoke()
        }
    }
    if (negativeButtonResId != 0) {
        builder.setNegativeButton(negativeButtonResId) { _, _ ->
            actionNegative?.invoke()
        }
    }
    val dialog = builder.show()
    dialog.setCancelable(isCancelable)
    dialog.setCanceledOnTouchOutside(isCancelable)
    return dialog
}

// endregion

// region Explicit intentions
///////////////////////////////////////////////////////////////////////////

fun Context.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
        .apply {
            data = Uri.parse(url)
        }
    ContextCompat.startActivity(this, intent, null)
}

fun Context.openUrl(@StringRes urlResId: Int) {
    val url = this.getString(urlResId)
    openUrl(url)
}

// endregion
