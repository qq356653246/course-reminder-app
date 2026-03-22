# 📚 课程提醒 (Course Reminder)

一个简洁的 Android 课程提醒应用，帮助你准时上课，不再迟到！

## ✨ 功能特性

- 📅 **课程管理** - 添加、编辑、删除课程
- ⏰ **智能提醒** - 可自定义提前提醒时间
- 📍 **教室定位** - 记录每节课的教室位置
- 👨‍🏫 **教师信息** - 记录任课教师
- 🔔 **通知推送** - 准时推送上课提醒
- 📱 **Material Design** - 现代化 UI 设计

## 🏗️ 技术架构

- **语言**: Kotlin
- **架构**: MVVM (Model-View-ViewModel)
- **数据库**: Room
- **异步**: Kotlin Coroutines + Flow
- **UI**: ViewBinding + Material Components
- **提醒**: AlarmManager + BroadcastReceiver

## 📁 项目结构

```
app/src/main/java/com/course/reminder/
├── Course.kt              # 课程数据模型
├── CourseDao.kt           # 数据库访问对象
├── CourseDatabase.kt      # Room 数据库
├── CourseRepository.kt    # 数据仓库
├── CourseViewModel.kt     # ViewModel
├── CourseAdapter.kt       # RecyclerView 适配器
├── MainActivity.kt        # 主界面
├── ReminderManager.kt     # 提醒管理器
└── ReminderReceiver.kt    # 广播接收器
```

## 🚀 快速开始

### 环境要求

- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 17
- Android SDK 34

### 构建步骤

1. 克隆项目
```bash
git clone https://github.com/YOUR_USERNAME/course-reminder-app.git
```

2. 用 Android Studio 打开项目

3. 同步 Gradle 文件

4. 运行应用

## 📝 待开发功能

- [ ] 添加/编辑课程界面
- [ ] 课程表视图
- [ ] 课程颜色分类
- [ ] 考试提醒
- [ ] 作业提醒
- [ ] 数据备份与恢复
- [ ] 小组件支持

## 📄 许可证

MIT License

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

---

**Made with ❤️ by OpenClaw**
