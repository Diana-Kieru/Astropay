package com.example.astropay

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

data class TimelineItem(
    val title: String,
    val timestamp: String,
    val status: TimelineStatus,
    val showLine: Boolean = true
) : java.io.Serializable

enum class TimelineStatus : java.io.Serializable {
    SUCCESS,
    FAILED,
    PENDING
}

data class TransactionDetail(
    val recipientName: String,
    val amount: String,
    val currency: String,
    val dateTime: String,
    val transactionType: TransactionType,
    val timeline: List<TimelineItem>,
    val balanceCurrency: String,
    val balanceAmount: String,
    val status: TransactionStatus? = null
) : java.io.Serializable

class TransactionDetailFragment : Fragment() {

    private lateinit var btnBack: ImageView
    private lateinit var tvRecipientName: TextView
    private lateinit var tvAmount: TextView
    private lateinit var tvDateTime: TextView
    private lateinit var timelineContainer: LinearLayout
    private lateinit var btnSeeBreakdown: TextView
    private lateinit var tvBalanceAmount: TextView
    private lateinit var btnViewReceipt: TextView

    private var transactionDetail: TransactionDetail? = null

    companion object {
        private const val ARG_TRANSACTION_DETAIL = "transaction_detail"
        
        fun newInstance(transactionDetail: TransactionDetail): TransactionDetailFragment {
            val fragment = TransactionDetailFragment()
            val args = Bundle()
            args.putSerializable(ARG_TRANSACTION_DETAIL, transactionDetail)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transaction_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initViews(view)
        setupListeners()
        loadTransactionDetail()
    }

    private fun initViews(view: View) {
        btnBack = view.findViewById(R.id.btnBack)
        tvRecipientName = view.findViewById(R.id.tvRecipientName)
        tvAmount = view.findViewById(R.id.tvAmount)
        tvDateTime = view.findViewById(R.id.tvDateTime)
        btnSeeBreakdown = view.findViewById(R.id.btnSeeBreakdown)
        tvBalanceAmount = view.findViewById(R.id.tvBalanceAmount)
        btnViewReceipt = view.findViewById(R.id.btnViewReceipt)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnSeeBreakdown.setOnClickListener {
            showBreakdown()
        }

        btnViewReceipt.setOnClickListener {
            viewReceipt()
        }
    }

    private fun loadTransactionDetail() {
        // Get data from arguments
        transactionDetail = arguments?.getSerializable(ARG_TRANSACTION_DETAIL) as? TransactionDetail
        
        // If no data provided, use sample data for demonstration
        if (transactionDetail == null) {
            transactionDetail = TransactionDetail(
                recipientName = "Natalia Sharova",
                amount = "-1.00",
                currency = "GBP",
                dateTime = "5 Oct 2025, 17:16",
                transactionType = TransactionType.MONEY_SENT,
                timeline = listOf(
                    TimelineItem(
                        title = "Transaction created",
                        timestamp = "05/10/2025, 17:16",
                        status = TimelineStatus.SUCCESS
                    ),
                    TimelineItem(
                        title = "We are processing your transaction",
                        timestamp = "05/10/2025, 17:16",
                        status = TimelineStatus.SUCCESS
                    ),
                    TimelineItem(
                        title = "Your transaction is on the way",
                        timestamp = "05/10/2025, 17:16",
                        status = TimelineStatus.SUCCESS
                    ),
                    TimelineItem(
                        title = "Your transfer failed and the money returned to your wallet",
                        timestamp = "05/10/2025, 17:16",
                        status = TimelineStatus.FAILED,
                        showLine = false
                    )
                ),
                balanceCurrency = "KES",
                balanceAmount = "-177.02 KES",
                status = TransactionStatus.REJECTED
            )
        }

        displayTransactionDetail()
    }

    private fun displayTransactionDetail() {
        transactionDetail?.let { detail ->
            // Set basic info
            tvRecipientName.text = detail.recipientName
            tvAmount.text = "${detail.amount} ${detail.currency}"
            tvDateTime.text = detail.dateTime
            tvBalanceAmount.text = detail.balanceAmount

            // Apply strikethrough if transaction failed
            if (detail.status == TransactionStatus.REJECTED) {
                tvAmount.paintFlags = tvAmount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }

            // Transaction icon is now handled in the layout

            // Build timeline dynamically (since we can't use includes programmatically)
            // In production, you'd use a RecyclerView for timeline
            // For now, the timeline items are hardcoded in the layout
        }
    }

    private fun viewReceipt() {
        // Implement view receipt logic
        android.widget.Toast.makeText(
            requireContext(),
            "Viewing receipt...",
            android.widget.Toast.LENGTH_SHORT
        ).show()
        
        // In real app:
        // - Navigate to receipt screen
        // - Show PDF or receipt details
        // - Allow sharing or printing
    }

    private fun showBreakdown() {
        // Show detailed breakdown of transaction
        android.widget.Toast.makeText(
            requireContext(),
            "Show breakdown",
            android.widget.Toast.LENGTH_SHORT
        ).show()
        
        // In real app:
        // - Navigate to breakdown screen
        // - Show bottom sheet with details
        // - Display fees, exchange rates, etc.
    }
}

// Helper class for building timeline programmatically (if needed)
object TimelineBuilder {
    fun createTimelineItem(
        context: android.content.Context,
        item: TimelineItem
    ): View {
        val view = android.view.LayoutInflater.from(context)
            .inflate(R.layout.item_timeline, null, false)
        
        val indicator = view.findViewById<CardView>(R.id.timelineIndicator)
        val icon = view.findViewById<ImageView>(R.id.ivTimelineIcon)
        val line = view.findViewById<View>(R.id.timelineLine)
        val title = view.findViewById<TextView>(R.id.tvTimelineTitle)
        val time = view.findViewById<TextView>(R.id.tvTimelineTime)
        
        title.text = item.title
        time.text = item.timestamp
        
        when (item.status) {
            TimelineStatus.SUCCESS -> {
                indicator.setCardBackgroundColor(
                    ContextCompat.getColor(context, android.R.color.holo_green_light)
                )
                icon.setImageResource(R.drawable.ic_check)
                line.setBackgroundColor(
                    ContextCompat.getColor(context, android.R.color.holo_green_light)
                )
            }
            TimelineStatus.FAILED -> {
                indicator.setCardBackgroundColor(
                    ContextCompat.getColor(context, android.R.color.holo_red_light)
                )
                icon.setImageResource(R.drawable.ic_close)
                line.visibility = View.GONE
            }
            TimelineStatus.PENDING -> {
                indicator.setCardBackgroundColor(
                    ContextCompat.getColor(context, android.R.color.darker_gray)
                )
                icon.setImageResource(R.drawable.ic_check)
                line.setBackgroundColor(
                    ContextCompat.getColor(context, android.R.color.darker_gray)
                )
            }
        }
        
        if (!item.showLine) {
            line.visibility = View.GONE
        }
        
        return view
    }
}
