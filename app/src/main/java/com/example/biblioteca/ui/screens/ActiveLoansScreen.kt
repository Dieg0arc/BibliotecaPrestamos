package com.example.biblioteca.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.biblioteca.viewmodel.LoanViewModel

@Composable
fun ActiveLoansScreen(loanViewModel: LoanViewModel = viewModel()) {
    val activeLoans by loanViewModel.activeLoans.collectAsState()
    val books by loanViewModel.books.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(activeLoans) { loan ->
            // Recibe solo los datos puntuales (título y nombre), no las entidades completas.
            val bookTitle = books.find { it.id == loan.bookId }?.title ?: "Libro desconocido"
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = bookTitle, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Estudiante: ${loan.studentName}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { loanViewModel.returnBook(loan.id, loan.bookId) }) {
                        Text("Marcar como devuelto")
                    }
                }
            }
        }
    }
}
