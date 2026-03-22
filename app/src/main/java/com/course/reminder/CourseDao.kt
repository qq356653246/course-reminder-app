package com.course.reminder

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    
    @Query("SELECT * FROM courses ORDER BY dayOfWeek, startTime")
    fun getAllCourses(): Flow<List<Course>>
    
    @Query("SELECT * FROM courses WHERE isEnabled = 1 ORDER BY dayOfWeek, startTime")
    fun getEnabledCourses(): Flow<List<Course>>
    
    @Query("SELECT * FROM courses WHERE id = :id")
    suspend fun getCourseById(id: Long): Course?
    
    @Query("SELECT * FROM courses WHERE dayOfWeek = :dayOfWeek ORDER BY startTime")
    fun getCoursesByDay(dayOfWeek: Int): Flow<List<Course>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: Course): Long
    
    @Update
    suspend fun update(course: Course)
    
    @Delete
    suspend fun delete(course: Course)
    
    @Query("DELETE FROM courses WHERE id = :id")
    suspend fun deleteById(id: Long)
    
    @Query("UPDATE courses SET isEnabled = :enabled WHERE id = :id")
    suspend fun toggleCourse(id: Long, enabled: Boolean)
}
