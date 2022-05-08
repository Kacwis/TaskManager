package com.example.prm_project_1.fragment

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.prm_project_1.databinding.ActivityNewTaskScreenBinding
import java.text.DateFormat
import java.util.*

class TimePicker(private val layoutBinding:ActivityNewTaskScreenBinding) : DialogFragment(), TimePickerDialog.OnTimeSetListener {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(activity, this, hour, minute, true)
    }


    override fun onTimeSet(datePicker: TimePicker?, hour: Int, minute: Int) {
        val currentDate = layoutBinding.dateTimeOutput.text.toString().split(" ")[0]
        var newTime = "$hour:$minute"
        if(hour < 10) {
            newTime = "0$hour:$minute"
            if(minute < 10)
                newTime = "0$hour:0$minute"
        }
        else{
            if(minute < 10)
                newTime = "$hour:0$minute"
        }
        layoutBinding.dateTimeOutput.text = "$currentDate $newTime"

    }
}