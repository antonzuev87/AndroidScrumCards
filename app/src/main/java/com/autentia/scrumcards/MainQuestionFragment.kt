package com.autentia.scrumcards

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.core.app.BundleCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.autentia.scrumcards.CardsModel.CardsContent
import kotlinx.android.synthetic.main.fragment_main_question.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MainQuestionFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MainQuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MainQuestionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_question, container, false)
        view.fibonacciButton.setOnClickListener {
            var bundle = Bundle()
            bundle.putParcelableArrayList("cards", CardsContent.fibonacciItems)
            it.findNavController().navigate(R.id.action_mainQuestionFragment_to_cardListFragment, bundle)

//            val transaction = this.activity?.supportFragmentManager?.beginTransaction()?.apply {
//                // Replace whatever is in the fragment_container view with this fragment,
//                // and add the transaction to the back stack so the user can navigate back
//                setCustomAnimations(R.anim.right_to_left_animation, R.anim.left_to_right_animation, 0, R.anim.left_to_right_animation)
//                val fibonacciListFragment = CardListFragment()
//                fibonacciListFragment.cards = CardsContent.fibonacciItems
//                replace(R.id.fragment, fibonacciListFragment)
//                addToBackStack(null)
//            }
//            // Commit the transaction
//            transaction?.commit()
        }

        view.tShirtButtonSizes.setOnClickListener {
            var bundle = Bundle()
            bundle.putParcelableArrayList("cards", CardsContent.TShirtSizesItems)
            it.findNavController().navigate(R.id.action_mainQuestionFragment_to_cardListFragment, bundle)
//            val transaction = this.activity?.supportFragmentManager?.beginTransaction()?.apply {
//                // Replace whatever is in the fragment_container view with this fragment,
//                // and add the transaction to the back stack so the user can navigate back
//                setCustomAnimations(R.anim.right_to_left_animation, R.anim.left_to_right_animation, 0, R.anim.left_to_right_animation)
//                val fibonacciListFragment = CardListFragment()
//                fibonacciListFragment.cards = CardsContent.TShirtSizesItems
//                replace(R.id.fragment, fibonacciListFragment)
//                addToBackStack(null)
//            }
//            // Commit the transaction
//            transaction?.commit()
        }
        return view

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainQuestionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainQuestionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
