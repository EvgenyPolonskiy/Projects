package com.example.springdb.controller

import com.example.springdb.models.ProductNB
import com.example.springdb.service.ProductNBService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequiredArgsConstructor
class ProductNBController(val productNBService: ProductNBService) {

    @GetMapping("/")
    fun productsNB(@RequestParam(name = "title", required = false) title: String?, model: Model): String? {
        model.addAttribute("productsNB", productNBService.listProductsNB(title))
        return "products"
    }

    @GetMapping("/productNB/{id}")
    fun productNBInfo(@PathVariable id: Long, model: Model): String {
        model.addAttribute("productNB", productNBService.getProductNBById(id))
        return "product-info"
    }

    @PostMapping("/product/delete/{id}")
    fun deleteProductNB(@PathVariable id: Long): String {
        productNBService.deleteProductNB(id)
        return "redirect:/"
    }

    @PostMapping("/productNB/create")
    fun createProductNB(productNB: ProductNB): String {
        productNBService.saveProductNB(productNB)
        return "redirect:/"
    }

    @PostMapping("/productNB/parse")
    fun parseSite(productNB: ProductNB) : String{
        productNBService.parseNB(productNB)
        return "redirect:/"
    }
}

