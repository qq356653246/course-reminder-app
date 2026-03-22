package com.course.reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.course.reminder.databinding.ItemCourseBinding

/**
 * 课程列表适配器
 */
class CourseAdapter(
    private val onCourseClick: (Course) -> Unit,
    private val onToggleCourse: (Course, Boolean) -> Unit,
    private val onDeleteCourse: (Course) -> Unit
) : ListAdapter<Course, CourseAdapter.CourseViewHolder>(CourseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CourseViewHolder(
        private val binding: ItemCourseBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(course: Course) {
            binding.apply {
                textViewCourseName.text = course.name
                textViewRoom.text = "📍 ${course.room}"
                textViewTime.text = "⏰ ${course.startTime} - ${course.endTime}"
                textViewDay.text = course.getDayOfWeekString()
                textViewTeacher.text = if (course.teacher.isNotEmpty()) "👨‍🏫 ${course.teacher}" else ""
                
                switchEnabled.isChecked = course.isEnabled
                
                root.setOnClickListener { onCourseClick(course) }
                
                switchEnabled.setOnCheckedChangeListener { _, isChecked ->
                    onToggleCourse(course, isChecked)
                }
                
                buttonDelete.setOnClickListener {
                    onDeleteCourse(course)
                }
            }
        }
    }

    class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }
}
