package com.example.prm_project_1

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.example.prm_project_1.databinding.ActivityTaskDetailsBinding
import com.example.prm_project_1.spinner.ProgressSpinnerActivity
import java.time.LocalDateTime

class TaskDetails : AppCompatActivity() {

    private val binding by lazy {ActivityTaskDetailsBinding.inflate(layoutInflater)}

    private var currentProgress: Double = 0.0

    private val currentName: String = intent.extras?.get("task_name").toString()

    private val currentDeadline: LocalDateTime = intent.extras?.get("task_deadline") as LocalDateTime

    private val currentEstimatedTime: 


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        currentProgress = intent.getDoubleExtra("task_progress", 0.0)
        setTextForAllTextViews()
        binding.editTaskButton.setOnClickListener {
            var intent = Intent(this, NewTaskScreen::class.java)
            intent.putExtra("is_new_task_mode", false)
            intent.putExtra("task_name", intent.extras)
        }
        binding.returnButton.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        changeProgressButton()
        binding.progressPieChart.setImageDrawable(PieProgressDrawable(intent.getDoubleExtra("task_progress", 0.0)))
        spinner()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setTextForAllTextViews(){
        binding.taskDetailsTitleName.text = intent.extras?.get("task_name").toString()
        binding.taskDetailsDeadlineOutput.text = formatDateString(intent.extras?.get("task_deadline") as LocalDateTime)
        binding.taskDetailsEstimatedTimeOutput.text = intent.extras?.get("task_estimated_time").toString()
        binding.startingDateOutput.text = formatDateString(intent.extras?.get("task_starting_date") as LocalDateTime)
//        binding.taskDetailsProgressOutput.text = intent.extras?.get("task_progress").toString()
    }

    private fun formatDateString(date: LocalDateTime): String{
        var stringDate = date.toString()
        stringDate = stringDate.replace("T", " ").substring(0, stringDate.length - 3)
        return stringDate
    }

    private fun spinner(){
        var spinner = binding.progressSpinner
        ArrayAdapter.createFromResource(this, R.array.progress_numbers, android.R.layout.simple_spinner_item)
            .also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = arrayAdapter
            }
    }

    private fun changeProgressButton(){
        binding.progressSpinner.onItemSelectedListener = ProgressSpinnerActivity(this)
        binding.changeProgressButton.setOnClickListener {
            showAllProgressChangeElements()
        }
        binding.progressChangeConfirmButton.setOnClickListener {
            DataStorage.getTaskByName(getTaskName())?.progress = currentProgress
            binding.progressPieChart.setImageDrawable(PieProgressDrawable(currentProgress))
            hideAllProgressChangeElements()
        }
        binding.progressChangeCancelButton.setOnClickListener {
            hideAllProgressChangeElements()
        }

    }

    fun changeProgress(taskName: String, newProgress: Int){
        this.currentProgress = newProgress.toDouble()
    }

    fun getTaskName(): String{
        return intent.getStringExtra("task_name").toString()
    }

    private fun showAllProgressChangeElements(){
        binding.progressPieChart.visibility = View.GONE
        binding.progressSpinner.visibility = View.VISIBLE
        binding.changeProgressButton.visibility = View.GONE
        binding.progressChangeConfirmButton.visibility = View.VISIBLE
        binding.progressChangeCancelButton.visibility = View.VISIBLE
    }

    private fun hideAllProgressChangeElements(){
        binding.progressPieChart.visibility = View.VISIBLE
        binding.progressSpinner.visibility = View.GONE
        binding.changeProgressButton.visibility = View.VISIBLE
        binding.progressChangeConfirmButton.visibility = View.GONE
        binding.progressChangeCancelButton.visibility = View.GONE
    }


}