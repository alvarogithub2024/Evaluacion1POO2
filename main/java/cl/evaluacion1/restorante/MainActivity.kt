package cl.evaluacion1.restorante

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var propinaAplicada: Boolean = false
    private lateinit var cuentaMesa: CuentaMesa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cuentaMesa = CuentaMesa(mesa = 1)

        val etPastelChoclo = findViewById<EditText>(R.id.etPastelChoclo)
        val etCazuela = findViewById<EditText>(R.id.etCazuela)
        val switchPropina = findViewById<Switch>(R.id.switchPropina)
        val tvTotalComida = findViewById<TextView>(R.id.tvTotalComida)
        val tvTotalCompleto = findViewById<TextView>(R.id.tvTotalCompleto)
        val tvTotalPropina = findViewById<TextView>(R.id.tvTotalPropina)
        val tvPrecioPastelChoclo = findViewById<TextView>(R.id.textView3)
        val tvPrecioCazuela = findViewById<TextView>(R.id.textView6)


        val pastelDeChoclo = ItemMenu("Pastel de Choclo", 12000)
        val cazuela = ItemMenu("Cazuela", 10000)


        fun calculateTotalSinPropina() {
            cuentaMesa = CuentaMesa(mesa = 1)

            val pastelCount = etPastelChoclo.text.toString().toIntOrNull() ?: 0
            val cazuelaCount = etCazuela.text.toString().toIntOrNull() ?: 0

            if (pastelCount > 0) {
                cuentaMesa.agregarItem(pastelDeChoclo, pastelCount)
            }
            if (cazuelaCount > 0) {
                cuentaMesa.agregarItem(cazuela, cazuelaCount)
            }

            tvPrecioPastelChoclo.text = "$ ${pastelDeChoclo.precio * pastelCount}.-"
            tvPrecioCazuela.text = "$ ${cazuela.precio * cazuelaCount}.-"

            tvTotalComida.text = "$${cuentaMesa.calcularTotalSinPropina()}"
            tvTotalPropina.text = "$0"
            tvTotalCompleto.text = "$${cuentaMesa.calcularTotalSinPropina()}"
        }


        fun calculateTotalConPropina() {
            tvTotalComida.text = "$${cuentaMesa.calcularTotalSinPropina()}"
            tvTotalPropina.text = "$${cuentaMesa.calcularPropina()}"
            tvTotalCompleto.text = "$${cuentaMesa.calcularTotalConPropina()}"
        }


        etPastelChoclo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calculateTotalSinPropina()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etCazuela.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calculateTotalSinPropina()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                propinaAplicada = true
                calculateTotalConPropina()
            } else {
                propinaAplicada = false
                calculateTotalSinPropina()
            }
        }

        calculateTotalSinPropina()
    }
}



