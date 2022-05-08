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
    taskPriority.text = getDisplayPriorityLevel(task.priority)
    taskProgress.text = task.progress.toString().split(".")[0].plus("%")
  }

  @SuppressLint("NewApi")
  private fun createDisplayStringFromDate(date: LocalDateTime) : String {
    return "${getCorrectDateElementString(date.year)}-" +
            "${getCorrectDateElementString(date.monthValue)}-" +
            "${getCorrectDateElementString(date.dayOfMonth)} " +
            "${getCorrectDateElementString(date.hour)}:" +
            "${getCorrectDateElementString(date.minute)}"
  }

  private fun getCorrectDateElementString(elementOfDate: Int): String{
    if(elementOfDate < 10)
      return "0$elementOfDate"
    return "$elementOfDate"
  }

  private fun getDisplayPriorityLevel(priorityLevel: Int): String{
    when(priorityLevel) {
      1 -> return "Niski"
      2 -> return "Sredni"
      3 -> return "Wysoki"
    }
    return "Nie ma"
  }

}