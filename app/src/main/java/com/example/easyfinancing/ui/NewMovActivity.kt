package com.example.easyfinancing.ui

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.easyfinancing.R
import com.example.easyfinancing.database.AppDatabase
import com.example.easyfinancing.database.daos.MovimetationDao
import com.example.easyfinancing.database.models.MovimentationModel
import com.example.easyfinancing.ui.fragments.newmov.DateSelector
import com.example.easyfinancing.ui.fragments.newmov.Resume
import com.example.easyfinancing.ui.fragments.newmov.Value
import com.example.easyfinancing.ui.viewmodels.NewMovViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewMovActivity : AppCompatActivity() {

    lateinit var dataBase : AppDatabase
    lateinit var addMovimentation : MovimetationDao

    val viewModel: NewMovViewModel by lazy {
        ViewModelProvider(this).get(NewMovViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_mov)

        this.dataBase = AppDatabase.getInstance(this)
        this.addMovimentation = dataBase.movimentationDao()

        var bar = findViewById<View>(R.id.down_bar_pg)

        var actual_fragment = 0;

        val valueFg = Value()
        val DateFg = DateSelector()
        val ResumeFg = Resume()

        val fragments = mutableListOf(valueFg, DateFg, ResumeFg)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_placer, fragments[actual_fragment])
            commit()
        }

            findViewById<ImageButton>(R.id.bt_avancar_fg).setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                if (actual_fragment < 2) {
                    actual_fragment++
                    replace(R.id.fragment_placer, fragments[actual_fragment])
                    commit()

                    if (actual_fragment == 2){
                        findViewById<ImageButton>(R.id.bt_avancar_fg).background = ContextCompat.getDrawable(this@NewMovActivity, R.drawable.round_background_green)
                        findViewById<ImageButton>(R.id.bt_avancar_fg).setImageResource(R.drawable.check)
                    }

                    bar.layoutParams.width = bar.layoutParams.width + dpToPx(107.33f).toInt()
                    bar.requestLayout()
                }else{
                    CoroutineScope(Dispatchers.IO).launch {
                        addMovimentation.insertMov(
                            MovimentationModel(
                                viewModel.getFormatedDate(viewModel.movDate),
                                if (viewModel.movType) "E" else "S",
                                viewModel.movDesc,
                                viewModel.movCatId,
                                viewModel.movValue,
                                viewModel.movRecurence,
                                viewModel.movCardId,
                                viewModel.movCardInstalments,
                                viewModel.movBudgetId
                            )
                        )
                    }
                    finish()
                }
            }
        }

        findViewById<ImageButton>(R.id.arrow_back_inform_values).setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                if (actual_fragment > 0){actual_fragment--
                    replace(R.id.fragment_placer, fragments[actual_fragment])
                    commit()

                    if (actual_fragment < 2){
                        findViewById<ImageButton>(R.id.bt_avancar_fg).background = ContextCompat.getDrawable(this@NewMovActivity, R.drawable.round_background_blue)
                        findViewById<ImageButton>(R.id.bt_avancar_fg).setImageResource(R.drawable.ic_right)
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