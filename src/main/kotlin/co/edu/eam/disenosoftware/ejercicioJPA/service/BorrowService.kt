package co.edu.eam.disenosoftware.ejercicioJPA.service

import co.edu.eam.disenosoftware.ejercicioJPA.exception.BusinessException
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Borrow
import co.edu.eam.disenosoftware.ejercicioJPA.repositories.BookRepository
import co.edu.eam.disenosoftware.ejercicioJPA.repositories.BorrowRepository
import co.edu.eam.disenosoftware.ejercicioJPA.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class BorrowService {
    @Autowired
    lateinit var borrowRepository: BorrowRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun listBorrowByUser(id: String): List<Borrow> {
        val user = userRepository.find(id) ?: throw BusinessException("The user does not exist")
        return borrowRepository.findBorrowUser(id)
    }

    fun findByUser(id: String) = borrowRepository.findBorrowUser(id)

    fun findByBook(id: String) = borrowRepository.findBorrowBook(id)

    fun lendBook(borrow: Borrow, userId: String, bookId: String) {
        val user = userRepository.find(userId) ?: throw BusinessException("The user does not exist")

        val book = bookRepository.find(bookId) ?: throw BusinessException("The book does not exit")
        val cantBook = book.cantidad

        if (cantBook <= 0) {
            throw BusinessException("There are no books")
        }

        val borrows = borrowRepository.findBorrowUser(userId)

        if (borrows.size > 5 ) {
            throw BusinessException("Only 5 loans per person")
        }

        val newQuantity = cantBook - 1
        book.cantidad = newQuantity

        borrowRepository.create(borrow)
    }



}