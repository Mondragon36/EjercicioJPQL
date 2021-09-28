package co.edu.eam.disenosoftware.ejercicioJPA.repositories

import co.edu.eam.disenosoftware.ejercicioJPA.models.Author
import co.edu.eam.disenosoftware.ejercicioJPA.models.AuthorBook
import co.edu.eam.disenosoftware.ejercicioJPA.models.Book
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
@Transactional
class AuthorBookRepository {
    @Autowired

    lateinit var em: EntityManager

    fun create(authorBook: AuthorBook) {
        em.persist(authorBook)
    }

    fun find(id: Long): AuthorBook? {
        return em.find(AuthorBook::class.java, id)
    }

    fun update(authorBook: AuthorBook) {
        em.merge(authorBook)
    }

    fun delete(id: Long) {
        val authorBook = find(id)
        if (authorBook != null) {
            em.remove(authorBook)
        }
    }

    fun findAuhorAuthor(author: Long): List<Book> {
        val query = em.createQuery("SELECT authorBook.author FROM AuthorBook authorBook WHERE authorBook.book.code = : book")
        query.setParameter("author",author)
        return query.resultList as List<Book>
    }

    fun findAuthorBook(book: String): List<Author> {
        val query = em.createQuery("SELECT authorBook.author FROM AuthorBook WHERE authorBook.book.code = :book")
        query.setParameter("book", book)
        return query.resultList as List<Author>
    }


}