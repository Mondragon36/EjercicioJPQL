package co.edu.eam.disenosoftware.ejercicioJPA.repositories

import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Author
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.AuthorBook
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Book
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

    fun findByAuthor(id: Long): List<AuthorBook> {
        val query = em.createQuery("SELECT authorBook FROM AuthorBook authorBook WHERE authorBook.author.id =: id")
        query.setParameter("id", id)
        return query.resultList as List<AuthorBook>
    }

    fun findByBook(id: String): List<AuthorBook> {
        val query = em.createQuery("SELECT authorBook FROM AuthorBook WHERE authorBook.book.id =: id")
        query.setParameter("id", id)
        return query.resultList as List<AuthorBook>
    }


}