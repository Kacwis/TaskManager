package com.example.prm_project_1.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.prm_project_1.DataStorage
import com.example.prm_project_1.MainActivity
import com.example.prm_project_1.Task
import com.example.prm_project_1.databinding.TaskListItemBinding
import com.example.prm_project_1.fragment.RemoveTaskFragment
import com.example.prm_project_1.holder.TaskViewHolder

class TaskAdapter(private val mainActivity: MainActivity, taskList: MutableList<Task>) : RecyclerView.Adapter<TaskViewHolder>(){

    val taskList = taskList

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
        holder.itemView.setOnLongClickListener {
            val fragment = RemoveTaskFragment(position, this, taskList[position].name)
            fragment.show(mainActivity.supportFragmentManager, "removing task")
            return@setOnLongClickListener true
        }
    }

    fun addTasks(taskList: MutableList<Task>){
        this.taskList.run{
            clear()
            addAll(taskList)
        }
        Log.d("TAG", "dodano")
        notifyDataSetChanged()
    }

    fun removeTask(position: Int){
        taskList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    fun sortTaskList(){
        taskList.sortBy { task -> task.deadline}
    }


}