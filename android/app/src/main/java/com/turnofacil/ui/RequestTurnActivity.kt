package com.turnofacil.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.turnofacil.R
import com.turnofacil.api.RetrofitClient
import com.turnofacil.model.TurnRequest
import com.turnofacil.model.TurnResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RequestTurnActivity : AppCompatActivity() {

    private var serviceId: Int = 0
    private var serviceQuota: Int = 0
    private lateinit var currentHour: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_turn)

        serviceId = intent.getIntExtra("service_id", 0)
        serviceQuota = intent.getIntExtra("service_quota", 0)
        val serviceName = intent.getStringExtra("service_name") ?: "Servicio"

        val tvRequestService: TextView = findViewById(R.id.tvRequestService)
        val tvCurrentTime: TextView = findViewById(R.id.tvCurrentTime)
        val tvQuotaInfo: TextView = findViewById(R.id.tvQuotaInfo)
        val etName: TextInputEditText = findViewById(R.id.etName)
        val etDocument: TextInputEditText = findViewById(R.id.etDocument)
        val btnSubmitTurn: Button = findViewById(R.id.btnSubmitTurn)
        val progressBar: ProgressBar = findViewById(R.id.progressRequest)

        currentHour = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        tvRequestService.text = "Servicio seleccionado: $serviceName"
        tvCurrentTime.text = "Hora de solicitud: $currentHour"
        tvQuotaInfo.text = if (serviceQuota > 0) {
            "Cupos disponibles: $serviceQuota"
        } else {
            "Sin cupos"
        }

        if (serviceQuota <= 0) {
            btnSubmitTurn.isEnabled = false
            Toast.makeText(this, "Sin cupos disponibles para este servicio", Toast.LENGTH_LONG).show()
        }

        btnSubmitTurn.setOnClickListener {
            val name = etName.text?.toString()?.trim().orEmpty()
            val document = etDocument.text?.toString()?.trim().orEmpty()

            if (name.isEmpty() || document.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            btnSubmitTurn.isEnabled = false

            val request = TurnRequest(
                nombre = name,
                documento = document,
                servicioId = serviceId,
                hora = currentHour
            )

            RetrofitClient.apiService.createTurn(request).enqueue(object : Callback<TurnResponse> {
                override fun onResponse(call: Call<TurnResponse>, response: Response<TurnResponse>) {
                    progressBar.visibility = View.GONE
                    btnSubmitTurn.isEnabled = true

                    if (response.isSuccessful && response.body() != null) {
                        AlertDialog.Builder(this@RequestTurnActivity)
                            .setTitle("Turno solicitado")
                            .setMessage("Tu turno fue registrado correctamente.")
                            .setPositiveButton("Aceptar") { _, _ ->
                                finishAffinity()
                            }
                            .show()
                    } else {
                        Toast.makeText(this@RequestTurnActivity, "No fue posible registrar el turno", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<TurnResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    btnSubmitTurn.isEnabled = true
                    Toast.makeText(this@RequestTurnActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
