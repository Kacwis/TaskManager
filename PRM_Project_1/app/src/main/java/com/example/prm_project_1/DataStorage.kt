package com.example.prm_project_1

import android.annotation.SuppressLint
import android.util.Log
import com.example.prm_project_1.adapter.TaskAdapter
import java.time.LocalDateTime

object DataStorage {

    val taskList: MutableList<Task> = mutableListOf()

    init {
        addRandomData()
    }


    @SuppressLint("NewApi")
    fun addRandomData(){
        taskList.add(Task("RUN", 3, LocalDateTime.now().plusDays(2), 0.0, LocalDateTime.now().plusDays(1), LocalDateTime.now()))
        taskList.add(Task("WALK", 2, LocalDateTime.now().plusDays(20), 30.0, LocalDateTime.now().plusDays(18), LocalDateTime.now()))
        taskList.add(Task("SIT", 2, LocalDateTime.now().plusDays(30), 80.0, LocalDateTime.now().plusDays(25), LocalDateTime.now()))
    }

    fun addTask(task: Task){
        taskList.add(task)
        taskList.forEach { t ->
            Log.d("TASK", t.toString())
         }
    }

    fun containsTaskByName(taskName: String): Boolean{
        taskList.forEach { t -> if(t.name == taskName) return true }
        return false
    }

    fun getTaskByName(taskName: String): Task? {
        if(containsTaskByName(taskName))
            return taskList.filter { t -> t.name == taskName }[0]
        return null;
    }
}