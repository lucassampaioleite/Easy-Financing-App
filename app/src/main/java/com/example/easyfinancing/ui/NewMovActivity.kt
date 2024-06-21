package com.example.easyfinancing.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.easyfinancing.ui.models.extract.Movimentation
import com.example.easyfinancing.ui.viewmodels.NewMovViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

class NewMovActivity : AppCompatActivity() {

    lateinit var dataBase : AppDatabase
    lateinit var movimentationDao : MovimetationDao

    val viewModel: NewMovViewModel by lazy {
        ViewModelProvider(this).get(NewMovViewModel::class.java)
    }

    private var editMovimentation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_mov)

        val mov = intent.getParcelableExtra<Movimentation>("mov")

        if (mov != null) {
            viewModel.movId = mov.id
            viewModel.movType = mov.type
            viewModel.movDate = mov.date
            viewModel.movDesc = mov.mainDescription
            viewModel.movCatId = mov.categoryId
            viewModel.movValue = mov.movAmount
            viewModel.movCardId = mov.cardId
            viewModel.movCardInstalments = mov.cardInstalments
            viewModel.movRecurence = mov.recurenceId
            viewModel.movBudgetId = mov.budgetId

            editMovimentation =  true
        }


        this.dataBase = AppDatabase.getInstance(this)
        this.movimentationDao = dataBase.movimentationDao()

        var bar = findViewById<View>(R.id.down_bar_pg)

        var actual_fragment = 0;

        val valueFg = Value()
        val DateFg = DateSelector()
        val ResumeFg = Resume()

        val fragments = mutableListOf(valueFg, DateFg, ResumeFg)

        if(!editMovimentation){

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_placer, fragments[0])
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
                            if (viewModel.movCardId != 0){
                                val uuid = UUID.randomUUID().toString()

                                for (i in 1..viewModel.movCardInstalments){

                                    fun instalmentDate (date : String, step : Int) : String{

                                        val stringDate = date.split("-")

                                        if ((stringDate[1].toInt() + i) > 12){
                                            return "${stringDate[0].toInt() + 1}-${if (i < 10) "0" + i else i}-${stringDate[2]}"
                                        }

                                        return "${stringDate[0]}-${if ((stringDate[1].toInt() + i) < 10) "0" + (stringDate[1].toInt() + i) else (stringDate[1].toInt() + i)}-${stringDate[2]}"
                                    }

                                    movimentationDao.insertMov(
                                        MovimentationModel(
                                            instalmentDate(viewModel.getFormatedDate(viewModel.movDate), i),
                                            viewModel.movType,
                                            viewModel.movDesc,
                                            viewModel.movCatId,
                                            viewModel.movValue,
                                            viewModel.movRecurence,
                                            viewModel.movCardId,
                                            i,
                                            viewModel.movCardInstalments,
                                            uuid,
                                            viewModel.movBudgetId
                                        )
                                    )
                                }
                            }else{
                                movimentationDao.insertMov(
                                    MovimentationModel(
                                        viewModel.getFormatedDate(viewModel.movDate),
                                        viewModel.movType,
                                        viewModel.movDesc,
                                        viewModel.movCatId,
                                        viewModel.movValue,
                                        viewModel.movRecurence,
                                        viewModel.movCardId,
                                        0,
                                        viewModel.movCardInstalments,
                                        "",
                                        viewModel.movBudgetId
                                    )
                                )
                            }
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

        }else{

            ResumeFg.readonly = true

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_placer, fragments[2])
                commit()

                bar.layoutParams.width = bar.layoutParams.width + (dpToPx(107.33f).toInt() * 2)
                bar.requestLayout()
            }

            findViewById<ImageButton>(R.id.bt_cancelar).setImageResource(R.drawable.delete)
            findViewById<ImageButton>(R.id.bt_cancelar).setOnClickListener{
                if (editMovimentation){
                    CoroutineScope(Dispatchers.IO).launch {
                        if (viewModel.movCardId != 0){
                            movimentationDao.deleteCardMov(movimentationDao.getInstalmentsCode(viewModel.movId))
                        }else{
                            movimentationDao.deleteMov(viewModel.movId)
                        }
                    }
                    finish()
                }else{
                    finish()
                }
            }

            findViewById<ImageButton>(R.id.bt_avancar_fg).setImageResource(R.drawable.edit)
            findViewById<ImageButton>(R.id.bt_avancar_fg).setOnClickListener{

                if(editMovimentation){
                    ResumeFg.readonly = false

                    findViewById<ImageButton>(R.id.bt_avancar_fg).setImageResource(R.drawable.ic_right)
                    findViewById<ImageButton>(R.id.bt_cancelar).setImageResource(R.drawable.ic_close)

                    if (actual_fragment == 0){
                        bar.layoutParams.width = bar.layoutParams.width - (dpToPx(107.33f).toInt() * 2)
                        bar.requestLayout()
                    }

                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragment_placer, fragments[actual_fragment])
                        commit()
                    }

                    editMovimentation = false
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

                                if(movimentationDao.verifyIfMovHasCard(viewModel.movId) != 0){
                                    movimentationDao.deleteCardMov(movimentationDao.getInstalmentsCode(viewModel.movId))

                                    if (viewModel.movCardId != 0){
                                        val uuid = UUID.randomUUID().toString()

                                        for (i in 1..viewModel.movCardInstalments){

                                            fun instalmentDate (date : String, step : Int) : String{

                                                val stringDate = date.split("-")

                                                if ((stringDate[1].toInt() + i) > 12){
                                                    return "${stringDate[0].toInt() + 1}-${if (i<10) "0" + i else i}-${stringDate[2]}"
                                                }

                                                return "${stringDate[0]}-${if ((stringDate[1].toInt() + i) < 10) "0" + (stringDate[1].toInt() + i) else (stringDate[1].toInt() + i)}-${stringDate[2]}"
                                            }

                                            movimentationDao.insertMov(
                                                MovimentationModel(
                                                    instalmentDate(viewModel.getFormatedDate(viewModel.movDate), i),
                                                    viewModel.movType,
                                                    viewModel.movDesc,
                                                    viewModel.movCatId,
                                                    viewModel.movValue,
                                                    viewModel.movRecurence,
                                                    viewModel.movCardId,
                                                    i,
                                                    viewModel.movCardInstalments,
                                                    uuid,
                                                    viewModel.movBudgetId
                                                )
                                            )
                                        }
                                    }else{
                                        movimentationDao.insertMov(
                                            MovimentationModel(
                                                viewModel.getFormatedDate(viewModel.movDate),
                                                viewModel.movType,
                                                viewModel.movDesc,
                                                viewModel.movCatId,
                                                viewModel.movValue,
                                                viewModel.movRecurence,
                                                viewModel.movCardId,
                                                0,
                                                viewModel.movCardInstalments,
                                                "",
                                                viewModel.movBudgetId
                                            )
                                        )
                                    }
                                }else{
                                    movimentationDao.updateMov(
                                        viewModel.getFormatedDate(viewModel.movDate),
                                        viewModel.movType,
                                        viewModel.movDesc,
                                        viewModel.movCatId,
                                        viewModel.movValue,
                                        viewModel.movRecurence,
                                        viewModel.movCardId,
                                        viewModel.movCardInstalments,
                                        viewModel.movBudgetId,
                                        viewModel.movId
                                    )
                                }
                            }
                            finish()
                        }
                    }
                }
            }

            findViewById<ImageButton>(R.id.arrow_back_inform_values).setOnClickListener{
                supportFragmentManager.beginTransaction().apply {
                    if (actual_fragment > 0 && !editMovimentation){

                        actual_fragment--

                        replace(R.id.fragment_placer, fragments[actual_fragment])
                        commit()

                        if (actual_fragment < 2){
                            findViewById<ImageButton>(R.id.bt_avancar_fg).background = ContextCompat.getDrawable(this@NewMovActivity, R.drawable.round_background_blue)
                            findViewById<ImageButton>(R.id.bt_avancar_fg).setImageResource(R.drawable.ic_right)
                        }

                        bar.layoutParams.width = bar.layoutParams.width - dpToPx(107.33f).toInt()
                        bar.requestLayout()

                        editMovimentation = false
                    }else{finish()}
                }
            }
        }
    }
}

fun Context.dpToPx(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics)
}