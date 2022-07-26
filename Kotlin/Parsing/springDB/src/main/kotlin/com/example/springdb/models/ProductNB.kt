package com.example.springdb.models

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productsNB")
data class ProductNB(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "title", columnDefinition = "text")
    var title: String = "",

    @Column(name = "price")
    var price: String = "",

    @Column(name = "oldPrice")
    var oldPrice: String = "",

    @Column(name = "link")
    var link: String = "",

    @Column(name = "vendorCode")
    var vendorCode: String = ""
)