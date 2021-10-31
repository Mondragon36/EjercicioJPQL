package co.edu.eam.disenosoftware.ejercicioJPA.repositories

import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Author
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class TestAuthorRepository {
    @Autowired
    lateinit var authorRepository: AuthorRepository
    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {

    }

    @Test
    fun testCreate() {
        authorRepository.create(Author(10, "Cardona", "Frank"))
        authorRepository.create(Author(11, "Gutierrez", "Valentina"))
        val author = entityManager.find(Author::class.java, 11)
        Assertions.assertNotNull(author)
        Assertions.assertEquals("Gutierrez",author.lastName)
    }

    @Test
    fun testFind() {
        entityManager.persist(Author(10, "Cardona", "Frank"))
        val author = authorRepository.find(10)
        if (author != null) {
            Assertions.assertNotNull(author)
            Assertions.assertEquals("Frank", author?.name)
        }else {
            println("Is not found")
        }
    }

    @Test
    fun testUpdate() {
        entityManager.persist(Author(10, "Cardona", "Frank"))
        val author = entityManager.find(Author::class.java, 10)
        author.name = "Stiven"
        authorRepository.update(author)
        val authorToAssert = entityManager.find(Author::class.java, 10)
        Assertions.assertEquals("Stiven", authorToAssert.name)
    }

    @Test
    fun testDelete() {
        entityManager.persist(Author(10, "Cardona", "Frank"))
        authorRepository.delete(10)

        val author = entityManager.find(Author::class.java, 10)
        Assertions.assertNull(author)
    }

}