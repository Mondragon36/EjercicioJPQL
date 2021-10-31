package co.edu.eam.disenosoftware.ejercicioJPA.controller

import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Publisher
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
class PublisherControllerTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun createUserHappyPathTest() {
        entityManager.persist(Publisher("21", "Planeta"))

        val body = """
           {
            "id": "21",
            "nombre_editorial": "Planeta"
            }
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/publishers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun createUserNotFoundTest() {
        val publisher = Publisher("22", "Planeta")
        entityManager.persist(publisher)
        val body = """
           {
            "id": "22",
            "nombre_editorial": "Castellana"
            }
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/publishers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This editorial already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }
}