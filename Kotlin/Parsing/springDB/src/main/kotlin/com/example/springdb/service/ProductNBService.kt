package com.example.springdb.service

import com.example.springdb.models.ProductNB
import com.example.springdb.repositories.ProductNBRepository
import lombok.RequiredArgsConstructor
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.stereotype.Service
import java.io.File
import kotlin.math.roundToLong

@Service
@RequiredArgsConstructor
class ProductNBService(val productNBRepository: ProductNBRepository) {

    fun listProductsNB(title: String?): List<ProductNB>? {
        return if (title != null) productNBRepository.findByTitle(title) else productNBRepository.findAll()
    }

    fun getProductNBById(id: Long): ProductNB {
        return productNBRepository.findById(id).orElse(null)
    }

    fun deleteProductNB(id: Long) {
        productNBRepository.deleteById(id)
    }

    fun saveProductNB(productNB: ProductNB) {
        productNBRepository.save(productNB)
    }

    fun parseNB(productNB: ProductNB) {
        var count = 1
        val url = "https://www.eldorado.ru/c/noutbuki/"
        val webDriverMainPage = createWebdriver(url)
        webDriverMainPage.get(url)
        scrollDownToTheEndOfThePage(8, webDriverMainPage)
        val lastPage = getNumberOfLastPage(webDriverMainPage)
        println(1)
        closeWebDriver(webDriverMainPage)
        val list = ArrayList<ProductNB>()
        for (i in 1 until 2/*lastPage*/) {

            var baseUrl = if (i == 1) {
                "https://www.eldorado.ru/c/noutbuki/"
            } else {
                "https://www.eldorado.ru/c/noutbuki/?page=$i"
            }
            val fileWithRawData = File("src\\main\\resources\\text.txt")
            fileWithRawData.writeText("")
            val webDriver = createWebdriver(baseUrl)
            webDriver.get(baseUrl)
            scrollDownToTheEndOfThePage(8, webDriver)
            val textOfThePage = webDriver.pageSource
            fileWithRawData.appendText(textOfThePage)
            println(2)
            closeWebDriver(webDriver)
            val doc: Document = Jsoup.parse(fileWithRawData, "UTF-8", "https://www.eldorado.ru/c/noutbuki/")
            val elementList: Elements = doc.getElementsByAttributeValue("data-dy", "title")
            val mainURL = "https://www.eldorado.ru"
            elementList.forEach {
                println("start 3")
                val detailURL = mainURL + it.attr("href")
                val webDriver = createWebdriver(detailURL)
                webDriver.get(detailURL)
                scrollDownToTheEndOfThePage(8, webDriver)
                val text = webDriver.pageSource // DATA HERE!!!
                val doc2 = Jsoup.parse(text)
                val product = ProductNB()
                Thread.sleep(1000)
                try {
                    product.title = doc2.getElementsByAttributeValue("class", "catalogItemDetailHd").text()
                } catch (e: Exception) {
                    println(e.stackTrace.toString())
                    println("no title")
                }
                try {
                    product.price = doc2.getElementsByAttributeValue("class", "product-box-price__active").first()!!.text()
                } catch (e: Exception) {
                    println(e.stackTrace.toString())
                    println("no price")
                }
                try {
                    product.vendorCode = doc2.getElementsByAttributeValue("itemprop", "sku").text()
                } catch (e: Exception) {
                    println(e.stackTrace.toString())
                    println("no noVendorCode")
                }
                try {
                    product.link = elementList.first()!!.attr("href")
                } catch (e: Exception) {
                    println(e.stackTrace.toString())
                    println("no link")
                }
                try {
                    product.oldPrice =
                        doc2.getElementsByAttributeValue("class", "product-box-price__old-el").first()!!.text()
                } catch (e: Exception) {
                    println(e.stackTrace.toString())
                    println("no oldPrice")
                }
                list.add(product)
                scrollDownToTheEndOfThePage(8, webDriver)
                println("end 3")
                closeWebDriver(webDriver)
            }
        }
        list.forEach { println(it.toString()) }

        for (i in 0 until list.size){
            saveProductNB(list[i])
        }
    }

    fun closeWebDriver(webDriver: ChromeDriver) {
        try {
            webDriver.close()
        } catch (e: Exception) {
            println(e.stackTrace)
        }
    }

    fun getNumberOfLastPage(webDriver: ChromeDriver): Int {
        val text = webDriver.pageSource
        val html: Document = Jsoup.parse(text)
        val results = html.getElementsByAttributeValue("id", "listing-container")
            .select("ul>li")
            .select("li>a")
            .select("a[href]")
            .text()
        val list = results.split(" ").toMutableList()
        return list[list.size - 2].toInt()
    }

    fun createWebdriver(url: String): ChromeDriver {
        System.setProperty("webdriver.chrome.driver", "selenium\\chromedriver.exe")
        val chromeOptions = ChromeOptions()
        chromeOptions.addArguments("--disable-notifications")
        chromeOptions.addArguments("--enable-javascript")
        chromeOptions.addArguments("start-maximized") // open Browser in maximized mode
        chromeOptions.addArguments("--disable-extensions") // disabling extensions
        chromeOptions.addArguments("--disable-gpu") // applicable to windows os only
        val webDriver = ChromeDriver(chromeOptions)
        webDriver.get(url)
        return webDriver
    }

    fun scrollDownToTheEndOfThePage(sec: Int, webDriver: ChromeDriver) {
        val scrollDown: JavascriptExecutor = webDriver
        val b = 3.5
        Thread.sleep(sec.toLong() * 1000)
        scrollDown.executeScript("window.scrollTo(0, document.body.scrollHeight)")
        Thread.sleep(((sec.toDouble() / b.toDouble()) * 1000).roundToLong())
        scrollDown.executeScript("window.scrollTo(0, document.body.scrollHeight)")
        Thread.sleep(((sec.toDouble() / b.toDouble()) * 1000).roundToLong())
        scrollDown.executeScript("window.scrollTo(0, document.body.scrollHeight)")
        Thread.sleep(((sec.toDouble() / b.toDouble()) * 1000).roundToLong())
    }
}