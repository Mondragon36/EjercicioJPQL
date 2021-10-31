package co.edu.eam.disenosoftware.ejercicioJPA.controller

import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Book
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
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class BorrowControllerTest {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun createBorrowHappyPathTest() {
        val publisher = Publisher("21", "Planeta")
        entityManager.persist(publisher)
        val book = Book("006","A6",11,"Amor en tiempos del colera", publisher)
        entityManager.persist(book)
        val user = User("10","Mondragon","Brayan")
        entityManager.persist(user)
        val body = """
           {
            "id": 30,
            "fecha_prestamo":"2021-10-28",
            "id_user":{
                "user_identification": "10",
                "apellido_usuario": "Mondragon",
                "nombre_usuario": "Brayan"
            },
            "book":{
                "id": "006",
                "isbn_libro":"A7",
                "cantidad": 50,
                "nombre_Libro": "Python 3.10",
                "id_publisher":{
                    "id": 21,
                    "nombre_editorial": "Planeta"
                }
            }
        }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/borrows/10/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }

}