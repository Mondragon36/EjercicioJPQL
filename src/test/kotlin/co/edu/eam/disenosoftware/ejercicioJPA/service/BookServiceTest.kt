package co.edu.eam.disenosoftware.ejercicioJPA.service

import co.edu.eam.disenosoftware.ejercicioJPA.exception.BusinessException
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Book
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Publisher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class BookServiceTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var bookService: BookService

    @Test
    fun createUserHappyPathTest() {
        val publisher = Publisher("21", "Planeta")
        entityManager.persist(publisher)
        val book = Book("001", "A1", 10,"Amigo imaginario", publisher)

        val bookToAssert = entityManager.find(Book::class.java, "001")
        Assertions.assertNotNull(bookToAssert)
        Assertions.assertEquals("Amigo imaginario", bookToAssert.nombre_libro)
    }

    @Test
    fun createUserAlreadyExistsIdandNameTest() {
        val publisher = Publisher("21", "Planeta")
        entityManager.persist(publisher)
        val book = Book("001", "A1", 10,"Amigo imaginario", publisher)

        try {
            bookService.createBook(Book("001", "A1", 10,"Amigo imaginario", publisher))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This book already exists", e.message)
        }
    }

    @Test
    fun entregarLibroTestHappyPath() {
        val publisher = Publisher("21", "Planeta")
        entityManager.persist(publisher)
        val book = Book("001", "A1", 10,"Amigo imaginario", publisher)

        bookService.deliverBook("001","1",21)

        val bookAssert = entityManager.find(Book::class.java, "001")
        Assertions.assertEquals(11, bookAssert.cantidad)
    }

    @Test
    fun entregarLibroNotExists() {
        val publisher = Publisher("21", "Planeta")
        entityManager.persist(publisher)

        val exception = Assertions.assertThrows(BusinessException::class.java, { bookService.deliverBook("001","1",21) }
        )
        Assertions.assertEquals("This book does not exists", exception.message)
    }
}