package shander.annelisapp.ui.projectSummary

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import shander.annelisapp.R
import shander.annelisapp.ui.projectSummary.fragment.gallery.GalleryFragment
import shander.annelisapp.ui.projectSummary.fragment.MaterialsFragment
import shander.annelisapp.ui.projectSummary.fragment.MeasurementsFragment
import shander.annelisapp.ui.projectSummary.fragment.tasks.TasksFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3,
    R.string.tab_text_4
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, private val projectId: Int) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position){
            1 -> GalleryFragment.newInstance(projectId)
            2 -> MeasurementsFragment.newInstance()
            3 -> MaterialsFragment.newInstance()
            else -> TasksFragment.newInstance(projectId)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 4
    }
}