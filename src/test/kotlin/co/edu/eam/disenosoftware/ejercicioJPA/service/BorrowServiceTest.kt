package co.edu.eam.disenosoftware.ejercicioJPA.service

import co.edu.eam.disenosoftware.ejercicioJPA.exception.BusinessException
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Book
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Borrow
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Publisher
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class BorrowServiceTest {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var borrowService: BorrowService

    @Test
    fun listaPrestamoDeUnUsuarioHappyPathTest() {
        val user = User("1", "Mondragon", "Brayan")
        entityManager.persist(user)
        val publisher = Publisher("2", "Comfenalco")
        entityManager.persist(publisher)
        val book = Book("001", "A1", 10, "Amigo imaginario", publisher)
        entityManager.persist(book)
        entityManager.persist(Borrow(1L, Date(2021, 9, 23), book, user))
        entityManager.persist(Borrow(2L, Date(2021, 9, 23), book, user))
        entityManager.persist(Borrow(3L, Date(2021, 9, 23), book, user))
        entityManager.persist(Borrow(4L, Date(2021, 9, 23), book, user))

        val products = borrowService.listBorrowByUser("1")

        Assertions.assertEquals(4, products.size)
    }

    @Test
    fun listaPrestamoDeUnUsuarioNotFoundTest() {
        val user = User("1", "Mondragon", "Brayan")
        entityManager.persist(user)
        val publisher = Publisher("2", "Comfenalco")
        entityManager.persist(publisher)
        val book = Book("001", "A1", 10, "Amigo imaginario", publisher)
        entityManager.persist(book)
        val borrow = Borrow(1L, Date(2021, 9, 23), book, user)
        entityManager.persist(borrow)
        val exc = Assertions.assertThrows(
            BusinessException::class.java,
            {
                borrowService.listBorrowByUser("2")
            }
        )
        Assertions.assertEquals("The user does not exist", exc.message)
    }

    @Test
    fun prestarLibroToUserNotFoundTest() {
        val user = User("1", "Mondragon", "Brayan")
        entityManager.persist(user)
        val publisher = Publisher("2", "Comfenalco")
        entityManager.persist(publisher)
        val book = Book("001", "A1", 10, "Amigo imaginario", publisher)
        entityManager.persist(book)
        val borrow = Borrow(1L, Date(2021, 9, 23), book, user)
        entityManager.persist(borrow)
        val exc = Assertions.assertThrows(
            BusinessException::class.java,
            {
                borrowService.lendBook(borrow, "1", "001")
            }
        )
        Assertions.assertEquals("The user does not exist", exc.message)
    }

    @Test
    fun prestarLibroToBookNotFoundTest() {
        val user = User("1", "Mondragon", "Brayan")
        entityManager.persist(user)
        val publisher = Publisher("2", "Comfenalco")
        entityManager.persist(publisher)
        val book = Book("001", "A1", 10, "Amigo imaginario", publisher)
        entityManager.persist(book)
        val borrow = Borrow(1L, Date(2021, 9, 23), book, user)
        entityManager.persist(borrow)
        val exc = Assertions.assertThrows(
            BusinessException::class.java,
            {
                borrowService.lendBook(borrow, "1", "001")
            }
        )
        Assertions.assertEquals("The book does not exist", exc.message)
    }

    @Test
    fun prestarLibroToBookCantidadNotFoundTest() {
        val user = User("1", "Mondragon", "Brayan")
        entityManager.persist(user)
        val publisher = Publisher("2", "Comfenalco")
        entityManager.persist(publisher)
        val book = Book("001", "A1", 10, "Amigo imaginario", publisher)
        entityManager.persist(book)
        val borrow = Borrow(1L, Date(2021, 9, 23), book, user)
        entityManager.persist(borrow)
        val exc = Assertions.assertThrows(
            BusinessException::class.java,
            {
                borrowService.lendBook(borrow, "1", "001")
            }
        )
        Assertions.assertEquals("No hay ejemplares de este libro para prestar", exc.message)
    }

    @Test
    fun prestarLibroCuandoHayMasDe5LibrosPrestados() {
        val user = User("1", "Mondragon", "Brayan")
        entityManager.persist(user)
        val publisher = Publisher("2", "Comfenalco")
        entityManager.persist(publisher)
        val book = Book("001", "A1", 10, "Amigo imaginario", publisher)
        entityManager.persist(book)
        val borrow = Borrow(1L, Date(2021, 9, 23), book, user)
        entityManager.persist(book)

        for (i in 1..6) {
            entityManager.persist(
                Borrow(
                    i.toLong(),
                    Date(2021, 9, 23),
                    book,
                    user)
            )
        }

        val exc = Assertions.assertThrows(
            BusinessException::class.java
        ) {
            borrowService.lendBook(Borrow(1L,Date(2021, 9, 23),book, user),"1","001")
        }

        Assertions.assertEquals("Solo 5 prestamos por persona", exc.message)
    }

    @Test
    fun prestarLibroToPersonHappyPathTest() {
        val user = User("1", "Mondragon", "Brayan")
        entityManager.persist(user)
        val publisher = Publisher("2", "Comfenalco")
        entityManager.persist(publisher)
        val book = Book("001", "A1", 10, "Amigo imaginario", publisher)
        entityManager.persist(book)
        val borrow = Borrow(1L, Date(2021, 9, 23), book, user)
        entityManager.persist(borrow)

        borrowService.lendBook(borrow, "1", "001")

        val prest = entityManager.find(Borrow::class.java, "1")

        Assertions.assertNotNull(prest)
        Assertions.assertEquals("Brayan", prest.user.name)
        Assertions.assertEquals("Mondragon", prest.user.lastName)
        Assertions.assertEquals(9, prest.book.cantidad)
    }
}