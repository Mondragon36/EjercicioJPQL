package co.edu.eam.disenosoftware.ejercicioJPA.service

import co.edu.eam.disenosoftware.ejercicioJPA.exception.BusinessException
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Book
import co.edu.eam.disenosoftware.ejercicioJPA.repositories.AuthorBookRepository
import co.edu.eam.disenosoftware.ejercicioJPA.repositories.BookRepository
import co.edu.eam.disenosoftware.ejercicioJPA.repositories.BorrowRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.EntityNotFoundException

@Service
class BookService {
    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var borrowRepository: BorrowRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createBook(book: Book) {
        val bookById = bookRepository.find(book.code)
        val bookName = bookRepository.listNameBook(book)

        if (bookById != null && bookName != null) {
            throw BusinessException("This book already exists")
        }
        bookRepository.create(book)
    }

    fun editBook(book: Book) {
        bookRepository.find(book.code?:"") ?: throw EntityNotFoundException("This book does not exist")
        bookRepository.update(book)
    }

    fun deliverBook(id_book: String, id_user: String, id_borrow: Long) {
        bookRepository.find(id_book) ?: throw BusinessException("This book does not exists")
        val bookFind = entityManager.find(Book::class.java, id_book)
        val previousAmount = bookFind.cantidad
        val newQuantity = previousAmount + 1
        bookFind.cantidad = newQuantity

        bookRepository.update(bookFind)
        borrowRepository.delete(id_borrow)
    }
}