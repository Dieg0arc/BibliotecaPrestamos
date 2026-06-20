package com.example.biblioteca.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.biblioteca.data.local.dao.BookDao
import com.example.biblioteca.data.local.dao.LoanDao
import com.example.biblioteca.data.model.Book
import com.example.biblioteca.data.model.Loan

@Database(entities = [Book::class, Loan::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun loanDao(): LoanDao
}
