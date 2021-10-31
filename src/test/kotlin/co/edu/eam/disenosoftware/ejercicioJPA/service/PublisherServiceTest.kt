package co.edu.eam.disenosoftware.ejercicioJPA.service

import co.edu.eam.disenosoftware.ejercicioJPA.exception.BusinessException
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Publisher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class PublisherServiceTest {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var publisherService: PublisherService

    @Test
    fun createUserHappyPathTest(){
        publisherService.createPublisher(Publisher("1", "Comfenalco"))

        val publisherToAssert = entityManager.find(Publisher::class.java, "1")
        Assertions.assertNotNull(publisherToAssert)
        Assertions.assertEquals("Comfenalco", publisherToAssert.name)
    }

    @Test
    fun createUserAlreadyExistsTest(){
        //Prereqquisitos
        entityManager.persist(Publisher("1", "Comfenalco"))

        try {
            publisherService.createPublisher(Publisher("1", "Comfenalco"))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This editorial already exists", e.message)
        }
    }

}