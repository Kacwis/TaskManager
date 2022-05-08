package com.example.prm_project_1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prm_project_1.adapter.TaskAdapter
import com.example.prm_project_1.databinding.ActivityMain2Binding
import com.example.prm_project_1.databinding.ActivityTaskDetailsBinding
import java.time.LocalDateTime
import java.time.Period

class MainActivity : AppCompatActivity() {

    private val binding by lazy {ActivityMain2Binding.inflate(layoutInflater)}

    private val taskAdapter by lazy {TaskAdapter(this)}

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        addAllData(taskAdapter)
        taskAdapter.sortTaskList()
        var layout = LinearLayoutManager(this@MainActivity)
        layout.orientation = LinearLayoutManager.VERTICAL
        binding.taskList.apply {
            adapter = taskAdapter
            layoutManager = layout
        }
        var numberOfUnfinishedTasks = calculateNumberOfUnfinishedTasksWeekly(taskAdapter.taskList)
        binding.numberOfUnfinishedTasks.text = numberOfUnfinishedTasks.toString()
        binding.addNewTaskButton.setOnClickListener {
            val intent = Intent(this, NewTaskScreen::class.java)
            intent.putExtra("is_new_task_mode", true)
            startActivity(intent)
            finish()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateNumberOfUnfinishedTasksWeekly(taskList: MutableList<Task>): Int{
        var currentDate = LocalDateTime.now()
        var endOfTheWeekDate = currentDate.plusDays((7 - currentDate.dayOfWeek.value).toLong())
        var numberOfUnfinishedTasks = taskList.filter { task -> task.deadline.isBefore(endOfTheWeekDate) }.count();
        return numberOfUnfinishedTasks
    }

    private fun addAllData(adapter: TaskAdapter){
        adapter.addTask(DataStorage.taskList)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startTaskDetailsActivity(task: Task){
        var intent = Intent(this, TaskDetails::class.java)
        intent.putExtra("task_name", task.name)
        intent.putExtra("task_priority", task.priority)
        intent.putExtra("task_progress", task.progress)
        intent.putExtra("task_deadline", task.deadline)
        intent.putExtra ("task_estimated_time", task.estimatedTime)
        intent.putExtra("task_starting_date", task.startDate)
        startActivity(intent)
        finish()
    }
}