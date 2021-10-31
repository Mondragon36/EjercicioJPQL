package co.edu.eam.disenosoftware.ejercicioJPA.repositories

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
class TestBookRepository {
    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {

    }

    @Test
    fun testCreate() {
        val publisher = Publisher("21", "Planeta")
        bookRepository.create(Book("001", "A1", 10,"Amigo imaginario", publisher))
        val book = entityManager.find(Book::class.java, "001")
        println(book)
        Assertions.assertNotNull(book)
        Assertions.assertEquals("Amigo imaginario", book.nombre_libro)
        Assertions.assertEquals("Planeta", book.publisher.name)
    }

    @Test
    fun testFind() {
        val publisher = Publisher("21", "Planeta")
        entityManager.persist(Book("001", "A1", 10,"Amigo imaginario", publisher))
        val book = bookRepository.find("001")
        if (book != null) {
            Assertions.assertNotNull(book)
            Assertions.assertEquals("Amigo imaginario", book?.nombre_libro)
            Assertions.assertEquals("Planeta", book?.publisher.name)
            print(book)
        } else {
            println("Is not found")
        }
    }

    @Test
    fun testUpdate() {
        val publisher = Publisher("21", "Planeta")
        entityManager.persist(Book("001", "A1", 10,"Amigo imaginario", publisher))

        val book = entityManager.find(Book::class.java, "001")
        println(book)
        book.nombre_libro = "Ciudad de papel"
        book.publisher.name = "Scribe"
        bookRepository.update(book)

        val bookToAssert = entityManager.find(Book::class.java, "001")
        Assertions.assertEquals("Ciudad de papel", bookToAssert.nombre_libro)
        Assertions.assertEquals("Scribe", bookToAssert.publisher.name)
        println(bookToAssert)
    }

    @Test
    fun testDelete() {
        val publisher = Publisher("21", "Planeta")
        entityManager.persist(Book("001", "A1", 10,"Amigo imaginario", publisher))
        bookRepository.delete("001")
        val book = entityManager.find(Book::class.java, "001")
        Assertions.assertNull(book)
        println(book)
    }

    @Test
    fun testFindBookByPublisher() {
        val publisherOne = Publisher("21", "Planeta")
        entityManager.persist(publisherOne)
        entityManager.persist(Book("001", "A1", 10,"Amigo imaginario", publisherOne))
        entityManager.persist(Book("002", "A2", 10,"Magia salvaje", publisherOne))

        val list = bookRepository.findBookPublisher("21")
        println(list[0].nombre_libro)
        println(list[1].nombre_libro)
        Assertions.assertEquals(2, list.size)
    }


}