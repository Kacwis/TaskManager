package com.example.prm_project_1.spinner

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import com.example.prm_project_1.TaskDetails

class ProgressSpinnerActivity(val taskDetails: TaskDetails) : Activity(), AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        var item = parent?.getItemAtPosition(pos)
        taskDetails.changeProgress(taskDetails.getTaskName(), item.toString().toInt())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        return
    }
}