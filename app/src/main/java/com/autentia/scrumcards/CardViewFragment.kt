package com.autentia.scrumcards

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.card_view_fragment.*
import android.content.Context
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import com.autentia.scrumcards.cardsmodel.CardsUtil
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import kotlin.math.abs


interface GestureReceiverInterface {
    fun onTouchDown()
    fun onDragLeft(distance: Float)
    fun onDragRight(distance: Float)
    fun onDragFarLeftUp()
    fun onDragFarRightUp()
    fun onTouchUp()
}

class CardViewFragment : Fragment(), GestureReceiverInterface {

    companion object {
        fun newInstance() = CardViewFragment()
    }

    private lateinit var viewModel: CardViewFragmentViewModel
    private var leftImageView: ImageView? = null
    private var rightImageView: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.card_view_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        super.onViewCreated(view, savedInstanceState)
        //val mDetector = GestureDetectorCompat(activity, MyGestureDetector(activity!!, imageView))
        imageView.setOnTouchListener(MyTouchListener(activity!!, innerFrame, this))
        frameLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProviders.of(this.activity!!).get(CardViewFragmentViewModel::class.java)
        viewModel.cardItem.observe(this, Observer<CardsUtil.CardItem> {
            val resourceId = this.resources.getIdentifier(
                CardsUtil.getCardImageIdentifier(it.imageName),
                "drawable",
                this.activity?.packageName
            )
            imageViewAnimatedChange(context!!, imageView, resourceId)
            if (it.bottomText!= null) {
                bottomText.text = getString(
                    this.resources.getIdentifier(
                        it.bottomText,
                        "string",
                        this.activity?.packageName
                    )
                )
            } else {
                bottomText.text = null
            }
        })
    }

    private fun imageViewAnimatedChange(c: Context, v: ImageView, resourceId: Int) {
        val animOut = AnimationUtils.loadAnimation(c, android.R.anim.fade_out)
        animOut.duration = 200
        val animIn = AnimationUtils.loadAnimation(c, android.R.anim.fade_in)
        animIn.duration = 100
        animOut.setAnimationListener(object : AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation) {
                imageView.setImageResource(resourceId)
                v.startAnimation(animIn)
            }
        })
        v.startAnimation(animOut)
    }

    override fun onTouchDown() {
        leftImageView = createImageView(Gravity.LEFT or Gravity.CENTER_VERTICAL, CardsUtil.getPreviousCardItemImageName(viewModel.cardItem.value, viewModel.itemList.value), imageView.width/2, imageView.height/2)
        rightImageView = createImageView(Gravity.RIGHT or Gravity.CENTER_VERTICAL, CardsUtil.getNextCardItemImageName(viewModel.cardItem.value, viewModel.itemList.value), imageView.width/2, imageView.height/2)
    }

    override fun onDragFarLeftUp() {
        val nextCardItem = CardsUtil.getNextCardItem(viewModel.cardItem.value, viewModel.itemList.value)
        if(nextCardItem!=null) {
            viewModel.cardItem.postValue(nextCardItem)
        }
    }

    override fun onDragFarRightUp() {
        val previousCardItem = CardsUtil.getPreviousCardItem(viewModel.cardItem.value, viewModel.itemList.value)
        if(previousCardItem!=null) {
            viewModel.cardItem.postValue(previousCardItem)
        }
    }

    override fun onDragLeft(distance: Float) {
        if (leftImageView?.parent==null) {
            frameLayout.addView(leftImageView)
        }
        leftImageView?.let { it.x = frameLayout.x - it.width + distance
                             it.alpha = abs(distance/(frameLayout.width/2))}
    }

    override fun onDragRight(distance: Float) {
        if(rightImageView?.parent==null) {
            frameLayout.addView(rightImageView)
        }
        rightImageView?.let { it.x = frameLayout.x + frameLayout.width + distance
                              it.alpha = abs(distance/(frameLayout.width/2))}
    }

    override fun onTouchUp() {
        frameLayout.removeView(leftImageView)
        frameLayout.removeView(rightImageView)
    }


    private fun createImageView(gravity: Int, imageName: String?, width: Int, height: Int): ImageView {
        val imageView = ImageView(context)
        if(imageName!= null) {
            val resourceId = context!!.resources.getIdentifier(
                imageName,
                "drawable",
                context?.packageName
            )
            imageView.setImageResource(resourceId)
            imageView.setBackgroundResource(R.drawable.card_view_image_view_background)
        }
        imageView.layoutParams = FrameLayout.LayoutParams(width, height, gravity)
        return imageView
    }
}


class MyTouchListener(var context: Context, private var cardFrameLayout: ViewGroup, var gestureListener: GestureReceiverInterface) : View.OnTouchListener {

    private var mLastTouchX: Float = 0.0f
    private var imageViewInitialX: Float = 0.0f
    private var mPosX: Float = 0.0f

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val imageAppearingDistance = 0
        val changeDistance = cardFrameLayout.width/2

        if (event!=null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Remember where we started (for dragging)
                    gestureListener.onTouchDown()
                    mLastTouchX = event.rawX
                    imageViewInitialX = cardFrameLayout.x
                }
                MotionEvent.ACTION_MOVE -> {
                    mPosX += event.rawX - mLastTouchX
                    cardFrameLayout.x = cardFrameLayout.x + (event.rawX - mLastTouchX)
                    val distance = cardFrameLayout.x - imageViewInitialX
                    Log.i("Distance","Distance $distance")
                    when {
                        distance > imageAppearingDistance -> gestureListener.onDragLeft(distance)
                        distance < -imageAppearingDistance -> gestureListener.onDragRight(distance)
                    }

                    //                    if ((event.rawX - imageViewInitialX) > slideDistance) {
//                        gestureListener.onDragLeft()
//                    } else if((mLastTouchX - event.rawX) > slideDistance)  {
//                        gestureListener.onDragRight()
//                    }
                    // Remember this touch position for the next move event

//                    if ((event.rawX - imageViewInitialX) > slideDistance) {
//                        gestureListener.onDragLeft()
//                    } else if((mLastTouchX - event.rawX) > slideDistance)  {
//                        gestureListener.onDragRight()
//                    }
                    // Remember this touch position for the next move event
                    mLastTouchX = event.rawX
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val distance = cardFrameLayout.x - imageViewInitialX
                    cardFrameLayout.x = imageViewInitialX
                    when {
                        distance > changeDistance -> gestureListener.onDragFarRightUp()
                        distance < -changeDistance -> gestureListener.onDragFarLeftUp()
                    }
                    gestureListener.onTouchUp()
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    mLastTouchX = event.rawX
                }
            }
            return true
        }
        return false
    }
}
