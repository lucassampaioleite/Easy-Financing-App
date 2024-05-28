package com.example.easyfinancing.ui

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.fragments.newmov.DateSelector
import com.example.easyfinancing.ui.fragments.newmov.Resume
import com.example.easyfinancing.ui.fragments.newmov.Value
import com.example.easyfinancing.ui.viewmodels.NewMovViewModel

class NewMovActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_mov)

        var bar = findViewById<View>(R.id.down_bar_pg)

        ViewModelProvider(this).get(NewMovViewModel::class.java)

        var actual_fragment = 0;

        val valueFg = Value()
        val DateFg = DateSelector()

        val fragments = mutableListOf(valueFg, DateFg, Resume())

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_placer, fragments[actual_fragment])
            commit()
        }

        val btnNext = findViewById<ImageButton>(R.id.bt_avancar_fg)
            btnNext.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                if (actual_fragment < 2) {
                    actual_fragment++
                    replace(R.id.fragment_placer, fragments[actual_fragment])
                    commit()

                    if (actual_fragment == 2){
                        btnNext.background = ContextCompat.getDrawable(this@NewMovActivity, R.drawable.round_background_green)
                        btnNext.setImageResource(R.drawable.check)
                    }

                    bar.layoutParams.width = bar.layoutParams.width + dpToPx(107.33f).toInt()
                    bar.requestLayout()
                }
            }
        }

        findViewById<ImageButton>(R.id.arrow_back_inform_values).setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                if (actual_fragment > 0){actual_fragment--
                    replace(R.id.fragment_placer, fragments[actual_fragment])
                    commit()

                    if (actual_fragment < 2){
                        btnNext.background = ContextCompat.getDrawable(this@NewMovActivity, R.drawable.round_background_blue)
                        btnNext.setImageResource(R.drawable.ic_right)
                    }

                    bar.layoutParams.width = bar.layoutParams.width - dpToPx(107.33f).toInt()
                    bar.requestLayout()
                }else{finish()}
            }
        }

        findViewById<ImageButton>(R.id.bt_cancelar).setOnClickListener{
            finish()
        }
    }
}

fun Context.dpToPx(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics)
}