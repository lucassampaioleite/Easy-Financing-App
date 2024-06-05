package com.example.easyfinancing.ui.fragments.newmov

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.viewmodels.NewMovViewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

class Value : Fragment() {

    val viewModel : NewMovViewModel by activityViewModels()
    var type : Boolean = true
    lateinit var value: EditText
    lateinit var description : EditText

    private var current = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_mov_values, container, false)

        val btnEntrada = view.findViewById<Button>(R.id.btn_type_entrada)
        val btnSaida = view.findViewById<Button>(R.id.btn_type_saida)

        btnEntrada.setOnClickListener{
            btnEntrada.background = ContextCompat.getDrawable(requireContext(), R.drawable.mov_type_clicked)
            btnSaida.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent)
            viewModel.movType = true
        }

        btnSaida.setOnClickListener{
            btnSaida.background = ContextCompat.getDrawable(requireContext(), R.drawable.mov_type_clicked)
            btnEntrada.background = ContextCompat.getDrawable(requireContext(), R.drawable.transparent)
            viewModel.movType = false
        }

        value = view.findViewById(R.id.edit_value)
        value.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != current) {
                    value.removeTextChangedListener(this)

                    val cleanString = s.toString().replace("[R$,.]".toRegex(), "")
                    val parsed = cleanString.toDoubleOrNull() ?: 0.0

                    val symbols = DecimalFormatSymbols(Locale("pt", "BR"))
                    symbols.decimalSeparator = ','
                    symbols.groupingSeparator = '.'

                    val decimalFormat = DecimalFormat("#,##0.00", symbols)
                    val formatted = decimalFormat.format((parsed / 100))

                    current = "R$ $formatted"
                    value.setText(current)
                    value.setSelection(current.length)

                    value.addTextChangedListener(this)
                    viewModel.movValue = current
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        description = view.findViewById(R.id.mov_description)
        description.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.movDesc = description.text.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }
}