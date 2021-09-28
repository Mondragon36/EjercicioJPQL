package co.edu.eam.disenosoftware.ejercicioJPA.repositories

import co.edu.eam.disenosoftware.ejercicioJPA.models.Publisher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager


@SpringBootTest
@Transactional
class TestPublisherRepository {
    @Autowired
    lateinit var publisherRepository: PublisherRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {

    }

    @Test
    fun testCreate() {
        publisherRepository.create(Publisher("1", "Comfenalco"))
        publisherRepository.create(Publisher("2", "Museum"))
        val publisher = entityManager.find(Publisher::class.java, 11)
        Assertions.assertNotNull(publisher)
        Assertions.assertEquals("Museum", publisher.name)
    }

    @Test
    fun testFind() {
        publisherRepository.create(Publisher("1", "Comfenalco"))
        val publisher = publisherRepository.find("1")
        if (publisher != null) {
            Assertions.assertNotNull(publisher)
            Assertions.assertEquals("Comfenalco", publisher?.name)
            println(publisher)
        } else {
            println("Is not found")
        }
    }

    @Test
    fun testUpdate() {
        entityManager.persist(Publisher("1", "Comfenalco"))
        val publisher = entityManager.find(Publisher::class.java, "1")
        publisher.name = "Libros Armenia"
        publisherRepository.update(publisher)
        val publisherToAssert = entityManager.find(Publisher::class.java, "1")
        Assertions.assertEquals("Comfenalco", publisherToAssert.name)
    }

    @Test
    fun testDelete() {
        entityManager.persist(Publisher("1", "Comfenalco"))
        publisherRepository.delete("1")

        val publisher = entityManager.find(Publisher::class.java, "1")
        Assertions.assertNull(publisher)
    }

}