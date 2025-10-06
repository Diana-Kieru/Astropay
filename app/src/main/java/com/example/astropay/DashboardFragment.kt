package com.example.astropay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class DashboardFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Set up click listeners for navigation
        setupClickListeners(view)
    }
    
    private fun setupClickListeners(view: View) {
        // Profile circle click - navigate to Profile
        view.findViewById<View>(R.id.profileCircle)?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, ProfileFragment())
                .addToBackStack(null)
                .commit()
        }
        
        // See all transactions - navigate to Transaction list
        view.findViewById<View>(R.id.btnSeeAll)?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, TransactionFragment())
                .addToBackStack(null)
                .commit()
        }
        
        // Bottom navigation - Home (already on dashboard, so no action needed)
        // Pay button - could navigate to payment screen
        // Contacts button - could navigate to contacts screen
    }
}


