package com.autentia.scrumcards

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.card_view_fragment.*
import android.content.Context
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import com.autentia.scrumcards.cardsmodel.CardsUtil
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils


interface GestureReceiverInterface {
    fun onBeforeAction()
    fun onActionDown()
    fun onActionMoveMuchLeft()
    fun onActionMoveMuchRight()
    fun onDragFarLeft()
    fun onDragFarRight()
    fun onActionUp()
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

    fun imageViewAnimatedChange(c: Context, v: ImageView, resourceId: Int) {
        val anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out)
        anim_out.duration = 200
        val anim_in = AnimationUtils.loadAnimation(c, android.R.anim.fade_in)
        anim_in.duration = 100
        anim_out.setAnimationListener(object : AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation) {
                imageView.setImageResource(resourceId)
                v.startAnimation(anim_in)
            }
        })
        v.startAnimation(anim_out)
    }

    override fun onActionDown() {
        leftImageView = createImageView(Gravity.LEFT or Gravity.CENTER_VERTICAL, CardsUtil.getPreviousCardItemImageName(viewModel.cardItem.value, viewModel.itemList.value), imageView.width/2, imageView.height/2)
        rightImageView = createImageView(Gravity.RIGHT or Gravity.CENTER_VERTICAL, CardsUtil.getNextCardItemImageName(viewModel.cardItem.value, viewModel.itemList.value), imageView.width/2, imageView.height/2)
    }

    override fun onDragFarLeft() {
        val nextCardItem = CardsUtil.getNextCardItem(viewModel.cardItem.value, viewModel.itemList.value)
        if(nextCardItem!=null) {
            viewModel.cardItem.postValue(nextCardItem)
        }
    }

    override fun onDragFarRight() {
        var previousCardItem = CardsUtil.getPreviousCardItem(viewModel.cardItem.value, viewModel.itemList.value)
        if(previousCardItem!=null) {
            viewModel.cardItem.postValue(previousCardItem)
        }
    }

    override fun onActionMoveMuchLeft() {
        if (leftImageView?.parent==null) {
            frameLayout.addView(leftImageView)
        }
    }

    override fun onActionMoveMuchRight() {
        if(rightImageView?.parent==null) {
            frameLayout.addView(rightImageView)
        }
    }

    override fun onActionUp() {
        frameLayout.removeView(leftImageView)
        frameLayout.removeView(rightImageView)
    }

    override fun onBeforeAction() {

    }

    private fun createImageView(gravity: Int, imageName: String?, width: Int, height: Int): ImageView {
        var imageView = ImageView(context)
        if(imageName!= null) {
            val resourceId = context!!.resources.getIdentifier(
                imageName,
                "drawable",
                context?.packageName
            )
            imageView.setImageResource(resourceId)
            imageView.setBackgroundResource(R.drawable.card_view_image_view_background)
            imageView.elevation = 5.0f
        }
        imageView.layoutParams = FrameLayout.LayoutParams(width, height, gravity)
        return imageView
    }
}


class MyTouchListener(var context: Context, var frameLayout: ViewGroup, var gestureListener: GestureReceiverInterface) : View.OnTouchListener {

    var mLastTouchX: Float = 0.0f
    var imageViewInitialX: Float = 0.0f
    var mPosX: Float = 0.0f

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        gestureListener.onBeforeAction()
        val slideDistance = 100
        val changeDistance = 200

        if (event!=null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Remember where we started (for dragging)
                    gestureListener.onActionDown()
                    mLastTouchX = event.rawX
                    imageViewInitialX = frameLayout.x
                }
                MotionEvent.ACTION_MOVE -> {
                    mPosX += event.rawX - mLastTouchX
                    frameLayout.x = frameLayout.x + (event.rawX - mLastTouchX)
                    val distance = frameLayout.x - imageViewInitialX
                    when {
                        distance > slideDistance -> gestureListener.onActionMoveMuchLeft()
                        distance < -slideDistance -> gestureListener.onActionMoveMuchRight()
                    }

                    //                    if ((event.rawX - imageViewInitialX) > slideDistance) {
//                        gestureListener.onActionMoveMuchLeft()
//                    } else if((mLastTouchX - event.rawX) > slideDistance)  {
//                        gestureListener.onActionMoveMuchRight()
//                    }
                    // Remember this touch position for the next move event

//                    if ((event.rawX - imageViewInitialX) > slideDistance) {
//                        gestureListener.onActionMoveMuchLeft()
//                    } else if((mLastTouchX - event.rawX) > slideDistance)  {
//                        gestureListener.onActionMoveMuchRight()
//                    }
                    // Remember this touch position for the next move event
                    mLastTouchX = event.rawX
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val distance = frameLayout.x - imageViewInitialX
                    frameLayout.x = imageViewInitialX
                    when {
                        distance > changeDistance -> gestureListener.onDragFarRight()
                        distance < -changeDistance -> gestureListener.onDragFarLeft()
                    }
                    gestureListener.onActionUp()
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
