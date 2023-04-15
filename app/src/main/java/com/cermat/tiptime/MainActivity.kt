package com.cermat.tiptime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cermat.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    // Use view binding to access the views
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up a click listener for the calculate button
        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    // Calculate the tip based on the user input
    private fun calculateTip() {
        // Get the cost of service from the edit text
        val costOfService = binding.CostOfService.text.toString().toDoubleOrNull()

        // If the cost is null or zero, display zero tip and exit early
        if (costOfService == null || costOfService == 0.0) {
            displayTip(0.0)
            return
        }

        // Get the tip percentage from the radio buttons
        val tipPercentage = when (binding.TipOptions.checkedRadioButtonId) {
            R.id.OptionTwentyPercent -> 0.20
            R.id.OptionEighteenPercent -> 0.18
            else -> 0.15
        }

        // Calculate the tip amount
        var tip = costOfService * tipPercentage

        // Round up the tip if the switch is checked
        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp) {
            tip = ceil(tip)
        }

        // Format and display the tip amount
        displayTip(tip)
    }

    // Format and display the tip amount in the text view
    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}