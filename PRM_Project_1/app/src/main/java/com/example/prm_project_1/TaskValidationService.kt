package com.example.prm_project_1

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.example.prm_project_1.fragment.InvalidInputFragment
import java.time.LocalDateTime

class TaskValidationService(private val name: String, private val deadline: LocalDateTime, private val progress: Double, private val estimatedDays: Int, private val fragmentManager: FragmentManager) {

    private var isValid: Boolean = true


    @RequiresApi(Build.VERSION_CODES.O)
    fun validateTask(){
        validateNewTaskName(name)
        validateNewTaskProgress(progress)
        validateEstimatedTime(estimatedDays, deadline)
    }

    private fun validateNewTaskName(newTaskName: String){
        if(newTaskName.isEmpty() || newTaskName.length > 25) {
            isValid = false
            showInvalidInputFragment("Nazwa powinna zawierac mniej niż 25 znaków")
        }
        if(DataStorage.containsTaskByName(newTaskName)) {
            isValid = false
            showInvalidInputFragment("Juz istnieje zadanie o takiej nazwie")
        }
    }


    private fun validateNewTaskProgress(newTaskProgress: Double) {
        if(newTaskProgress > 100) {
            isValid = false
            showInvalidInputFragment("Postep powinien być w przedziale od 0 do 100")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateEstimatedTime(estimatedDays: Int, deadline: LocalDateTime){
        val currentDate = LocalDateTime.now()
        if(deadline.isBefore(currentDate.plusDays(estimatedDays.toLong()))) {
            isValid = false
            showInvalidInputFragment("Szacowany czas nie może przekraczać terminu ostatecznego")
        }
    }

    fun getIfValid(): Boolean{
        return isValid
    }

    private fun showInvalidInputFragment(message: String){
        val invalidInputFragment = InvalidInputFragment(message)
        invalidInputFragment.show(fragmentManager, "invalid data")
    }
}