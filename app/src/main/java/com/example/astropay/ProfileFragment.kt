package com.example.astropay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Set up click listeners for navigation
        setupClickListeners(view)
    }
    
    private fun setupClickListeners(view: View) {
        // Back button - navigate back to Dashboard
        view.findViewById<View>(R.id.btnBack)?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        
        // QR Code button - could show QR code or navigate to QR screen
        view.findViewById<View>(R.id.btnQrCode)?.setOnClickListener {
            // TODO: Implement QR code functionality
        }
        
        // Camera button - could open camera for profile photo
        view.findViewById<View>(R.id.btnCamera)?.setOnClickListener {
            // TODO: Implement camera functionality
        }
        
        // Copy User ID button
        view.findViewById<View>(R.id.btnCopyUserId)?.setOnClickListener {
            // TODO: Implement copy to clipboard functionality
        }
        
        // Menu items - could navigate to respective screens
        view.findViewById<View>(R.id.btnHelpCenter)?.setOnClickListener {
            // TODO: Navigate to help center
        }
        
        view.findViewById<View>(R.id.btnPersonalData)?.setOnClickListener {
            // TODO: Navigate to personal data screen
        }
        
        view.findViewById<View>(R.id.btnSecurity)?.setOnClickListener {
            // TODO: Navigate to security screen
        }
        
        view.findViewById<View>(R.id.btnSettings)?.setOnClickListener {
            // TODO: Navigate to settings screen
        }
    }
}


