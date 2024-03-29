package com.autentia.scrumcards

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.autentia.scrumcards.cardsmodel.CardsUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CardListFragment.OnListFragmentInteractionListener,
    MainQuestionFragment.OnFragmentInteractionListener {


    override fun onFragmentInteraction(uri: Uri) {

    }


    override fun onListFragmentInteraction(item: CardsUtil.CardItem?, itemList: ArrayList<CardsUtil.CardItem>?) {
        val viewModel = ViewModelProviders.of(this).get(CardViewFragmentViewModel::class.java)
        viewModel.cardItem.postValue(item)
        viewModel.itemList.postValue(itemList)
        findNavController(R.id.navHostFragment).navigate(R.id.action_cardListFragment_to_cardViewFragment)

//        val transaction = this.supportFragmentManager?.beginTransaction()?.apply {
//            // Replace whatever is in the fragment_container view with this fragment,
//            // and add the transaction to the back stack so the user can navigate back
//            setCustomAnimations(0, R.anim.right_to_left_out_animation, 0, R.anim.left_to_right_animation)
//            val cardViewFragment = CardViewFragment()
//            replace(R.id.fragment, cardViewFragment)
//            addToBackStack(null)
//        }
//        // Commit the transaction
//        transaction?.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
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
            R.id.settings_action ->
                {findNavController(R.id.navHostFragment).navigate(R.id.open_settings_fragment)
                return true}
            else -> super.onOptionsItemSelected(item)
        }
    }
}
