package com.example.prm_project_1.holder

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.prm_project_1.Task
import com.example.prm_project_1.databinding.TaskListItemBinding
import java.time.LocalDateTime

class TaskViewHolder(private val layoutBinding: TaskListItemBinding) : RecyclerView.ViewHolder(layoutBinding.root) {

  fun bind(task: Task) = with(layoutBinding) {
    taskName.text = task.name
    taskDeadline.text = createDisplayStringFromDate(task.deadline)
    taskPriority.text = task.priority.toString()
    taskProgress.text = task.progress.toString().split(".")[0].plus("%")
  }

  @SuppressLint("NewApi")
  private fun createDisplayStringFromDate(date: LocalDateTime) : String {
    return "${date.year}-${date.monthValue}-${date.dayOfMonth} ${date.hour}:${date.minute}"
  }

}