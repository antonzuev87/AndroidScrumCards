package com.autentia.scrumcards

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


import com.autentia.scrumcards.CardListFragment.OnListFragmentInteractionListener
import com.autentia.scrumcards.cardsmodel.CardsUtil
import com.autentia.scrumcards.cardsmodel.CardsUtil.CardItem
import kotlinx.android.synthetic.main.card.view.*
import kotlinx.android.synthetic.main.card_list_footer.view.*



/**
 * [RecyclerView.Adapter] that can display a [CardItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class CardListRecyclerViewAdapter(
    private val mValues: ArrayList<CardItem>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as CardItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item, mValues)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View
        var vh: RecyclerView.ViewHolder

        if (viewType == footerView) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_list_footer, parent, false)
            vh = FooterViewHolder(view)

        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card, parent, false)
            vh = CardViewHolder(view)
        }

        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is CardViewHolder) {
            val item = mValues[position]
            //holder.mIdView.text = item.id
            holder.mContentView.text = item.imageName

            with(holder.mView) {
                tag = item
                setOnClickListener(mOnClickListener)
            }

        }
    }

    override fun getItemCount(): Int = mValues.size + 1

    override fun getItemViewType(position: Int): Int {
        if (position == mValues.size) {
            // This is where we'll add footer.
            return footerView
        }
        return super.getItemViewType(position)
    }

    inner class CardViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        //val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.cardName

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }

    inner class FooterViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
        private val continueButton: Button = this.mView.continueButton
        private val restButton: Button = this.mView.restButton

        init {
            continueButton.setOnClickListener {
                val continueButtonItem = CardsUtil.continueButtonItem
                mListener?.onListFragmentInteraction(continueButtonItem)
            }
            restButton.setOnClickListener {
                val restButtonItem = CardsUtil.restButtonItem
                mListener?.onListFragmentInteraction(restButtonItem)
            }
        }
    }

    companion object {
        const val footerView: Int = 1
    }


}
