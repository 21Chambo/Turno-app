package com.turnofacil.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.turnofacil.R
import com.turnofacil.adapter.ServiceAdapter
import com.turnofacil.api.RetrofitClient
import com.turnofacil.model.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var rvServices: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvServices = findViewById(R.id.rvServices)
        progressBar = findViewById(R.id.progressMain)
        rvServices.layoutManager = LinearLayoutManager(this)

        loadServices()
    }

    private fun loadServices() {
        progressBar.visibility = View.VISIBLE

        RetrofitClient.apiService.getServices().enqueue(object : Callback<List<Service>> {
            override fun onResponse(call: Call<List<Service>>, response: Response<List<Service>>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    val services = response.body()!!
                    rvServices.adapter = ServiceAdapter(services) { service ->
                        val intent = Intent(this@MainActivity, ServiceDetailActivity::class.java)
                        intent.putExtra("service_id", service.id)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "No se pudieron cargar los servicios", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Service>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
