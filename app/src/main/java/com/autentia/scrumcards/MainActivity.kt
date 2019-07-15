package com.autentia.scrumcards

import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.autentia.scrumcards.CardsModel.CardsContent

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CardListFragment.OnListFragmentInteractionListener, MainQuestionFragment.OnFragmentInteractionListener{


    override fun onFragmentInteraction(uri: Uri) {

    }


    override fun onListFragmentInteraction(item: CardsContent.CardItem?) {
        print("Click has happened")
        val viewModel = ViewModelProviders.of(this).get(CardViewFragmentViewModel::class.java)
        viewModel.imageName.postValue(item?.imageName)

        val transaction = this.supportFragmentManager?.beginTransaction()?.apply {
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            setCustomAnimations(0, R.anim.right_to_left_out_animation, 0, R.anim.left_to_right_animation)
            val cardViewFragment = CardViewFragment()
            replace(R.id.fragment, cardViewFragment)
            addToBackStack(null)
        }
        // Commit the transaction
        transaction?.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
