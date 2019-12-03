package ru.skillbranch.gameofthrones.ui.main

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.NeedHouses


/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var housePageViewModel: HousePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        housePageViewModel = ViewModelProviders.of(this).get(HousePageViewModel::class.java).apply {
            setHouse(NeedHouses.values().get(arguments?.getInt(ARG_SECTION_NUMBER) ?: 0))
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// create ContextThemeWrapper from the original Activity Context with the custom theme
        // create ContextThemeWrapper from the original Activity Context with the custom theme
        val contextThemeWrapper: Context =
            ContextThemeWrapper(activity, housePageViewModel.themeId.value?: R.style.AppTheme)

// clone the inflater using the ContextThemeWrapper
        // clone the inflater using the ContextThemeWrapper
        val localInflater = inflater.cloneInContext(contextThemeWrapper)

// inflate the layout using the cloned inflater, not default inflater
        // inflate the layout using the cloned inflater, not default inflater

        val root = localInflater.inflate(R.layout.fragment_main, container, false)
        val textView: TextView = root.findViewById(R.id.section_label)
        housePageViewModel.text.observe(this, Observer<String> {
            textView.text = it
        })
        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}