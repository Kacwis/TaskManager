package com.example.prm_project_1.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.prm_project_1.NewTaskScreen
import com.example.prm_project_1.databinding.ActivityNewTaskScreenBinding
import java.util.*

class DatePicker(private val layoutBinding: ActivityNewTaskScreenBinding) : DialogFragment(), DatePickerDialog.OnDateSetListener {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(this.requireContext(), this, year, month, day)
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        val currentTime = layoutBinding.dateTimeOutput.text.split(" ")[1]
        val monthString = createDateString(month + 1)
        val dayString = createDateString(day)
        val newDate = "$year-$monthString-$dayString"
        layoutBinding.dateTimeOutput.text = "$newDate $currentTime"
    }

    private fun createDateString(partOfDate: Int): String{
        if(partOfDate < 10)
            return "0$partOfDate"
        return "$partOfDate"
    }

}