package com.example.prm_project_1.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.prm_project_1.MainActivity
import com.example.prm_project_1.R
import com.example.prm_project_1.adapter.TaskAdapter
import java.lang.IllegalStateException

class RemoveTaskFragment(private val position: Int, private val taskAdapter: TaskAdapter, private val taskName: String): DialogFragment() {

    private val message: String = "Czy na pewno chcesz usunąć zadanie $taskName?"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it)
            builder.setMessage(message)
            builder.setNegativeButton(
                R.string.return_button
            ) { dialog, id ->
                dialog.cancel()
            }
            builder.setPositiveButton(
                R.string.delete_task_button,
                { dialog, id ->
                    taskAdapter.removeTask(position)
                }
            )
            builder.create()
        } ?: throw IllegalStateException("Activity can not be null")
    }
}