package com.xsis.android.batch217.ui.leave_request

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.xsis.android.batch217.R
import java.util.ArrayList

class LeaveRequestAddActivity : AppCompatActivity() {
    var spProvince: SmartMaterialSpinner<String>? = null
    var provinceList: MutableList<String>? = null

    val context=this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_request_add)

        initItemList()
    }

    fun initSpinner() {
        spProvince = findViewById(R.id.spinnerLeaveType)
        spProvince!!.item = provinceList

        setOnItemSelectedListener(spProvince!!)
        setOnSpinnerEventListener(spProvince!!)
    }

    fun initItemList(){
        provinceList = ArrayList()

        provinceList!!.add("Banteay Meanchey")
        provinceList!!.add("Battambang")
        provinceList!!.add("Kampong Cham")
        provinceList!!.add("Kampong Chhnang")
        provinceList!!.add("Kampong Speu")
        provinceList!!.add("Kampong Thom")
        provinceList!!.add("Kampot")
        provinceList!!.add("Kandal")
        provinceList!!.add("Kep")
        provinceList!!.add("Koh Kong")
        provinceList!!.add("Kratie")
        provinceList!!.add("Mondulkiri")
        provinceList!!.add("Oddar Meanchey")
        provinceList!!.add("Pailin")
        provinceList!!.add("Phnom Penh")
        provinceList!!.add("Preah Vihear")
        provinceList!!.add("Prey Veng")
        provinceList!!.add("Pursat")
        provinceList!!.add("Ratanakiri")
        provinceList!!.add("Siem Reap")
        provinceList!!.add("Sihanoukville")
        provinceList!!.add("Stung Treng")
        provinceList!!.add("Svay Rieng")
        provinceList!!.add("Takeo")
        provinceList!!.add("Tbong Khmum")
        provinceList!!.add("The best Android spinner library for your android application with more customization")
        initSpinner()
    }

    private fun setOnItemSelectedListener(vararg spinners: SmartMaterialSpinner<*>) {
        for (spinner in spinners) {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                    spinner.errorText = "You are selecting on spinner item -> \"" + spinner.item[position] + "\" . You can show it both in XML and programmatically and you can display as single line or multiple lines"
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {
                    spinner.errorText = "On Nothing Selected"
                }
            }
        }
    }

    private fun setOnSpinnerEventListener(vararg spinners: SmartMaterialSpinner<*>) {
        for (spinner in spinners) {
            spinner.setOnSpinnerEventListener(object : SmartMaterialSpinner.OnSpinnerEventListener {
                override fun onSpinnerOpened(spinner: SmartMaterialSpinner<*>) {
                    Log.i("SpinnerEventListener", "onSpinnerOpened: ")
                }

                override fun onSpinnerClosed(spinner: SmartMaterialSpinner<*>) {
                    Log.i("SpinnerEventListener", "onSpinnerClosed: ")
                }
            })
        }
    }

   /* private fun setOnEmptySpinnerClickListener(vararg spinners: SmartMaterialSpinner<*>) {
        for (spinner in spinners) {
            spinner.setOnEmptySpinnerClickListener { Toast.makeText(context, "Empty Spinner Clicked", Toast.LENGTH_SHORT).show() }
        }
    }*/

}
