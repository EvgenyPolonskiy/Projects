package com.example.springdb.repositories

import com.example.springdb.models.ProductNB
import org.springframework.data.jpa.repository.JpaRepository

interface ProductNBRepository : JpaRepository<ProductNB, Long> {
    fun findByTitle(title: String): List<ProductNB>
}