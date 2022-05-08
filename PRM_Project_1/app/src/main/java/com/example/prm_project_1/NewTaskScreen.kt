package com.example.prm_project_1

import android.content.Intent
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.view.get
import com.example.prm_project_1.adapter.TaskAdapter
import com.example.prm_project_1.databinding.ActivityMain2Binding
import com.example.prm_project_1.databinding.ActivityNewTaskScreenBinding
import com.example.prm_project_1.fragment.DatePicker
import com.example.prm_project_1.fragment.InvalidInputFragment
import com.example.prm_project_1.fragment.TimePicker
import com.example.prm_project_1.spinner.PriorityLevelSpinnerActivity
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt
import java.time.LocalDateTime
import java.time.Period

class NewTaskScreen : AppCompatActivity() {

    private val binding by lazy {ActivityNewTaskScreenBinding.inflate(layoutInflater)}

    private var priorityLevel: Int = 1

    private var currentTaskName: String = ""

    private var isNewTaskMode = true

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        isNewTaskMode = intent.extras?.get("is_new_task_mode") as Boolean
        if(isNewTaskMode)
            newTaskMode()
        else
            editTaskMode()
        binding.testButton.setOnClickListener {
            showTimePickerDialog(binding.root)
        }
        binding.pickDateButton.setOnClickListener {
            showDatePickerDialog(binding.root)
        }
        binding.newEditTaskReturnButton.setOnClickListener {
            startMainActivity()
        }
        spinner()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun newTaskMode(){
        setCurrentDateFormatted()
        binding.addEditTaskButton.setOnClickListener {
            addTaskOnClickListener()
        }
        binding.newEditTaskTitle.text = getString(R.string.new_task_title)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun editTaskMode(){
        setALlViewsForEditMode()
        setEditButton()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setALlViewsForEditMode(){
        currentTaskName = intent.getStringExtra("task_name").toString()
        var currentEstimatedDays = Period.between((intent.extras?.get("task_deadline") as LocalDateTime).toLocalDate(), (intent.extras?.get("task_estimated_time") as LocalDateTime).toLocalDate()).days * (-1)
        val deadlineString = intent.extras?.get("task_deadline").toString().replace("T", " ")
        binding.dateTimeOutput.text = deadlineString.substring(0, deadlineString.length - 7)
        binding.newTaskNameInput.text = Editable.Factory.getInstance().newEditable(currentTaskName)
        binding.estimatedTimeInput.text = Editable.Factory.getInstance().newEditable(currentEstimatedDays.toString())
        binding.newTaskProgressInput.text = Editable.Factory.getInstance().newEditable(intent.extras?.get("task_progress").toString())
        binding.newTaskProgressInput.focusable = View.NOT_FOCUSABLE
        binding.addEditTaskButton.text = getString(R.string.edit_task_button)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setEditButton(){
        binding.addEditTaskButton.text = getString(R.string.edit_task_button)
        binding.addEditTaskButton.setOnClickListener {
            setEditTaskOnClickListener()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setEditTaskOnClickListener(){
        if(!validateData())
            return
        addEditedTask()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addEditedTask(){
        var currentTask = DataStorage.getTaskByName(currentTaskName)
        var newEditedTask = getTaskFromInputs()
        currentTask?.name =  newEditedTask.name
        currentTask?.deadline = newEditedTask.deadline
        currentTask?.estimatedTime = newEditedTask.estimatedTime
        currentTask?.priority = newEditedTask.priority
        startMainActivity()
    }

    fun showTimePickerDialog(v: View){
        TimePicker(binding).show(supportFragmentManager, "timePicker")
    }

    fun showDatePickerDialog(v: View){
        DatePicker(binding).show(supportFragmentManager, "datePicker")
    }

    private fun spinner(){
        var spinner = binding.priorityLevelSpinner
        ArrayAdapter.createFromResource(this, R.array.priority_levels, android.R.layout.simple_spinner_item)
            .also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = arrayAdapter
                spinner.onItemSelectedListener = PriorityLevelSpinnerActivity(this)
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addTaskOnClickListener(){
        if(!validateData())
            return
        addNewTask()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addNewTask(){
        DataStorage.addTask(getTaskFromInputs())
        startMainActivity()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTaskFromInputs(): Task{
        val name = binding.newTaskNameInput.text.toString()
        val deadlineInput = binding.dateTimeOutput.text.toString()
        Log.d("DEBUG_NEW_TASK", deadlineInput)
        Log.d("DEBUG_NEW_TASK", name)
        deadlineInput.replace(" ", "T")
        val deadline = LocalDateTime.parse(deadlineInput.replace(" ", "T"))
        Log.d("tag", binding.newTaskProgressInput.text.toString())
        val progress = binding.newTaskProgressInput.text.toString().toDouble()
        val estimatedDays = binding.estimatedTimeInput.text.toString().toInt()
        return Task(name, priorityLevel, deadline, progress, LocalDateTime.now().plusDays(estimatedDays.toLong()), LocalDateTime.now())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateData(): Boolean{
        val name = binding.newTaskNameInput.text.toString()
        val progress = parseDouble(binding.newTaskProgressInput.text.toString())
        val estimatedTime = binding.estimatedTimeInput.text.toString().toInt()
        val deadline = LocalDateTime.parse((binding.dateTimeOutput.text.toString().replace(" ", "T").plus(":00")))
        val validationService = TaskValidationService(name, deadline, progress, estimatedTime, supportFragmentManager)
        validationService.validateTask()
        return validationService.getIfValid()
    }

    private fun checkPriority(priorityLevel: String): Int{
        val priorityHigh = getString(R.string.priority_high)
        val priorityLow = getString(R.string.priority_low)
        val priorityMedium = getString(R.string.priority_medium)
        when(priorityLevel){
            priorityHigh -> return 3
            priorityLow -> return 1
            priorityMedium -> return 2
        }
        return 1
    }

    private fun createDateString(partOfDate: Int): String{
        if(partOfDate < 10)
            return "0$partOfDate"
        return "$partOfDate"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setCurrentDateFormatted(){
        val currentDate = LocalDateTime.now()
        binding.dateTimeOutput.text = "${currentDate.year}-" +
                "${createDateString(currentDate.month.value)}-" +
                "${createDateString(currentDate.dayOfMonth)} " +
                "${createDateString(currentDate.hour)}:" +
                "${createDateString(currentDate.minute)}"
    }

    fun setCurrentPriorityLevel(priorityLevel: String){
        this.priorityLevel = checkPriority(priorityLevel)
    }

    private fun startMainActivity(){
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}