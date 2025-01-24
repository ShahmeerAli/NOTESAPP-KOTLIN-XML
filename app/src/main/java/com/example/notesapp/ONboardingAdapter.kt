package com.example.notesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.viewpager.widget.PagerAdapter
import com.airbnb.lottie.LottieAnimationView

class ONboardingAdapter (var context: Context):PagerAdapter(){

    lateinit var layoutInflater: LayoutInflater

    val desc= arrayOf(
        R.string.Screen1Desc,
        R.string.Screen2Desc
    )

    val anim= arrayOf(
        R.raw.welcome,
        R.raw.second
    )




    override fun getCount(): Int {
    return desc.size
    }



    override fun isViewFromObject(view: View, `object`: Any): Boolean {

        return view == `object` as ConstraintLayout

    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view=layoutInflater.inflate(R.layout.slider,container,false)

        val animation:LottieAnimationView=view.findViewById(R.id.lottieAnimationView)
        val textDesc:TextView=view.findViewById(R.id.TextDescription)


        animation.setAnimation(anim[position])
        textDesc.setText(desc[position])

        container.addView(view)

        return view


    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
       container.removeView(`object`  as ConstraintLayout)

    }




}