package com.example.prm_project_1.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.prm_project_1.DataStorage
import com.example.prm_project_1.MainActivity
import com.example.prm_project_1.Task
import com.example.prm_project_1.TaskDetails
import com.example.prm_project_1.databinding.ActivityMain2Binding
import com.example.prm_project_1.databinding.TaskListItemBinding
import com.example.prm_project_1.holder.TaskViewHolder
import java.io.Serializable
import java.time.LocalDateTime

class TaskAdapter(private val mainActivity: MainActivity) : RecyclerView.Adapter<TaskViewHolder>(){

    val taskList = mutableListOf<Task>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
        holder.itemView.setOnClickListener {
            mainActivity.startTaskDetailsActivity(taskList[position])
        }
    }

    fun addTask(taskList: MutableList<Task>){
        this.taskList.run{
            clear()
            addAll(taskList)
        }
        Log.d("TAG", "dodano")
        notifyDataSetChanged()
    }



    fun sortTaskList(){
        taskList.sortBy { task -> task.deadline}
    }


}