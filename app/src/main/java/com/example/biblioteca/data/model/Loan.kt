package com.example.biblioteca.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "loans",
    foreignKeys = [ForeignKey(
        entity = Book::class,
        parentColumns = ["id"],
        childColumns = ["bookId"],
        onDelete = ForeignKey.RESTRICT
    )],
    indices = [Index(value = ["bookId"])]
)
data class Loan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bookId: Long,
    val studentName: String,
    val studentCode: String,
    val returned: Boolean = false
)
