package su.vasic2000.kotlin.ui.main

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import su.vasic2000.kotlin.R

class OutDialog: DialogFragment() {
    companion object {
        val TAG = OutDialog::class.java.name + "TAG"
        fun createInstance() = OutDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        AlertDialog.Builder(context)
            .setTitle(getString(R.string.exit_title))
            .setMessage(getString(R.string.logout_message))
            .setPositiveButton(getString(R.string.positive_button)) { dialog, which -> (activity as LogoutListener).onLogOut() }
            .setNegativeButton(getString(R.string.negative_button)) { dialog, which ->  dismiss() }
            .create()


    interface LogoutListener{
        fun onLogOut()
    }
}