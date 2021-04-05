package com.simran.covid19tracker

import android.app.VoiceInteractor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.leo.simplearcloader.SimpleArcLoader
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import org.json.JSONObject
import java.lang.reflect.Method
import java.util.*

class TrackCountries : AppCompatActivity() {
    lateinit var pieChart: PieChart
    lateinit var arcLoader: SimpleArcLoader
    lateinit var scrollView: ScrollView
    lateinit var txtCasesc2: TextView
    lateinit var txtRecoveredC2: TextView
    lateinit var txtCriticalC2: TextView
    lateinit var txtActiveC2: TextView
    lateinit var txttodayCasesC2: TextView
    lateinit var txttotalDeathsC2: TextView
    lateinit var txttodayDeathsC2: TextView
    lateinit var txtaffectedCountriesC2: TextView
    fun convertIntoInternationalForm(i: Int): String? {
        var a: String? = null
        if (i < 1000) {
            return i.toString()

        } else if (i >= 1000 && i <= 100000) {
            val pass: Double = i.toDouble() / 1000;
            a = pass.toString() + "k"

        } else if (i >= 1000000) {
            val pass: Double = i.toDouble() / 1000;
            a = pass.toString() + "M"

        }
        return a;

    }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_track_countries)
        arcLoader = findViewById(R.id.arcLoader)
        pieChart = findViewById(R.id.pieChart)
        txtCasesc2 = findViewById(R.id.txtCasesc2)
        txtRecoveredC2 = findViewById(R.id.txtRecoveredC2)
        txtCriticalC2 = findViewById(R.id.txtCriticalC2)
        txtActiveC2 = findViewById(R.id.txtActiveC2)
        txttodayCasesC2 = findViewById(R.id.txttodayCasesC2)
        txttotalDeathsC2 = findViewById(R.id.txttotalDeathsC2)
        txttodayDeathsC2 = findViewById(R.id.txttodayDeathsC2)
        txtaffectedCountriesC2 = findViewById(R.id.txtaffectedCountriesC2)
        scrollView = findViewById(R.id.scrollView)
        fetchData()

    }

    private fun fetchData() {
        arcLoader.start()
        val url = "https://corona.lmao.ninja/v2/all/"
        val stringRequest: StringRequest = StringRequest(Request.Method.GET, url,
            {
                //Response listner
                try {
                    val jsonObject = JSONObject(it.toString())


//                    txtCasesc2.setText(jsonObject.getString("cases"))

                    txtCasesc2.setText(
                        convertIntoInternationalForm(
                            jsonObject.getString("cases").toInt()
                        )
                    )
                    txtActiveC2.setText(
                        convertIntoInternationalForm(
                            jsonObject.getString("active").toInt()
                        )
                    )
                    txttodayDeathsC2.setText(
                        convertIntoInternationalForm(
                            jsonObject.getString("deaths").toInt()
                        )
                    )
//                   txtCriticalC2.setText(jsonObject.getString("critical"))

                    txtCriticalC2.setText(
                        convertIntoInternationalForm(
                            jsonObject.getString("critical").toInt()
                        )
                    )
                    txtaffectedCountriesC2.setText(
                        convertIntoInternationalForm(
                            jsonObject.getString(
                                "affectedCountries"
                            ).toInt()
                        )
                    )
                    txtRecoveredC2.setText(
                        convertIntoInternationalForm(
                            jsonObject.getString("recovered").toInt()
                        )
                    )
                    txttodayCasesC2.setText(
                        convertIntoInternationalForm(
                            jsonObject.getString("todayCases").toInt()
                        )
                    )
                    txttotalDeathsC2.setText(
                        convertIntoInternationalForm(
                            jsonObject.getString("deaths").toInt()
                        )
                    )

                    //piechart
                    pieChart.addPieSlice(
                        PieModel(
                            "cases",
                            Integer.parseInt(txtCasesc2.text.toString()).toFloat(),
                            Color.parseColor("#ff9900")
                        )
                    )
                    pieChart.addPieSlice(
                        PieModel(
                            "recovered",
                            Integer.parseInt(txtCasesc2.text.toString()).toFloat(),
                            Color.parseColor("#33cc33")
                        )
                    )
                    pieChart.addPieSlice(
                        PieModel(
                            "deaths",
                            Integer.parseInt(txtCasesc2.text.toString()).toFloat(),
                            Color.parseColor("#ff3333")
                        )
                    )
                    pieChart.addPieSlice(
                        PieModel(
                            "active",
                            Integer.parseInt(txtCasesc2.text.toString()).toFloat(),
                            Color.parseColor("#4da6ff")
                        )
                    )
                    arcLoader.stop()
                    arcLoader.visibility = View.GONE
                    scrollView.visibility = View.VISIBLE
                } catch (e1: Exception) {


                }

            }, {
//                error listener
                arcLoader.visibility = View.GONE
                scrollView.visibility = View.VISIBLE
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            })


        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest);


    }

}




