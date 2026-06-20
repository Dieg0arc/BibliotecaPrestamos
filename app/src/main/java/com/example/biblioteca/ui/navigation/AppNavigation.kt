package com.example.biblioteca.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.biblioteca.ui.screens.ActiveLoansScreen
import com.example.biblioteca.ui.screens.RegisterBookScreen
import com.example.biblioteca.ui.screens.RegisterLoanScreen
import com.example.biblioteca.viewmodel.LoanViewModel

private enum class Screen { ACTIVE_LOANS, REGISTER_BOOK, REGISTER_LOAN }

@Composable
fun AppNavigation() {
    val loanViewModel: LoanViewModel = viewModel()
    var currentScreen by remember { mutableStateOf(Screen.ACTIVE_LOANS) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Screen.entries.forEach { screen ->
                val label = when (screen) {
                    Screen.ACTIVE_LOANS  -> "Préstamos"
                    Screen.REGISTER_BOOK -> "Libros"
                    Screen.REGISTER_LOAN -> "Prestar"
                }
                Button(
                    onClick = { currentScreen = screen },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentScreen == screen)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text(label)
                }
            }
        }
        when (currentScreen) {
            Screen.ACTIVE_LOANS  -> ActiveLoansScreen(loanViewModel = loanViewModel)
            Screen.REGISTER_BOOK -> RegisterBookScreen(loanViewModel = loanViewModel)
            Screen.REGISTER_LOAN -> RegisterLoanScreen(loanViewModel = loanViewModel)
        }
    }
}
