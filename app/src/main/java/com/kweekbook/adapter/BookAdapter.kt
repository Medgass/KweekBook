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
    private val onBorrowClick: (Book) -> Unit
) : ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffCallback()) {
    
    inner class BookViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(book: Book) {
            binding.apply {
                textViewTitle.text = book.title
                textViewAuthor.text = book.author
                textViewCategory.text = book.category
                textViewRating.text = "⭐ ${book.rating}"
                
                // Load image using Glide
                Glide.with(imageViewBook.context)
                    .load(book.image)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .centerCrop()
                    .into(imageViewBook)
                
                // Handle status
                when (book.status) {
                    "available" -> {
                        buttonBorrow.text = "Emprunter"
                        buttonBorrow.isEnabled = true
                    }
                    "borrowed" -> {
                        buttonBorrow.text = "Emprunté"
                        buttonBorrow.isEnabled = false
                    }
                    "reserved" -> {
                        buttonBorrow.text = "Réservé"
                        buttonBorrow.isEnabled = false
                    }
                }
                
                root.setOnClickListener { onBookClick(book) }
                buttonBorrow.setOnClickListener { onBorrowClick(book) }
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
