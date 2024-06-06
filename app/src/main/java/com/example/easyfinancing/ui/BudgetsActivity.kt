package com.example.easyfinancing.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.adapters.budget.BudgetAdapter
import com.example.easyfinancing.ui.models.budget.Budget
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class BudgetsActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    val budgets: MutableList<Budget> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budgets)
        botaoVoltar()
        buttonNewBudget()

        recyclerView = findViewById(R.id.budgets_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        budgets.add(Budget(1,"Teste", "R$ 10,00"))
        val adapter = BudgetAdapter(this, budgets)
        recyclerView.adapter = adapter
    }

    private fun botaoVoltar(){
        findViewById<ImageButton>(R.id.arrow_back_budgets).setOnClickListener{
            finish()
        }
    }

    private fun buttonNewBudget(){
        findViewById<ImageButton>(R.id.add_new_budget).setOnClickListener {

            val dialogView = layoutInflater.inflate(R.layout.dialog_budget_new_item_form, null)

            EditTextMoneyMask(dialogView.findViewById(R.id.budget_value))

            val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            dialog.setContentView(dialogView)
            dialog.show()
        }
    }

    var current = ""
    private fun EditTextMoneyMask(editText: EditText){
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != current) {
                    editText.removeTextChangedListener(this)

                    val cleanString = s.toString().replace("[R$,.]".toRegex(), "")
                    val parsed = cleanString.toDoubleOrNull() ?: 0.0

                    val symbols = DecimalFormatSymbols(Locale("pt", "BR"))
                    symbols.decimalSeparator = ','
                    symbols.groupingSeparator = '.'

                    val decimalFormat = DecimalFormat("#,##0.00", symbols)
                    val formatted = decimalFormat.format((parsed / 100))

                    current = "R$ $formatted"
                    editText.setText(current)
                    editText.setSelection(current.length)

                    editText.addTextChangedListener(this)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}