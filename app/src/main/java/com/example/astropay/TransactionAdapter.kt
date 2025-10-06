package com.example.astropay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

data class Transaction(
    val date: String,
    val title: String,
    val subtitle: String,
    val amount: String,
    val type: TransactionType,
    val status: TransactionStatus? = null,
    val showDateHeader: Boolean = false
)

enum class TransactionType : java.io.Serializable {
    MONEY_SENT,
    MONEY_RECEIVED,
    CURRENCY_EXCHANGE,
    PAYMENT_LINK
}

enum class TransactionStatus : java.io.Serializable {
    REJECTED
}

class TransactionAdapter(
    private val transactions: List<Transaction>,
    private val onItemClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDateHeader: TextView = view.findViewById(R.id.tvDateHeader)
        val iconCard: CardView = view.findViewById(R.id.iconCard)
        val ivIcon: ImageView = view.findViewById(R.id.ivIcon)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvSubtitle: TextView = view.findViewById(R.id.tvSubtitle)
        val tvAmount: TextView = view.findViewById(R.id.tvAmount)
        val statusBadge: CardView = view.findViewById(R.id.statusBadge)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)

        fun bind(transaction: Transaction) {
            // Show/hide date header
            if (transaction.showDateHeader) {
                tvDateHeader.visibility = View.VISIBLE
                tvDateHeader.text = transaction.date
            } else {
                tvDateHeader.visibility = View.GONE
            }

            // Set transaction details
            tvTitle.text = transaction.title
            tvSubtitle.text = transaction.subtitle
            tvAmount.text = transaction.amount

            // Set icon based on transaction type
            when (transaction.type) {
                TransactionType.MONEY_SENT -> {
                    ivIcon.setImageResource(R.drawable.ic_arrow_up)
                }
                TransactionType.MONEY_RECEIVED -> {
                    ivIcon.setImageResource(R.drawable.ic_arrow_up)
                    ivIcon.rotation = 180f
                }
                TransactionType.CURRENCY_EXCHANGE -> {
                    ivIcon.setImageResource(R.drawable.ic_currency_exchange)
                }
                TransactionType.PAYMENT_LINK -> {
                    ivIcon.setImageResource(R.drawable.ic_qr_payment)
                }
            }

            // Show status badge if present
            if (transaction.status != null) {
                statusBadge.visibility = View.VISIBLE
                when (transaction.status) {
                    TransactionStatus.REJECTED -> {
                        tvStatus.text = "Rejected"
                        // Add strikethrough to amount
                        tvAmount.paintFlags = tvAmount.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }
            } else {
                statusBadge.visibility = View.GONE
                tvAmount.paintFlags = tvAmount.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            // Set amount color based on positive/negative
            if (transaction.amount.startsWith("-")) {
                tvAmount.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
            } else if (transaction.amount.startsWith("+")) {
                tvAmount.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
            }

            itemView.setOnClickListener { onItemClick(transaction) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount() = transactions.size
}

// Sample data for testing
object TransactionData {
    fun getSampleTransactions(): List<Transaction> {
        return listOf(
            Transaction(
                date = "24 Sept 2025",
                title = "Money Sent",
                subtitle = "M-PESA",
                amount = "-9,347.33 KES",
                type = TransactionType.MONEY_SENT,
                showDateHeader = true
            ),
            Transaction(
                date = "9 Sept 2025",
                title = "Money Sent",
                subtitle = "M-PESA",
                amount = "-9,347.33 KES",
                type = TransactionType.MONEY_SENT,
                status = TransactionStatus.REJECTED,
                showDateHeader = true
            ),
            Transaction(
                date = "9 Sept 2025",
                title = "Currency exchange",
                subtitle = "- USD 11.00",
                amount = "+1,406.44 KES",
                type = TransactionType.CURRENCY_EXCHANGE,
                showDateHeader = false
            ),
            Transaction(
                date = "8 Sept 2025",
                title = "Gabriel Jorgelino Alegre",
                subtitle = "Payment link",
                amount = "+7,940.89 KES",
                type = TransactionType.PAYMENT_LINK,
                showDateHeader = true
            ),
            Transaction(
                date = "17 Aug 2025",
                title = "Mauricio Jose Puello",
                subtitle = "Payment link",
                amount = "+5,234.50 KES",
                type = TransactionType.PAYMENT_LINK,
                showDateHeader = true
            )
        )
    }
}
