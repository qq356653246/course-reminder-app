package com.course.reminder

import kotlinx.coroutines.flow.Flow

class CourseRepository(private val courseDao: CourseDao) {
    
    val allCourses: Flow<List<Course>> = courseDao.getAllCourses()
    
    val enabledCourses: Flow<List<Course>> = courseDao.getEnabledCourses()
    
    fun getCoursesByDay(dayOfWeek: Int): Flow<List<Course>> {
        return courseDao.getCoursesByDay(dayOfWeek)
    }
    
    suspend fun insert(course: Course): Long {
        return courseDao.insert(course)
    }
    
    suspend fun update(course: Course) {
        courseDao.update(course)
    }
    
    suspend fun delete(course: Course) {
        courseDao.delete(course)
    }
    
    suspend fun deleteById(id: Long) {
        courseDao.deleteById(id)
    }
    
    suspend fun toggleCourse(id: Long, enabled: Boolean) {
        courseDao.toggleCourse(id, enabled)
    }
}
