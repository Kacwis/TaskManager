package com.example.prm_project_1.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.prm_project_1.R
import java.lang.IllegalStateException

class InvalidInputFragment(private val message: String): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(message)
            builder.setNegativeButton(
                R.string.return_button,
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })
            builder.create()
        } ?: throw IllegalStateException("Activity can not be null")

    }
}