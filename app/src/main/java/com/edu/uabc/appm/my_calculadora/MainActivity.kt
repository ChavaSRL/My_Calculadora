package com.edu.uabc.appm.my_calculadora

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    // ingresar valores
    lateinit var txtInput: TextView

    // validar si presionamos un numero
    var UltimoNum: Boolean = false

    // valida que aya error
    var ValidarError: Boolean = false

    // si esta lleno el ultimo no perimite ingresar mas valores
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtInput = findViewById(R.id.txtInput)
    }

    /**
     * Append the Button.text to the TextView
     */
    fun onDigit(view: View) {
        if (ValidarError) {
            // If current state is Error, replace the error message
            txtInput.text = (view as Button).text
            ValidarError = false
        } else {
            // If not, already there is a valid expression so append to it
            txtInput.append((view as Button).text)
        }
        // Set the flag
        UltimoNum = true
    }

    /**
     * Append . to the TextView
     */
    fun onDecimalPoint(view: View) {
        if (UltimoNum && !ValidarError && !lastDot) {
            txtInput.append(".")
            UltimoNum = false
            lastDot = true
        }
    }

    /**
     * Append +,-,*,/ operators to the TextView
     */
    fun onOperator(view: View) {
        if (UltimoNum && !ValidarError) {
            txtInput.append((view as Button).text)
            UltimoNum = false
            lastDot = false    // Reset the DOT flag
        }
    }


    /**
     * Limpiar el textview
     */
    fun onClear(view: View) {
        this.txtInput.text = ""
        UltimoNum = false
        ValidarError = false
        lastDot = false
    }

    /**
     * Calculate the output using Exp4j
     */
    fun onEqual(view: View) {
        // If the current state is error, nothing to do.
        // If the last input is a number only, solution can be found.
        if (UltimoNum && !ValidarError) {
            // selee el resultado
            val txt = txtInput.text.toString()
            // Create an Expression (A class from exp4j library)
            val expression = ExpressionBuilder(txt).build()
            try {
                // se muestra el resultado
                val result = expression.evaluate()
                txtInput.text = result.toString()
                lastDot = true // Result contains a dot
            } catch (ex: ArithmeticException) {
                // Se mmuestra el mensaje d error
                txtInput.text = "Error la vida no tiene sentido"
                ValidarError = true
                UltimoNum = false
            }
        }
    }
}