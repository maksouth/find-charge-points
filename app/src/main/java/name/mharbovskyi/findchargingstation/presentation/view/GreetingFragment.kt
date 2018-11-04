package name.mharbovskyi.findchargingstation.presentation.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import name.mharbovskyi.findchargingstation.R
import name.mharbovskyi.findchargingstation.presentation.ViewUser

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class GreetingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_greeting, container, false)
    }

    companion object {
        fun newInstance(user: ViewUser): GreetingFragment {
            TODO()
        }
    }
}
