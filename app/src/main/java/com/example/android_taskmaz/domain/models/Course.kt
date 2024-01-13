package com.example.android_taskmaz.domain.models

data class Course(
    val id: String,
    val courseName: String,
    val courseImage: String,
    val courseTime: String,
    val instructor :Instructor
){
    data class Instructor(
        val instructorName: String,
        val instructorBio: String,
        val instructorImage: String,
    )
}