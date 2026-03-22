package com.course.reminder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CourseViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: CourseRepository
    
    val allCourses: StateFlow<List<Course>>
    val enabledCourses: StateFlow<List<Course>>
    
    init {
        val courseDao = CourseDatabase.getDatabase(application).courseDao()
        repository = CourseRepository(courseDao)
        
        allCourses = repository.allCourses.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
        
        enabledCourses = repository.enabledCourses.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }
    
    fun addCourse(course: Course) = viewModelScope.launch {
        repository.insert(course)
    }
    
    fun updateCourse(course: Course) = viewModelScope.launch {
        repository.update(course)
    }
    
    fun deleteCourse(course: Course) = viewModelScope.launch {
        repository.delete(course)
    }
    
    fun toggleCourse(id: Long, enabled: Boolean) = viewModelScope.launch {
        repository.toggleCourse(id, enabled)
    }
    
    fun getCoursesByDay(dayOfWeek: Int) = repository.getCoursesByDay(dayOfWeek)
}
