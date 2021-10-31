package co.edu.eam.disenosoftware.ejercicioJPA.controllers

import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Book
import co.edu.eam.disenosoftware.ejercicioJPA.models.requests.BookRequet
import co.edu.eam.disenosoftware.ejercicioJPA.service.BookService
import co.edu.eam.disenosoftware.ejercicioJPA.service.BorrowService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController {
    @Autowired
    lateinit var bookService: BookService

    @Autowired
    lateinit var borrowService: BorrowService

    @PostMapping
    fun createBook(@RequestBody book: Book) {
        bookService.createBook(book)
    }

    @PutMapping("/{code}")
    fun editBook(@PathVariable code: String, @RequestBody book: Book) {
        book.code = code
        bookService.editBook(book)
    }

    @DeleteMapping
    fun deliverBook(@RequestBody bookRequest: BookRequet) {
        bookService.deliverBook(bookRequest.idBook, bookRequest.idUser,bookRequest.idBorrow)
    }

    @GetMapping("/{id}/borrows")
    fun getUsersHasBook(@PathVariable("code") idBook:String) = borrowService.findByBook(idBook)
}