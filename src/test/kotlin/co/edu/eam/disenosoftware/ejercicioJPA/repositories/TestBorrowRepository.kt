package co.edu.eam.disenosoftware.ejercicioJPA.repositories


import co.edu.eam.disenosoftware.ejercicioJPA.models.Book
import co.edu.eam.disenosoftware.ejercicioJPA.models.Borrow
import co.edu.eam.disenosoftware.ejercicioJPA.models.Publisher
import co.edu.eam.disenosoftware.ejercicioJPA.models.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class TestBorrowRepository {

    @Autowired
    lateinit var borrowRepository: BorrowRepository
    @Autowired
    lateinit var entityManager: EntityManager


    @Test
    fun contextLoads() {

    }

    @Test
    fun testCreate() {
        val date = Date(2021)
        val publisher = Publisher("21", "Planeta")
        val book = Book("001", "A1", "Amigo imaginario", publisher)
        val user = User("1", "Mondragon", "Brayan")
        borrowRepository.create(Borrow(1, date, book, user))
        val borrow = entityManager.find(Borrow::class.java, 1)
        println(date)
        println(borrow)
        Assertions.assertNotNull(borrow)
        Assertions.assertEquals("Amigo imaginario", borrow.book.nombre_libro)
        Assertions.assertEquals("Planeta", borrow.book.publisher.name)
        Assertions.assertEquals("Brayan", borrow.user.name)
    }

    @Test
    fun testFind() {
        val date = Date(2021)
        val publisher = Publisher("21", "Planeta")
        val book = Book("001", "A1", "Amigo imaginario", publisher)
        val user = User("1", "Mondragon", "Brayan")
        entityManager.persist(Borrow(1, date, book, user))
        val borrow = borrowRepository.find(1)
        if (borrow != null) {
            Assertions.assertNotNull(borrow)
            Assertions.assertEquals("Amigo imaginario", borrow?.book.nombre_libro)
            Assertions.assertEquals("Planeta", borrow?.book.publisher.name)
            Assertions.assertEquals("Brayan", borrow?.user.name)
        } else {
            println("Is not found")
        }
    }

    @Test
    fun testUpdate() {
        val date = Date(2021)
        val publisher = Publisher("21", "Planeta")
        val book = Book("001", "A1", "Amigo imaginario", publisher)
        val user = User("1", "Mondragon", "Brayan")
        entityManager.persist(Borrow(1, date, book, user))
        val borrow = entityManager.find(Borrow::class.java, 1)
        borrow.user.name = "Frank"
        borrowRepository.update(borrow)
        val borrowToAssert = entityManager.find(borrow::class.java, 1)
        Assertions.assertEquals("Frank", borrowToAssert.user.name)
    }

    @Test
    fun testDelete() {
        val date = Date(2021)
        val publisher = Publisher("21", "Planeta")
        val book = Book("001", "A1", "Amigo imaginario", publisher)
        val user = User("1", "Mondragon", "Brayan")
        entityManager.persist(Borrow(1, date, book, user))
        borrowRepository.delete(1)
        val borrow = entityManager.find(Borrow::class.java, 1)
        Assertions.assertNull(borrow)
    }

    @Test
    fun testFindBorrowUser() {
        val date = Date(2021)
        val publisher = Publisher("21", "Planeta")
        val bookOne = Book("001", "A1", "Amigo imaginario", publisher)
        val bookTwo = Book("002", "A2", "Ciudad de papel", publisher)
        val user = User("1", "Mondragon", "Brayan")
        entityManager.persist(publisher)
        entityManager.persist(bookOne)
        entityManager.persist(bookTwo)
        entityManager.persist(user)
        entityManager.persist(Borrow(1, date, bookOne, user))
        entityManager.persist(Borrow(2, date, bookTwo, user))
        val list = borrowRepository.findBorrowUser("1")
        Assertions.assertEquals(2, list.size)
        println(list[0].book.nombre_libro)
        println(list[1].book.nombre_libro)
    }
}