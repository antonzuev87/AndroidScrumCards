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
        imageView.setOnTouchListener(MyTouchListener(activity!!, imageView, this))
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
            imageView.elevation = 10.0f
        }
        imageView.layoutParams = FrameLayout.LayoutParams(width, height, gravity)
        return imageView
    }
}


class MyTouchListener(var context: Context, var imageView: ImageView, var gestureListener: GestureReceiverInterface) : View.OnTouchListener {

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
                    imageViewInitialX = imageView.x
                }
                MotionEvent.ACTION_MOVE -> {
                    mPosX += event.rawX - mLastTouchX
                    imageView.x = imageView.x + (event.rawX - mLastTouchX)
                    val distance = imageView.x - imageViewInitialX
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
                    val distance = imageView.x - imageViewInitialX
                    imageView.x = imageViewInitialX
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



class MyGestureDetector(var context: Context, var imageView: ImageView) : GestureDetector.SimpleOnGestureListener() {

    var coordinateX = imageView.x

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        if (e2!=null && e1!=null) {
            imageView.x = (e2.x - e1.x) + imageView.x
            Log.d("TRANSLATION", "DISTANCE X ${e2.rawX}")
            return true
        }
        return false
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        if (e!=null) {
            imageView.x = coordinateX
            return true
        }
        return false
    }
//    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
//        try {
//            if (Math.abs(e1.y - e2.y) > SWIPE_MAX_OFF_PATH)
//                return false
//            if (e1.x - e2.x > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                imageView.translationX = e2.x
//                Toast.makeText(this.context, "Left Swipe", Toast.LENGTH_SHORT).show()
//            } else if (e2.x - e1.x > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                imageView.translationX = e1.x
//                Toast.makeText(this.context, "Right Swipe", Toast.LENGTH_SHORT).show()
//            }
//        } catch (e: Exception) {
//            // nothing
//        }
//
//        return false
//    }

    override fun onDown(e: MotionEvent?): Boolean {
        //coordinateX = imageView.x
        return true
    }
}
