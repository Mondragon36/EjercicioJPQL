package co.edu.eam.disenosoftware.ejercicioJPA.repositories

import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Borrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
@Transactional
class BorrowRepository {
    @Autowired

    lateinit var em: EntityManager

    fun create(borrow: Borrow) {
        em.persist(borrow)
    }

    fun find (id: Long): Borrow? {
        return em.find(Borrow::class.java, id)
    }

    fun update(borrow: Borrow) {
        em.merge(borrow)
    }

    fun delete(id: Long) {
        val borrow = find(id)

        if (borrow != null) {
            em.remove(borrow)
        }
    }

    fun findBorrowUser(userId: String) : List<Borrow> {
        val query = em.createQuery("SELECT borrow FROM Borrow borrow WHERE borrow.user.identficacion = : userId")
        query.setParameter("userId", userId)
        return query.resultList as List<Borrow>
    }

    fun findBorrowBook(code: String) : List<Borrow> {
        val query = em.createQuery("SELECT borrow FROM Borrow borrow WHERE borrow.book.code = : code")
        query.setParameter("code", code)
        return query.resultList as List<Borrow>
    }
}