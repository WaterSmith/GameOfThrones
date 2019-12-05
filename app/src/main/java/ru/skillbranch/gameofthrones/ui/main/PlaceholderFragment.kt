package ru.skillbranch.gameofthrones.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_main.*
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.NeedHouses
import ru.skillbranch.gameofthrones.ui.adapters.CharacterAdapter
import ru.skillbranch.gameofthrones.viewmodels.HousePageViewModel


/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var housePageViewModel: HousePageViewModel
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var house: NeedHouses

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        house = NeedHouses.values().get(arguments?.getInt(ARG_SECTION_NUMBER) ?: 0)
        setHasOptionsMenu(true)
    }

    private fun initViews(view: View) {
        characterAdapter = CharacterAdapter{
            //intent = Intent(this, ArchiveActivity::class.java)
            //startActivity(intent)
        }

        val divider = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)

        with(view.findViewById<RecyclerView>(R.id.rv_character_list)){
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(divider)
        }
    }

    private fun initViewModel() {
        housePageViewModel = ViewModelProviders.of(this).get(HousePageViewModel::class.java)
        housePageViewModel.setHouse(house)
        housePageViewModel.getCharacterData().observe(this, Observer { characterAdapter.updateData(it, house)})
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        initViews(root)
        initViewModel()
        //val textView: TextView = root.findViewById(R.id.section_label)
       // housePageViewModel.text.observe(this, Observer<String> {
       //     textView.text = it
       // })
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