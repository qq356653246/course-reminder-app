package com.course.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.Calendar

/**
 * 提醒管理器 - 负责设置和管理课程提醒
 */
class ReminderManager(private val context: Context) {
    
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    
    /**
     * 为课程设置提醒
     */
    fun scheduleReminder(course: Course) {
        if (!course.isEnabled) return
        
        // 计算提醒时间
        val reminderTime = calculateReminderTime(course)
        
        // 创建 PendingIntent
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            action = "com.course.reminder.REMINDER"
            putExtra("COURSE_ID", course.id)
            putExtra("COURSE_NAME", course.name)
            putExtra("COURSE_ROOM", course.room)
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            course.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // 设置闹钟
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    reminderTime,
                    pendingIntent
                )
            }
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                reminderTime,
                pendingIntent
            )
        }
    }
    
    /**
     * 取消课程提醒
     */
    fun cancelReminder(courseId: Long) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            action = "com.course.reminder.REMINDER"
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            courseId.toInt(),
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        
        pendingIntent?.let {
            alarmManager.cancel(it)
            it.cancel()
        }
    }
    
    /**
     * 计算提醒时间戳
     */
    private fun calculateReminderTime(course: Course): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
        }
        
        // 解析开始时间
        val timeParts = course.startTime.split(":")
        val hour = timeParts[0].toInt()
        val minute = timeParts[1].toInt()
        
        // 设置课程开始时间
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        
        // 减去提醒偏移量
        calendar.add(Calendar.MINUTE, -course.reminderOffset)
        
        // 如果时间已过，设置到下周
        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1)
        }
        
        return calendar.timeInMillis
    }
    
    /**
     * 重新安排所有课程的提醒
     */
    fun rescheduleAllCourses(courses: List<Course>) {
        // 取消所有现有提醒
        courses.forEach { cancelReminder(it.id) }
        
        // 重新设置启用的课程提醒
        courses.filter { it.isEnabled }.forEach { scheduleReminder(it) }
    }
}
