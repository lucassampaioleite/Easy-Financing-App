package com.example.easyfinancing.ui.fragments.newmov

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.adapters.dialogs.DialogBudgetAdapter
import com.example.easyfinancing.ui.adapters.dialogs.DialogCardAdapter
import com.example.easyfinancing.ui.adapters.dialogs.DialogCategoryAdapter
import com.example.easyfinancing.ui.models.budget.Budget
import com.example.easyfinancing.ui.models.card.CardBill
import com.example.easyfinancing.ui.models.category.Category
import com.example.easyfinancing.ui.viewmodels.NewMovViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class Resume : Fragment() {
    val viewModel: NewMovViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_mov_resume, container, false)
        view.findViewById<TextView>(R.id.mov_date_resume).setText(viewModel.getDateFormmated())
        view.findViewById<ImageView>(R.id.mov_type_resume).setImageResource(getType(viewModel.movType))
        view.findViewById<TextView>(R.id.mov_value_resume).setText(viewModel.movValue)
        view.findViewById<TextView>(R.id.mov_desc_resume).setText(viewModel.movDesc)

        setDialogCategories(view)
        setDialogBudgets(view)
        setDialogCard(view)

        var recurenceIndex = 0

        view.findViewById<ImageButton>(R.id.recurence_selection_inner).setOnClickListener {
            recurenceIndex++
            when(recurenceIndex){
                1 -> {
                    view.findViewById<ImageButton>(R.id.recurence_selection_inner).background = ContextCompat.getDrawable(requireContext(), R.drawable.round_background_blue)
                    view.findViewById<ImageButton>(R.id.recurence_selection_inner).setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue_dark))
                    view.findViewById<TextView>(R.id.text_recurence_selection).setText("Diário")
                }
                2 -> view.findViewById<TextView>(R.id.text_recurence_selection).setText("Semanal")
                3 -> view.findViewById<TextView>(R.id.text_recurence_selection).setText("Mensal")
                else -> {
                    view.findViewById<ImageButton>(R.id.recurence_selection_inner).background = ContextCompat.getDrawable(requireContext(), R.drawable.round_background_medium_blue)
                    view.findViewById<ImageButton>(R.id.recurence_selection_inner).setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue_light))
                    view.findViewById<TextView>(R.id.text_recurence_selection).setText("Recorrência")
                    recurenceIndex = 0
                }
            }
        }

        return view
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
            val categories : MutableList<Category> = mutableListOf()
            val dialog = BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme)

            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.setHasFixedSize(true)

            categories.add(Category(0, R.drawable.cat_ic_account_balance, "Salário", "R$ 0,00", 0))
            categories.add(Category(0, R.drawable.cat_ic_beach, "Lazer", "R$ 0,00", 1))
            categories.add(Category(0, R.drawable.cat_ic_house, "Aluguel", "R$ 0,00", 1))
            categories.add(Category(0, R.drawable.cat_ic_book, "Estudos", "R$ 0,00", 1))
            categories.add(Category(0, R.drawable.cat_ic_local_gas, "Combustível", "R$ 0,00", 1))

            val dialogAdapter = DialogCategoryAdapter(view.context, categories)
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
            val budgets : MutableList<Budget> = mutableListOf()

            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.setHasFixedSize(true)

            budgets.add(Budget("Lazer", "0,00"))
            budgets.add(Budget("Combustivel", "0,00"))
            budgets.add(Budget("Gás", "0,00"))
            budgets.add(Budget("Investimentos", "0,00"))

            val dialogAdapter = DialogBudgetAdapter(view.context, budgets)
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
            val cards : MutableList<CardBill> = mutableListOf()

            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.setHasFixedSize(true)

            cards.add(CardBill("Inter"))
            cards.add(CardBill("Nubank"))
            cards.add(CardBill("Santander"))
            cards.add(CardBill("Itaú"))

            val dialogAdapter = DialogCardAdapter(view.context, cards)
            recyclerView.adapter = dialogAdapter

            dialog.setContentView(dialogView)
            dialog.show()
        }
    }
}