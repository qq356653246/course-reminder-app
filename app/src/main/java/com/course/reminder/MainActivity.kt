package com.course.reminder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.course.reminder.databinding.ActivityMainBinding

/**
 * 主界面 - 显示课程列表
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CourseViewModel by viewModels()
    private lateinit var reminderManager: ReminderManager
    private lateinit var courseAdapter: CourseAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        reminderManager = ReminderManager(this)
        
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
        requestNotificationPermission()
    }
    
    private fun setupRecyclerView() {
        courseAdapter = CourseAdapter(
            onCourseClick = { course -> showCourseDetail(course) },
            onToggleCourse = { course, enabled ->
                viewModel.toggleCourse(course.id, enabled)
                if (enabled) {
                    reminderManager.scheduleReminder(course)
                } else {
                    reminderManager.cancelReminder(course.id)
                }
            },
            onDeleteCourse = { course ->
                viewModel.deleteCourse(course)
                reminderManager.cancelReminder(course.id)
            }
        )
        
        binding.recyclerViewCourses.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = courseAdapter
        }
    }
    
    private fun setupObservers() {
        viewModel.allCourses.observe(this) { courses ->
            courseAdapter.submitList(courses)
            
            if (courses.isEmpty()) {
                binding.emptyView.visibility = View.VISIBLE
                binding.recyclerViewCourses.visibility = View.GONE
            } else {
                binding.emptyView.visibility = View.GONE
                binding.recyclerViewCourses.visibility = View.VISIBLE
            }
        }
    }
    
    private fun setupClickListeners() {
        binding.fabAddCourse.setOnClickListener {
            // TODO: 打开添加课程界面
            Toast.makeText(this, "添加课程功能开发中...", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }
    }
    
    private fun showCourseDetail(course: Course) {
        // TODO: 显示课程详情或编辑界面
        Toast.makeText(this, "点击了：${course.name}", Toast.LENGTH_SHORT).show()
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "通知权限已授予", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
