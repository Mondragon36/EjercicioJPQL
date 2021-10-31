package co.edu.eam.disenosoftware.ejercicioJPA.controller

import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun createUserHappyPathTest() {
        val body = """
           {
            "user_identification": "4",
            "apellido_usuario": "Mondragon",
            "nombre_usuario": "Brayan"
            }
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun createUserNotFoundTest() {
        //prerequisito...
        entityManager.persist(User("10","Mondragon","Brayan"))
        val body = """
           {
            "user_identification": "10",
            "apellido_usuario": "Mondragon",
            "nombre_usuario": "Brayan"
            }
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This person already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun getUsersHasBookHappyPathTest() {
        val body = """
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .get("/users/10/borrows")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }
}