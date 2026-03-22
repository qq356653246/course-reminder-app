package com.course.reminder

/**
 * 课程数据模型
 */
data class Course(
    val id: Long = 0,
    val name: String,           // 课程名称
    val room: String,           // 教室
    val dayOfWeek: Int,         // 星期几 (1-7)
    val startTime: String,      // 开始时间 (HH:mm)
    val endTime: String,        // 结束时间 (HH:mm)
    val teacher: String = "",   // 教师
    val reminderOffset: Int = 15, // 提前提醒分钟数
    val isEnabled: Boolean = true // 是否启用
) {
    // 获取课程时长（分钟）
    fun getDuration(): Int {
        val start = parseTime(startTime)
        val end = parseTime(endTime)
        return end - start
    }
    
    private fun parseTime(time: String): Int {
        val parts = time.split(":")
        return parts[0].toInt() * 60 + parts[1].toInt()
    }
    
    // 获取星期几的中文显示
    fun getDayOfWeekString(): String {
        val days = arrayOf("", "周一", "周二", "周三", "周四", "周五", "周六", "周日")
        return days.getOrNull(dayOfWeek) ?: "未知"
    }
}
