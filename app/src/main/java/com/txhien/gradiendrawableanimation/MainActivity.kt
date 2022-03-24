package com.txhien.gradiendrawableanimation

import android.content.res.Resources
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Hita on 25,March,2022
 **/

class MainActivity : AppCompatActivity() {
    companion object {
        private const val INDEX_LAST_FRAME = 1
        private const val DURATION_ANIMATION_FADE_OUT = 100
        private const val DURATION_ANIMATION_FADE_IN = 1900
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val redBlueGradient = getGradientDrawable(R.color.red, R.color.blue)
        val greenYellowGradient = getGradientDrawable(R.color.green, R.color.yellow)

        gradient_view.background = redBlueGradient
        gradient_view.setOnClickListener {
            handleGradientViewClick(redBlueGradient, it, greenYellowGradient)
        }
    }

    private fun handleGradientViewClick(redBlueGradient: GradientDrawable, it: View, greenYellowGradient: GradientDrawable) {
        var startGradient = redBlueGradient

        if (it.background is GradientDrawable)
            startGradient = (it.background as GradientDrawable)
        else if (it.background is AnimationDrawable)
            startGradient = (it.background as AnimationDrawable).getFrame(INDEX_LAST_FRAME) as GradientDrawable

        val endGradient = if (startGradient == redBlueGradient) greenYellowGradient else redBlueGradient
        animateBackgroundColor(it, startGradient, endGradient)
    }

    private fun animateBackgroundColor(view: View, startGradient: GradientDrawable, endGradient: GradientDrawable) {
        val animationDrawable = AnimationDrawable()
        animationDrawable.addFrame(startGradient, DURATION_ANIMATION_FADE_OUT)
        animationDrawable.addFrame(endGradient, DURATION_ANIMATION_FADE_IN)
        animationDrawable.setEnterFadeDuration(DURATION_ANIMATION_FADE_OUT)
        animationDrawable.setExitFadeDuration(DURATION_ANIMATION_FADE_IN)

        view.background = animationDrawable
        animationDrawable.isOneShot = true
        animationDrawable.start()
    }

    private fun getGradientDrawable(@ColorRes leftColor: Int, @ColorRes rightColor: Int): GradientDrawable {
        //Vertical gradient
        return GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(resources.getColor(leftColor, this.theme), resources.getColor(rightColor, this.theme)))
    }

}