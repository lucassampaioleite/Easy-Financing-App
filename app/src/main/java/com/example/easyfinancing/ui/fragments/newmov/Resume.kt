package com.example.easyfinancing.ui.fragments.newmov

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.database.AppDatabase
import com.example.easyfinancing.database.daos.BudgetsDAO
import com.example.easyfinancing.database.daos.CardDao
import com.example.easyfinancing.database.daos.CategoryDao
import com.example.easyfinancing.database.models.BudgetsModel
import com.example.easyfinancing.database.models.CardModel
import com.example.easyfinancing.ui.adapters.dialogs.DialogBudgetAdapter
import com.example.easyfinancing.ui.adapters.dialogs.DialogCardAdapter
import com.example.easyfinancing.ui.adapters.dialogs.DialogCategoryAdapter
import com.example.easyfinancing.ui.models.budget.Budget
import com.example.easyfinancing.ui.models.card.CardBill
import com.example.easyfinancing.ui.models.category.Category
import com.example.easyfinancing.ui.viewmodels.NewMovViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Resume : Fragment() {
    val viewModel: NewMovViewModel by activityViewModels()

    lateinit var categories : List<Category>
    lateinit var budgets : MutableList<BudgetsModel>
    lateinit var cards : MutableList<CardBill>

    var recurenceIndex = 0
    var readonly = false

    lateinit var dataBase : AppDatabase
    lateinit var categoryDao : CategoryDao
    lateinit var budgetDao : BudgetsDAO
    lateinit var cardDao : CardDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.dataBase = AppDatabase.getInstance(requireContext())
        this.categoryDao = dataBase.categoryDao()
        this.cardDao = dataBase.cardDao()
        this.budgetDao = dataBase.budgetsDao()

        CoroutineScope(Dispatchers.IO).launch {
            val DB_Categories = categoryDao.getAllCategories()

            val CategoriesQueryResult : MutableList<Category> = mutableListOf()

            for (i in 0 until DB_Categories.size){
                CategoriesQueryResult.add(Category(DB_Categories[i].id, DB_Categories[i].icon, DB_Categories[i].name, "0", 0))
            }

            categories = CategoriesQueryResult
        }

        CoroutineScope(Dispatchers.IO).launch {
            val DB_Budgets = budgetDao.findAll()

            val BudgetQueryResult : MutableList<BudgetsModel> = mutableListOf()

            for (budget in DB_Budgets){
                BudgetQueryResult.add(BudgetsModel(budget.idBudgetsModel, budget.nameBudgets, budget.valueBudgets))
            }

            budgets = BudgetQueryResult
        }

        CoroutineScope(Dispatchers.IO).launch {
            val DB_Cards = cardDao.getAllCard()

            val CardsQueryResult : MutableList<CardBill> = mutableListOf()

            for (card in DB_Cards){
                CardsQueryResult.add(CardBill(card.id, card.nickname, card.dueDate))
            }

            cards = CardsQueryResult
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_mov_resume, container, false)

        view.findViewById<TextView>(R.id.mov_date_resume).setText(viewModel.getDateExpanded())
        view.findViewById<ImageView>(R.id.mov_type_resume).setImageResource(getType(viewModel.movType))
        view.findViewById<TextView>(R.id.mov_value_resume).setText(viewModel.movValue)
        view.findViewById<TextView>(R.id.mov_desc_resume).setText(viewModel.movDesc)
        setRecurence(view)

        if(!readonly){
            setDialogCategories(view)
            setDialogBudgets(view)
            setDialogCard(view)
        }

        if (viewModel.movCatId != 0){
            for(i in 0 until categories.size){
                if (categories[i].id == viewModel.movCatId){
                    view.findViewById<ImageButton>(R.id.category_selection_inner).setImageResource(categories[i].icon)
                    view.findViewById<TextView>(R.id.text_category_selection).setText(categories[i].name)
                    view.findViewById<ImageButton>(R.id.category_selection_inner).setColorFilter(ContextCompat.getColor(view.context, R.color.light_background))
                    view.findViewById<ImageButton>(R.id.category_selection_inner).background = ContextCompat.getDrawable(view.context, R.drawable.round_background_blue)
                }
            }
        }

        if (viewModel.movBudgetId != 0){
            for(i in 0 until budgets.size){
                if (budgets[i].idBudgetsModel?.toInt() == viewModel.movBudgetId){
                    view.findViewById<TextView>(R.id.text_budget_selection).setText(budgets[i].nameBudgets)
                    view.findViewById<ImageButton>(R.id.budget_selection_inner).setColorFilter(ContextCompat.getColor(view.context, R.color.light_background))
                    view.findViewById<ImageButton>(R.id.budget_selection_inner).background = ContextCompat.getDrawable(view.context, R.drawable.round_background_blue)
                }
            }
        }

        if (viewModel.movCardId != 0){
            for(i in 0 until cards.size){
                if (cards[i].id == viewModel.movCardId){
                    view.findViewById<TextView>(R.id.text_card_selection).setText(cards[i].nickname)
                    view.findViewById<TextView>(R.id.due_card_selection).setText(viewModel.movCardInstalments.toString() + "x")
                    view.findViewById<ImageButton>(R.id.card_selection_inner).setColorFilter(ContextCompat.getColor(view.context, R.color.light_background))
                    view.findViewById<ImageButton>(R.id.card_selection_inner).background = ContextCompat.getDrawable(view.context, R.drawable.round_background_blue)
                }
            }
        }

        return view
    }

    private fun setRecurence(view: View){

        fun setBlueBackGround(text: String, recurence : Int){
            view.findViewById<ImageButton>(R.id.recurence_selection_inner).background = ContextCompat.getDrawable(requireContext(), R.drawable.round_background_blue)
            view.findViewById<ImageButton>(R.id.recurence_selection_inner).setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue_dark))
            view.findViewById<TextView>(R.id.text_recurence_selection).setText(text)
            viewModel.movRecurence = recurence
        }

        fun updateRecurence(recurenceIndex : Int){
            when(recurenceIndex){
                1 -> setBlueBackGround("Diário", 1)
                2 -> setBlueBackGround("Semanal", 2)
                3 -> setBlueBackGround("Mensal", 3)
                else -> {
                    view.findViewById<ImageButton>(R.id.recurence_selection_inner).background = ContextCompat.getDrawable(requireContext(), R.drawable.round_background_medium_blue)
                    view.findViewById<ImageButton>(R.id.recurence_selection_inner).setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue_light))
                    view.findViewById<TextView>(R.id.text_recurence_selection).setText("Recorrência")
                    this.recurenceIndex = 0

                    viewModel.movRecurence = 0
                }
            }
        }

        recurenceIndex = viewModel.movRecurence
        updateRecurence(recurenceIndex)

        if (readonly) return

        view.findViewById<ImageButton>(R.id.recurence_selection_inner).setOnClickListener {
            recurenceIndex++
            updateRecurence(recurenceIndex)
        }
    }

    fun getType(type : Boolean): Int {
        if (type){
            return R.drawable.arrow_drop_up
        }
        return R.drawable.arrow_drop_down
    }

    private fun setDialogCategories(view: View){
        view.findViewById<ImageButton>(R.id.category_selection_inner).setOnClickListener {

            val dialogView = layoutInflater.inflate(R.layout.dialog_category_new_mov_selection, null)
            var recyclerView : RecyclerView = dialogView.findViewById(R.id.category_itens_recycle)
            val dialog = BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme)

            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.setHasFixedSize(true)

            val dialogAdapter = DialogCategoryAdapter(view.context, categories){

                view.findViewById<ImageButton>(R.id.category_selection_inner).setImageResource(it.icon)
                view.findViewById<TextView>(R.id.text_category_selection).setText(it.name)
                view.findViewById<ImageButton>(R.id.category_selection_inner).setColorFilter(ContextCompat.getColor(view.context, R.color.light_background))
                view.findViewById<ImageButton>(R.id.category_selection_inner).background = ContextCompat.getDrawable(view.context, R.drawable.round_background_blue)

                viewModel.movCatId = it.id

                dialog.dismiss()
            }

            recyclerView.adapter = dialogAdapter

            dialog.setContentView(dialogView)
            dialog.show()
        }
    }

    private fun setDialogBudgets(view: View){
        view.findViewById<ImageButton>(R.id.budget_selection_inner).setOnClickListener {

            val dialogView = layoutInflater.inflate(R.layout.dialog_budget_new_mov_selection, null)
            val recyclerView : RecyclerView = dialogView.findViewById(R.id.budget_itens_recycle)
            val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)

            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.setHasFixedSize(true)

            val dialogAdapter = DialogBudgetAdapter(view.context, budgets){
                view.findViewById<TextView>(R.id.text_budget_selection).setText(it.nameBudgets)
                view.findViewById<ImageButton>(R.id.budget_selection_inner).setColorFilter(ContextCompat.getColor(view.context, R.color.light_background))
                view.findViewById<ImageButton>(R.id.budget_selection_inner).background = ContextCompat.getDrawable(view.context, R.drawable.round_background_blue)

                viewModel.movBudgetId = it.idBudgetsModel?.toInt()!!

                dialog.dismiss()
            }
            recyclerView.adapter = dialogAdapter

            dialog.setContentView(dialogView)
            dialog.show()
        }
    }

    private fun setDialogCard(view: View){
        view.findViewById<ImageButton>(R.id.card_selection_inner).setOnClickListener {

            val dialogView = layoutInflater.inflate(R.layout.dialog_card_new_mov_selection, null)
            val recyclerView : RecyclerView = dialogView.findViewById(R.id.card_itens_recycle)
            val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)

            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.setHasFixedSize(true)

            dialogView.findViewById<EditText>(R.id.instalments_number).setText(viewModel.movCardInstalments.toString())

            val dialogAdapter = DialogCardAdapter(view.context, cards){

                val selectedCardInfo = it

                dialogView.findViewById<Button>(R.id.get_card_selected).setOnClickListener {
                    view.findViewById<TextView>(R.id.text_card_selection).setText(selectedCardInfo.nickname)
                    view.findViewById<TextView>(R.id.due_card_selection).setText(dialogView.findViewById<EditText>(R.id.instalments_number).text.toString() + "x")
                    view.findViewById<ImageButton>(R.id.card_selection_inner).setColorFilter(ContextCompat.getColor(view.context, R.color.light_background))
                    view.findViewById<ImageButton>(R.id.card_selection_inner).background = ContextCompat.getDrawable(view.context, R.drawable.round_background_blue)

                    viewModel.movCardId = selectedCardInfo.id
                    viewModel.movCardInstalments = dialogView.findViewById<EditText>(R.id.instalments_number).text.toString().toInt()

                    dialog.dismiss()
                }
            }
            recyclerView.adapter = dialogAdapter

            dialog.setContentView(dialogView)
            dialog.show()
        }
    }
}