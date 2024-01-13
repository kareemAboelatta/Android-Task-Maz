package com.example.android_taskmaz.presentaion.fragments.dashboard

import com.example.android_taskmaz.domain.models.Course
import com.example.android_taskmaz.domain.models.LiveUser

object DataGenerator {

    fun generateFakeUsers(): List<LiveUser> {
        return listOf(
            LiveUser("https://randomuser.me/api/portraits/men/1.jpg", isOnline = true),
            LiveUser("https://randomuser.me/api/portraits/women/2.jpg", isOnline = false),
            LiveUser("https://randomuser.me/api/portraits/men/3.jpg", isOnline = true),
            LiveUser("https://randomuser.me/api/portraits/women/4.jpg", isOnline = true),
            LiveUser("https://randomuser.me/api/portraits/men/5.jpg", isOnline = false),
            LiveUser("https://randomuser.me/api/portraits/women/6.jpg", isOnline = true),
            LiveUser("https://randomuser.me/api/portraits/men/7.jpg", isOnline = false),
            LiveUser("https://randomuser.me/api/portraits/women/8.jpg", isOnline = true),
            LiveUser("https://randomuser.me/api/portraits/men/9.jpg", isOnline = true),
            LiveUser("https://randomuser.me/api/portraits/women/10.jpg", isOnline = false)
        )
    }

    fun  generateFakeCourses() = listOf(
        Course(
            id = "course1",
            courseName = "Step design sprint for beginner",
            courseImage = "https://theawesomer.com/photos/2022/06/adobe_ui_ux_bundle_1.jpg",
            courseTime = "3h 15m",
            instructor = Course.Instructor(
                instructorName = "Laurel Seilha",
                instructorBio = "Product Designer",
                instructorImage = "https://symbolt.io/wp-content/uploads/2020/03/headofmarketing.png"
            )
        ),
        Course(
            id = "course2",
            courseName = "Advanced Programming",
            courseImage = "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d",
            courseTime = "4h 40m",
            instructor = Course.Instructor(
                instructorName = "Sara Smith",
                instructorBio = "Lead Developer",
                instructorImage = "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d"
            )
        ),
        Course(
            id = "course3",
            courseName = "Digital Marketing 101",
            courseImage = "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d",
            courseTime = "2h 20m",
            instructor = Course.Instructor(
                instructorName = "Emily White",
                instructorBio = "Marketing Expert",
                instructorImage = "https://symbolt.io/wp-content/uploads/2020/03/headofmarketing.png"
            )
        ),
        Course(
            id = "course4",
            courseName = "Basics of Photography",
            courseImage = "https://theawesomer.com/photos/2022/06/adobe_ui_ux_bundle_1.jpg",
            courseTime = "5h 00m",
            instructor = Course.Instructor(
                instructorName = "John Doe",
                instructorBio = "Professional Photographer",
                instructorImage = "https://theawesomer.com/photos/2022/06/adobe_ui_ux_bundle_1.jpg"
            )
        ),
        Course(
            id = "course5",
            courseName = "Creative Writing Workshop",
            courseImage = "https://theawesomer.com/photos/2022/06/adobe_ui_ux_bundle_1.jpg",
            courseTime = "3h 30m",
            instructor = Course.Instructor(
                instructorName = "Laura Green",
                instructorBio = "Author & Editor",
                instructorImage = "https://theawesomer.com/photos/2022/06/adobe_ui_ux_bundle_1.jpg"
            )
        )
    )
}
