package com.autentia.scrumcards

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout

import com.autentia.scrumcards.CardsModel.CardsContent.CardItem
import kotlinx.android.synthetic.main.card_list_fragment.view.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [CardListFragment.OnListFragmentInteractionListener] interface.
 */
class CardListFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var columnWidthDp = 155.0f

    private var listener: OnListFragmentInteractionListener? = null

    private var cards: ArrayList<CardItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context?.let {
            columnCount = Utility.calculateNoOfColumns(it, columnWidthDp) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.card_list_fragment, container, false)

        cards = this.arguments?.getParcelableArrayList("cards")

        cards?.let {
            // Set the adapter
            if (view is ConstraintLayout) {
                with(view.list) {
                    layoutManager = when {
                        columnCount <= 1 -> LinearLayoutManager(context)
                        else -> GridLayoutManager(context, columnCount)
                    }
                    adapter = CardListRecyclerViewAdapter(it, listener)

                }
            }
        }


        if (view.list.layoutManager is GridLayoutManager) {
            var gridLayoutManager = view.list.layoutManager as GridLayoutManager
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (view.list.adapter?.getItemViewType(position)) {
                        CardListRecyclerViewAdapter.footerView -> columnCount
                        else -> 1
                    }
                }
            }
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
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
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: CardItem?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            CardListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
