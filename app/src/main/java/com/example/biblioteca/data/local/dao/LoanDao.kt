package com.example.biblioteca.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.biblioteca.data.model.Loan
import kotlinx.coroutines.flow.Flow

@Dao
interface LoanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoan(loan: Loan)

    @Query("UPDATE loans SET returned = 1 WHERE id = :loanId")
    suspend fun markReturned(loanId: Long)

    @Query("UPDATE books SET availableCopies = availableCopies + 1 WHERE id = :bookId")
    suspend fun incrementCopies(bookId: Long)

    @Query("UPDATE books SET availableCopies = availableCopies - 1 WHERE id = :bookId")
    suspend fun decrementCopies(bookId: Long)

    @Query("SELECT availableCopies FROM books WHERE id = :bookId")
    suspend fun getAvailableCopies(bookId: Long): Int

    @Query("SELECT * FROM loans WHERE returned = 0")
    fun getActiveLoans(): Flow<List<Loan>>

    @Transaction
    suspend fun returnBook(loanId: Long, bookId: Long) {
        // Ambos pasos van juntos en una transacción para que la base no quede a medias si algo falla.
        markReturned(loanId)
        incrementCopies(bookId)
    }

    @Transaction
    suspend fun registerLoan(loan: Loan) {
        insertLoan(loan)
        decrementCopies(loan.bookId)
    }
}
