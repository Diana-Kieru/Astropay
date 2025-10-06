package com.example.astropay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {
    private lateinit var pinEditText: TextInputEditText
    private lateinit var pinInputLayout: TextInputLayout
    private lateinit var btnContinue: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pinEditText = view.findViewById(R.id.etPinInput)
        pinInputLayout = view.findViewById(R.id.tilPinInput)
        btnContinue = view.findViewById(R.id.btnContinue)

        // Set up number pad
        for (i in 0..9) {
            view.findViewById<TextView>(resources.getIdentifier("btn$i", "id", requireContext().packageName))
                ?.setOnClickListener { appendNumber(i.toString()) }
        }

        // Set up delete button
        view.findViewById<ImageView>(R.id.btnBackspace)?.setOnClickListener {
            val text = pinEditText.text.toString()
            if (text.isNotEmpty()) {
                pinEditText.setText(text.substring(0, text.length - 1))
            }
        }

        // Set up biometric button
        view.findViewById<ImageView>(R.id.btnBiometric)?.setOnClickListener {
            // TODO: Implement biometric authentication
        }

        // Watch for PIN changes
        pinEditText.addTextChangedListener { text ->
            btnContinue.isEnabled = text?.length == 4
            btnContinue.setBackgroundColor(
                resources.getColor(
                    if (text?.length == 4) R.color.teal_text else R.color.button_disabled,
                    null
                )
            )
        }

        // Set up continue button
        btnContinue.setOnClickListener {
            if (pinEditText.text?.length == 4) {
                // Navigate to Dashboard
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, DashboardFragment())
                    .commit()
            }
        }
    }

    private fun appendNumber(number: String) {
        val currentText = pinEditText.text.toString()
        if (currentText.length < 4) {
            pinEditText.append(number)
        }
    }
}
