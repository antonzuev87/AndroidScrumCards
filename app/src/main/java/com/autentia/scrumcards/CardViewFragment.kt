package com.autentia.scrumcards

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_view_fragment.*


class CardViewFragment : Fragment() {

    companion object {
        fun newInstance() = CardViewFragment()
    }

    private lateinit var viewModel: CardViewFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.card_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this.activity!!).get(CardViewFragmentViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.imageName.observe(this, Observer<String> {
            val resourceName = when(it) {
                "0,5" -> "card_half"
                "∞" -> "card_infinity"
                "card_rest" -> "card_rest"
                "card_too_much" -> "card_too_much"
                else -> "card_" + it?.toLowerCase()
            }
            val id = this.resources.getIdentifier(resourceName, "drawable", this.activity?.packageName)
            imageView.setImageResource(id)
        })
    }

}
