package co.edu.eam.disenosoftware.ejercicioJPA.service

import co.edu.eam.disenosoftware.ejercicioJPA.exception.BusinessException
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Author
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class AuthorServiceTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var authorService: AuthorService

    @Test
    fun createAutorHappyPathTest(){
        authorService.createAuthor(Author( 10,"Cardona", "Frank"))

        val authorToAssert = entityManager.find(Author::class.java, 10)
        Assertions.assertNotNull(authorToAssert)
        Assertions.assertEquals("Frank", authorToAssert.name)
        Assertions.assertEquals("Cardona", authorToAssert.lastName)
    }

    @Test
    fun createAutorAlreadyExistsTest(){
        //Prereqquisitos
        entityManager.persist(Author(10, "Cardona", "Frank"))

        try {
            authorService.createAuthor(Author(10, "Cardona", "Frank"))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This autor already exists", e.message)
        }
    }
}