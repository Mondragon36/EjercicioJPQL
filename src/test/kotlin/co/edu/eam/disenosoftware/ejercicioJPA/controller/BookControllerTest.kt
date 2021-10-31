package co.edu.eam.disenosoftware.ejercicioJPA.controller

import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Book
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Borrow
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Publisher
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class controller {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper



    @Test
    fun createBookHappyPathTest() {
        val publisher = Publisher("21", "Planeta")
        entityManager.persist(publisher)
        val body = """
           {
            "id": "006",
            "isbn_libro": "A6",
            "cantidad": 10,
            "nombre_Libro": "Amor en tiempos del colera",
            "id_publisher":{
                "id": 21,
                "nombre_editorial": "Planeta"
            }
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun createBookNotFoundTest() {
        val publisher = Publisher("21", "Planeta")
        entityManager.persist(publisher)
        entityManager.persist(Book("006","A6",10,"Amor en tiempos del colera", publisher))
        val body = """
         {
            "id": "006",
            "isbn_libro": "A6",
            "cantidad": 10,
            "nombre_Libro": "Amor en tiempos del colera",
            "id_publisher":{
                "id": 21,
                "nombre_editorial": "Planeta"
         }
         }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This libro already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun editBookHappyPathTest() {
        val publisher = Publisher("21", "Planeta")
        entityManager.persist(publisher)
        entityManager.persist(Book("006","A6",10,"Amor en tiempos del colera", publisher))

        val body = """
           {
            "id": "4",
            "isbn_libro":"A4",
            "cantidad": 20,
            "nombre_Libro": "Colombia, mi abuelo y yo",
            "id_publisher":{
                "id": 21,
                "nombre_editorial": "Planeta"
            }
        }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .put("/books/4")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun entregarLibroHappyPathTest() {
        val publisher = Publisher("21", "Planeta")
        entityManager.persist(publisher)
        val book = Book("006","A6",10,"Amor en tiempos del colera", publisher)
        entityManager.persist(book)
        val user = User("10","Mondragon","Brayan")
        entityManager.persist(user)
        val borrow = Borrow(30, Date(2021,10,10),book, user)
        entityManager.persist(borrow)
        val body = """
            {
            "id_borrow": 30,
            "id_user": "10",
            "id_Book": "006" 
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .delete("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun getUsersHasBookHappyPathTest() {
        val publisher = Publisher("21", "Planeta")
        entityManager.persist(publisher)
        val book = Book("006","A6",10,"Amor en tiempos del colera", publisher)
        entityManager.persist(book)
        val user = User("10","Mondragon","Brayan")
        entityManager.persist(user)
        val borrow = Borrow(30, Date(2021,10,10), book, user)
        entityManager.persist(borrow)

        val body = """
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .get("/books/30/borrows")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }


}