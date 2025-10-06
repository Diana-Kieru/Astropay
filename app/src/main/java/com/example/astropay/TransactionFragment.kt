package com.example.astropay

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TransactionFragment : Fragment() {

    private lateinit var btnBack: ImageView
    private lateinit var etSearch: EditText
    private lateinit var dateFilterCard: CardView
    private lateinit var rvTransactions: RecyclerView
    private lateinit var adapter: TransactionAdapter
    
    private var allTransactions: List<Transaction> = emptyList()
    private var filteredTransactions: List<Transaction> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initViews(view)
        setupRecyclerView()
        setupListeners()
        loadTransactions()
    }

    private fun initViews(view: View) {
        btnBack = view.findViewById(R.id.btnBack)
        etSearch = view.findViewById(R.id.etSearch)
        dateFilterCard = view.findViewById(R.id.dateFilterCard)
        rvTransactions = view.findViewById(R.id.rvTransactions)
    }

    private fun setupRecyclerView() {
        adapter = TransactionAdapter(emptyList()) { transaction ->
            onTransactionClick(transaction)
        }
        
        rvTransactions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TransactionFragment.adapter
            // Optional: Add item decoration for spacing
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: android.graphics.Rect,
                    view: android.view.View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    // No extra spacing needed as it's in the item layout
                }
            })
        }
    }

    private fun setupListeners() {
        // Back button
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Search functionality
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterTransactions(s.toString())
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })

        // Date filter
        dateFilterCard.setOnClickListener {
            showDateFilterDialog()
        }
    }

    private fun loadTransactions() {
        allTransactions = TransactionData.getSampleTransactions()
        filteredTransactions = allTransactions
        updateAdapter()
    }

    private fun filterTransactions(query: String) {
        filteredTransactions = if (query.isEmpty()) {
            allTransactions
        } else {
            allTransactions.filter { transaction ->
                transaction.title.contains(query, ignoreCase = true) ||
                transaction.subtitle.contains(query, ignoreCase = true) ||
                transaction.amount.contains(query, ignoreCase = true)
            }
        }
        updateAdapter()
    }

    private fun updateAdapter() {
        // Create new adapter with filtered data
        adapter = TransactionAdapter(filteredTransactions) { transaction ->
            onTransactionClick(transaction)
        }
        rvTransactions.adapter = adapter
    }

    private fun onTransactionClick(transaction: Transaction) {
        // Navigate to transaction details
        val transactionDetail = TransactionDetail(
            recipientName = transaction.title,
            amount = transaction.amount,
            currency = "KES", // Default currency
            dateTime = transaction.date,
            transactionType = transaction.type,
            timeline = listOf(
                TimelineItem(
                    title = "Transaction created",
                    timestamp = transaction.date,
                    status = TimelineStatus.SUCCESS
                ),
                TimelineItem(
                    title = "We are processing your transaction",
                    timestamp = transaction.date,
                    status = TimelineStatus.SUCCESS
                ),
                TimelineItem(
                    title = "Your transaction is on the way",
                    timestamp = transaction.date,
                    status = TimelineStatus.SUCCESS
                )
            ),
            balanceCurrency = "KES",
            balanceAmount = "220.84 KES",
            status = transaction.status
        )
        
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, TransactionDetailFragment.newInstance(transactionDetail))
            .addToBackStack(null)
            .commit()
    }

    private fun showDateFilterDialog() {
        // Implement date filter dialog
        // You can use DatePickerDialog or a custom dialog
    }
}
