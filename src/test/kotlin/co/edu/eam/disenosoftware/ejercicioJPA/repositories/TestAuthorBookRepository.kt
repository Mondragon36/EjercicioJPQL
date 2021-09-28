package co.edu.eam.disenosoftware.ejercicioJPA.repositories

import co.edu.eam.disenosoftware.ejercicioJPA.models.Author
import co.edu.eam.disenosoftware.ejercicioJPA.models.AuthorBook
import co.edu.eam.disenosoftware.ejercicioJPA.models.Book
import co.edu.eam.disenosoftware.ejercicioJPA.models.Publisher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager


@SpringBootTest
@Transactional
class TestAuthorBookRepository {
    @Autowired
    lateinit var authorBookRepository: AuthorBookRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {

    }

    @Test
    fun testCreate() {
        val publisher = Publisher("21", "Planeta")
        val book = Book("001", "A1", "Amigo imaginario", publisher)
        val author = Author(1, "Mondragon", "Brayan")
        authorBookRepository.create(AuthorBook(1, author, book))
        val authorBook = entityManager.find(AuthorBook::class.java, 1)
        Assertions.assertNotNull(authorBook)
        Assertions.assertEquals("Brayan", authorBook.author.name)
        Assertions.assertEquals("Amigo imaginario", authorBook.book.nombre_libro)
        Assertions.assertEquals("Planeta", authorBook.book.publisher.name)
    }

    @Test
    fun testFind() {
        val publisher = Publisher("21", "Planeta")
        val book = Book("001", "A1", "Amigo imaginario", publisher)
        val author = Author(1, "Mondragon", "Brayan")
        entityManager.persist(AuthorBook(1, author, book))
        val authorBook = authorBookRepository.find(1)
        if(authorBook != null) {
            Assertions.assertNotNull(authorBook)
            Assertions.assertEquals("Brayan", authorBook?.author.name)
            Assertions.assertEquals("Amigo imaginario", authorBook?.book.nombre_libro)
            Assertions.assertEquals("Planeta", authorBook?.book.publisher.name)
            println(author)
        } else {
            println("Is not found")
        }
    }

    @Test
    fun testUpdate() {
        val publisher = Publisher("21", "Planeta")
        val book = Book("001", "A1", "Amigo imaginario", publisher)
        val author = Author(1, "Mondragon", "Brayan")
        entityManager.persist(AuthorBook(1, author, book))
        val authorBook = entityManager.find(AuthorBook::class.java, 1)
        authorBook.author.name = "Camilo"
        authorBookRepository.update(authorBook)
        val authorBookToAssert = entityManager.find(AuthorBook::class.java, 1)
        Assertions.assertEquals("Camilo", authorBookToAssert.author.name)
        println(authorBookToAssert)
    }

    @Test
    fun testDelete() {
        val publisher = Publisher("21", "Planeta")
        val book = Book("001", "A1", "Amigo imaginario", publisher)
        val author = Author(1, "Mondragon", "Brayan")
        entityManager.persist(AuthorBook(1, author, book))
        println(authorBookRepository.find(1))
        authorBookRepository.delete(1)

        val authorBook = entityManager.find(AuthorBook::class.java, 1)
        Assertions.assertNull(authorBook)
        println(authorBook)
    }

    @Test
    fun testFindAuthorBook() {
        val publisher = Publisher("21", "Planeta")
        val bookOne = Book("001", "A1", "Amigo imaginario", publisher)
        val bookTwo = Book("002", "A2", "Ciudad de papel", publisher)
        val author = Author(1, "Mondragon", "Brayan")
        entityManager.persist(publisher)
        entityManager.persist(bookOne)
        entityManager.persist(bookTwo)
        entityManager.persist(author)
        entityManager.persist(AuthorBook(1, author, bookOne))
        entityManager.persist(AuthorBook(2, author, bookTwo))
        val list = authorBookRepository.findAuthorBook("21")
        Assertions.assertEquals(2, list.size)
        println(list[0].name)
        println(list[1].name)
    }

}