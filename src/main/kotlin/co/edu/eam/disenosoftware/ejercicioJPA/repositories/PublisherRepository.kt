package co.edu.eam.disenosoftware.ejercicioJPA.repositories

import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager


@Component
@Transactional
class PublisherRepository {
    @Autowired

    lateinit var em: EntityManager

    fun create(publisher: Publisher) {
        em.persist(publisher)
    }

    fun find(code: String): Publisher? {
        return em.find(Publisher::class.java, code)
    }

    fun update(publisher: Publisher) {
        em.merge(publisher)
    }

    fun delete (id: String) {
        val publisher = find(id)

        if (publisher != null) {
            em.remove(publisher)
        }
    }
}