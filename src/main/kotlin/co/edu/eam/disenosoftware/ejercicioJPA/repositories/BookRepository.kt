package co.edu.eam.disenosoftware.ejercicioJPA.repositories

import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Book
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Borrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager


@Component
@Transactional
class BookRepository {
    @Autowired

    lateinit var em: EntityManager

    fun create (book: Book) {
        em.persist(book)
    }

    fun find(code: String): Book? {
        return em.find(Book::class.java,code)
    }

    fun listNameBook(book: Book): List<String>? {
        return listOf(book.nombre_libro)
    }

    fun update(book: Book) {
        em.merge(book)
    }

    fun delete (code: String) {
        val book = find(code)
        if (book != null) {
            em.remove(book)
        }
    }

    fun findByUser(id: String): List<Borrow> {
        val query = em.createQuery("SELECT borrow FROM Borrow borrow WHERE borrow.user.identification =: identification")
        query.setParameter("identification", id)
        return query.resultList as List<Borrow>
    }

    fun findBookPublisher(code: String): List<Book> {
        val query = em.createQuery("SELECT book FROM Book book WHERE book.publisher.code =: code")
        query.setParameter("code", code)
        return query.resultList as List<Book>
    }
}