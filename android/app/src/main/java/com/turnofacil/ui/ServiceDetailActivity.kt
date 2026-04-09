package com.turnofacil.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.turnofacil.R
import com.turnofacil.api.RetrofitClient
import com.turnofacil.model.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceDetailActivity : AppCompatActivity() {

    private var serviceId: Int = 0
    private var currentService: Service? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)

        serviceId = intent.getIntExtra("service_id", 0)
        val btnRequestTurn: Button = findViewById(R.id.btnRequestTurn)

        btnRequestTurn.setOnClickListener {
            currentService?.let { service ->
                val intent = Intent(this, RequestTurnActivity::class.java)
                intent.putExtra("service_id", service.id)
                intent.putExtra("service_name", service.nombre)
                intent.putExtra("service_quota", service.cuposDisponibles)
                startActivity(intent)
            }
        }

        loadServiceDetail()
    }

    private fun loadServiceDetail() {
        val progressBar: ProgressBar = findViewById(R.id.progressDetail)
        val tvName: TextView = findViewById(R.id.tvDetailName)
        val tvDescription: TextView = findViewById(R.id.tvDetailDescription)
        val tvQuota: TextView = findViewById(R.id.tvDetailQuota)
        val tvSchedule: TextView = findViewById(R.id.tvDetailSchedule)
        val tvStatus: TextView = findViewById(R.id.tvStatus)
        val btnRequestTurn: Button = findViewById(R.id.btnRequestTurn)

        progressBar.visibility = View.VISIBLE

        RetrofitClient.apiService.getServiceDetail(serviceId).enqueue(object : Callback<Service> {
            override fun onResponse(call: Call<Service>, response: Response<Service>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    val service = response.body()!!
                    currentService = service
                    tvName.text = service.nombre
                    tvDescription.text = "Descripción: ${service.descripcion}"
                    tvQuota.text = "Cupos: ${service.cuposDisponibles}"
                    tvSchedule.text = "Horario: ${service.horario}"

                    if (service.cuposDisponibles <= 0) {
                        tvStatus.text = "Estado: Sin cupos"
                        tvStatus.setTextColor(Color.RED)
                        btnRequestTurn.isEnabled = false
                    } else {
                        tvStatus.text = "Estado: Disponible"
                        tvStatus.setTextColor(resources.getColor(R.color.green_ok, theme))
                        btnRequestTurn.isEnabled = true
                    }
                } else {
                    Toast.makeText(this@ServiceDetailActivity, "Servicio no encontrado", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Service>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@ServiceDetailActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
