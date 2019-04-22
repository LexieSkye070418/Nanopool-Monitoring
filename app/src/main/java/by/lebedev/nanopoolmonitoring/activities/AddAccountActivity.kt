package by.lebedev.nanopoolmonitoring.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import by.lebedev.nanopoolmonitoring.coins.PoolCoins
import kotlinx.android.synthetic.main.add_account_layout.*

class AddAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(by.lebedev.nanopoolmonitoring.R.layout.add_account_layout)

        val button = addAccountButton
        button.setOnClickListener {

        }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PoolCoins.instance.list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = coinSpinner
        spinner.adapter = adapter

        setupSpinner(spinner)
    }

    fun setupSpinner(spinner: Spinner) {

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View,
                position: Int, id: Long
            ) {
                Toast.makeText(baseContext, "Position = $position", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

}