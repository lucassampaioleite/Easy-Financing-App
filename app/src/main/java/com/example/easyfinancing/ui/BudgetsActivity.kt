package com.example.easyfinancing.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.database.AppDatabase
import com.example.easyfinancing.database.daos.BudgetsDAO
import com.example.easyfinancing.database.models.BudgetsModel
import com.example.easyfinancing.ui.adapters.budget.BudgetsAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class BudgetsActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var budgetsDAO: BudgetsDAO
    private lateinit var budgetsEntity: BudgetsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budgets)
        botaoVoltar()
        buttonNewBudget()
        openConnection()
    }

    override fun onResume() {
        super.onResume()

        val budgetsToEntity: MutableList<BudgetsModel> = mutableListOf()

        recyclerView = findViewById(R.id.budgets_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        CoroutineScope(Dispatchers.IO).launch {
            val listBudgets = budgetsDAO.findAll()
            for (i in listBudgets.indices) {
                budgetsToEntity.add(
                    BudgetsModel(listBudgets[i].idBudgetsModel, listBudgets[i].nameBudgets,
                        listBudgets[i].valueBudgets
                    )
                )
            }
        }
        recyclerView.adapter = BudgetsAdapter(applicationContext, budgetsToEntity)
    }

    private fun botaoVoltar() {
        findViewById<ImageButton>(R.id.arrow_back_budgets).setOnClickListener {
            finish()
        }
    }

    private fun getElementToEntity(name: String, value: String) =
        BudgetsModel(nameBudgets = name, valueBudgets = value.toDouble())

    private fun openConnection() {
        this.database = AppDatabase.getInstance(this)
        this.budgetsDAO = database.budgetsDao()
    }


    private fun buttonNewBudget() {
        findViewById<ImageButton>(R.id.add_new_budget).setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_budget_new_item_form, null)

//            dialogView.findViewById<Button>(R.id.new_budget_save_button).setOnClickListener {
//                Toast.makeText(dialogView.context, dialogView.findViewById<EditText>(R.id.budget_value).text.toString(), Toast.LENGTH_SHORT).show()
//            }

            val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            editTextMoneyMask(dialogView.findViewById(R.id.budget_value))
            dialogView.findViewById<Button>(R.id.new_budget_save_button).setOnClickListener {
                val arrayFormsBudgets = adjustElements(
                    dialogView.findViewById<EditText>(R.id.budget_name).text,
                    dialogView.findViewById<EditText>(R.id.budget_value).text
                )
                budgetsEntity = getElementToEntity(arrayFormsBudgets[0], arrayFormsBudgets[1])
                CoroutineScope(Dispatchers.IO).launch {
                    budgetsDAO.insert(budgetsEntity)
                }
                dialog.dismiss()
            }
            dialog.setContentView(dialogView)
            dialog.show()
        }
    }

    var current = ""
    private fun editTextMoneyMask(editText: EditText) {
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

    private fun adjustElements(viewName: Editable, viewValue: Editable): Array<String> {
        val name = viewName.toString()
        val value = viewValue.toString().replace("R$ ", "").replace(".", "")
            .replace(",", ".")
        return arrayOf(name, value)
    }
}