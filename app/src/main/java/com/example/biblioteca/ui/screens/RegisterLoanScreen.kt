package com.example.biblioteca.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.biblioteca.viewmodel.LoanViewModel

@Composable
fun RegisterLoanScreen(loanViewModel: LoanViewModel) {
    val books by loanViewModel.books.collectAsState()

    var selectedBookId by remember { mutableStateOf<Long?>(null) }
    var studentName by remember { mutableStateOf("") }
    var studentCode by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Selecciona un libro", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(books) { book ->
                val isSelected = book.id == selectedBookId
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .clickable { selectedBookId = book.id },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = book.title, style = MaterialTheme.typography.bodyLarge)
                        Text(
                            text = "Copias disponibles: ${book.availableCopies}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = studentName,
            onValueChange = { studentName = it },
            label = { Text("Nombre del estudiante") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = studentCode,
            onValueChange = { studentCode = it },
            label = { Text("Código del estudiante") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        if (resultMessage.isNotEmpty()) {
            Text(text = resultMessage)
            Spacer(Modifier.height(8.dp))
        }
        Button(
            onClick = {
                val bookId = selectedBookId
                when {
                    bookId == null -> resultMessage = "Selecciona un libro primero"
                    studentName.isBlank() -> resultMessage = "El nombre no puede estar vacío"
                    else -> loanViewModel.registerLoan(bookId, studentName, studentCode) { ok, msg ->
                        resultMessage = msg
                        if (ok) {
                            studentName = ""; studentCode = ""; selectedBookId = null
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar préstamo")
        }
    }
}
