package com.example.biblioteca.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.biblioteca.data.local.database.DatabaseProvider
import com.example.biblioteca.data.model.Book
import com.example.biblioteca.data.model.Loan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoanViewModel(application: Application) : AndroidViewModel(application) {

    private val db = DatabaseProvider.getDatabase(application)
    private val loanDao = db.loanDao()
    private val bookDao = db.bookDao()

    val activeLoans: StateFlow<List<Loan>> = loanDao.getActiveLoans()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val books: StateFlow<List<Book>> = bookDao.getAllBooks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addBook(title: String, author: String, isbn: String, copies: Int) {
        viewModelScope.launch {
            bookDao.insertBook(Book(title = title, author = author, isbn = isbn, availableCopies = copies))
        }
    }

    fun registerLoan(bookId: Long, studentName: String, studentCode: String, onResult: (Boolean, String) -> Unit) {
        // La validación de 0 copias va en el ViewModel porque es lógica de negocio: la View no debe
        // tomar decisiones de dominio y el DAO solo debe ejecutar operaciones atómicas de base de datos.
        viewModelScope.launch {
            val copies = loanDao.getAvailableCopies(bookId)
            if (copies <= 0) {
                onResult(false, "No hay copias disponibles de este libro")
                return@launch
            }
            loanDao.registerLoan(Loan(bookId = bookId, studentName = studentName, studentCode = studentCode))
            onResult(true, "Préstamo registrado exitosamente")
        }
    }

    fun returnBook(loanId: Long, bookId: Long) {
        viewModelScope.launch {
            loanDao.returnBook(loanId, bookId)
        }
    }
}
