package co.edu.eam.disenosoftware.ejercicioJPA.service

import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Author
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.AuthorBook
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
class BookAuthorServiceTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var authorBookService: AuthorBookService

    @Test
    fun listCategoryByProduct(){
        val author = Author(10,"Cardona", "Frank")
        entityManager.persist(author)
        val publisher = Publisher("21", "Planeta")
        entityManager.persist(publisher)
        val book = Book("001", "A1", 10,"Amigo imaginario", publisher)
        entityManager.persist(book)
        entityManager.persist(AuthorBook(20, author,book))
        entityManager.persist(AuthorBook(30, author,book))
        entityManager.persist(AuthorBook(40, author,book))
        entityManager.persist(AuthorBook(50, author,book))

        val categorys = authorBookService.listAuthorByAuthorBook(20)

        Assertions.assertEquals(4,categorys.size)
    }

}