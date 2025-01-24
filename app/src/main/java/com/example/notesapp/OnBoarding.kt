package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment.SavedState
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.notesapp.ONBOARDING.SharedPref

class OnBoarding : AppCompatActivity() {
    lateinit var savedstate:SharedPref
    lateinit var viewPager:ViewPager
    lateinit var nextBt:CardView
    lateinit var textDes:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_on_boarding)

        savedstate=SharedPref(this@OnBoarding,"OB")





        if(savedstate.getState()==1){
            startActivity(Intent(this@OnBoarding,MainActivity::class.java))
            finish()

        }




        nextBt=findViewById(R.id.nextBt)
        viewPager=findViewById(R.id.slideViewPager)


        nextBt.bringToFront()

        viewPager.addOnPageChangeListener(object: OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if(position==2){
                    savedstate.setState(1)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }


        })




        nextBt.setOnClickListener {
            if(getItem(0)<1){
                viewPager.setCurrentItem(getItem(1),true)
            }else{
                savedstate.setState(1)
                startActivity(Intent(this@OnBoarding,MainActivity::class.java))
                finish()
            }
        }


        val Onadapter=ONboardingAdapter(this)
        viewPager.adapter=Onadapter





    }


    private fun  getItem(i:Int):Int{
        return viewPager.currentItem + i
    }
}