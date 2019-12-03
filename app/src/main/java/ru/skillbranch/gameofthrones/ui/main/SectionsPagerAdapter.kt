package ru.skillbranch.gameofthrones.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.skillbranch.gameofthrones.data.NeedHouses.*

private val TAB_TITLES = arrayOf(
    STARK,
    LANNISTER,
    TARGARYEN,
    BARATHEON,
    GREYJOY,
    MARTEL,
    TYRELL
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position].shortName
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return TAB_TITLES.count()
    }
}