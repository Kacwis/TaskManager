package com.example.prm_project_1

import java.io.Serializable
import java.time.LocalDateTime
import java.time.Period

class Task(var name:String,var priority: Int, var deadline: LocalDateTime, var progress: Double, var estimatedTime: LocalDateTime, val startDate: LocalDateTime): Serializable {
}