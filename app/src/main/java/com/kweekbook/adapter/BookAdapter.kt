package com.kweekbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kweekbook.R
import com.kweekbook.databinding.ItemBookBinding
import com.kweekbook.model.Book

class BookAdapter(
    private val onBookClick: (Book) -> Unit,
    private val onBorrowClick: (Book) -> Unit,
    private val onReserveClick: (Book) -> Unit = {}
) : ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffCallback()) {
    
    inner class BookViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(book: Book) {
            binding.apply {
                textViewTitle.text = book.title
                textViewAuthor.text = book.author
                textViewCategory.text = book.category
                ratingBarBook.rating = book.rating.toFloat()
                
                // Load image using Glide
                Glide.with(imageViewBook.context)
                    .load(book.image)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .centerCrop()
                    .into(imageViewBook)
                
                // Configure buttons colors
                buttonBorrow.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                    androidx.core.content.ContextCompat.getColor(binding.root.context, R.color.success)
                ))
                buttonReserve.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                    androidx.core.content.ContextCompat.getColor(binding.root.context, R.color.warning)
                ))
                
                // Handle status
                val ctx = binding.root.context
                when (book.status) {
                    "available" -> {
                        buttonBorrow.text = ctx.getString(R.string.button_borrow)
                        buttonBorrow.isEnabled = true
                        buttonReserve.text = ctx.getString(R.string.button_reserve)
                        buttonReserve.isEnabled = true
                    }
                    "borrowed" -> {
                        buttonBorrow.text = ctx.getString(R.string.button_borrowed)
                        buttonBorrow.isEnabled = false
                        buttonReserve.text = ctx.getString(R.string.button_reserve)
                        buttonReserve.isEnabled = true
                    }
                    "reserved" -> {
                        buttonReserve.text = ctx.getString(R.string.button_reserved)
                        buttonReserve.isEnabled = false
                        buttonBorrow.text = ctx.getString(R.string.button_borrow)
                        buttonBorrow.isEnabled = true
                    }
                    else -> {
                        buttonBorrow.text = ctx.getString(R.string.button_borrow)
                        buttonBorrow.isEnabled = true
                        buttonReserve.text = ctx.getString(R.string.button_reserve)
                        buttonReserve.isEnabled = true
                    }
                }

                // Accessibility & visual states
                buttonBorrow.alpha = if (buttonBorrow.isEnabled) 1f else 0.5f
                buttonReserve.alpha = if (buttonReserve.isEnabled) 1f else 0.5f
                buttonBorrow.contentDescription = buttonBorrow.text
                buttonReserve.contentDescription = buttonReserve.text

                root.setOnClickListener { onBookClick(book) }
                buttonBorrow.setOnClickListener { onBorrowClick(book) }
                buttonReserve.setOnClickListener { onReserveClick(book) }
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Book, newItem: Book) = oldItem == newItem
    }
}
