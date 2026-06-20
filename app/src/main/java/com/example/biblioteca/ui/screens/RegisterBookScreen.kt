package com.example.biblioteca.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
fun RegisterBookScreen(loanViewModel: LoanViewModel) {
    val books by loanViewModel.books.collectAsState()

    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var isbn by remember { mutableStateOf("") }
    var copies by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = author,
            onValueChange = { author = it },
            label = { Text("Autor") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = isbn,
            onValueChange = { isbn = it },
            label = { Text("ISBN") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = copies,
            onValueChange = { copies = it },
            label = { Text("Copias disponibles") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(8.dp))
        }
        Button(
            onClick = {
                val copiesInt = copies.toIntOrNull() ?: 0
                when {
                    title.isBlank() -> errorMessage = "El título no puede estar vacío"
                    copiesInt <= 0 -> errorMessage = "Las copias deben ser mayor que 0"
                    else -> {
                        errorMessage = ""
                        loanViewModel.addBook(title, author, isbn, copiesInt)
                        title = ""; author = ""; isbn = ""; copies = ""
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar libro")
        }
        Spacer(Modifier.height(16.dp))
        Text("Libros registrados", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(books) { book ->
                Text("${book.title} - ${book.availableCopies} copia(s)")
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}
