import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.android_taskmaz.databinding.ItemCourseBinding
import com.example.android_taskmaz.domain.models.Course
import javax.inject.Inject

class CourseAdapter(private val courses: List<Course>) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount(): Int = courses.size

    class CourseViewHolder(private val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root) {

        @Inject
        lateinit var glide: RequestManager


        fun bind(course: Course) {
            with(binding) {
                // Bind data with your view here
                // Example: courseTitle.text = course.courseName
                courseTitle.text = course.courseName
                courseTime.text = course.courseTime


                Glide.with(itemView).load(course.courseImage).into(courseImage)

                instructorBio.text = course.instructor.instructorBio
                instructorName.text = course.instructor.instructorName

                Glide.with(itemView).load(course.instructor.instructorImage).into(instructorImage)



            }
        }
    }
}
