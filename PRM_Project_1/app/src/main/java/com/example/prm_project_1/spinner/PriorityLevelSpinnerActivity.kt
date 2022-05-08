package com.example.prm_project_1.spinner

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import com.example.prm_project_1.NewTaskScreen

class PriorityLevelSpinnerActivity(val newTaskScreen: NewTaskScreen) : Activity(), AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        var item = parent?.getItemAtPosition(pos)
        newTaskScreen.setCurrentPriorityLevel(item.toString())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        return
    }
}