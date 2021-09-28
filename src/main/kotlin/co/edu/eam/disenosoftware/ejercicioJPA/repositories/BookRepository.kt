package co.edu.eam.disenosoftware.ejercicioJPA.repositories

import co.edu.eam.disenosoftware.ejercicioJPA.models.Book
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

    fun update(book: Book) {
        em.merge(book)
    }

    fun delete (code: String) {
        val book = find(code)
        if (book != null) {
            em.remove(book)
        }
    }

    fun findBookPublisher(code: String): List<Book> {
        val query = em.createQuery("SELECT book FROM Book book WHERE book.publisher.code = : code")
        query.setParameter("code", code)
        return query.resultList as List<Book>
    }
}