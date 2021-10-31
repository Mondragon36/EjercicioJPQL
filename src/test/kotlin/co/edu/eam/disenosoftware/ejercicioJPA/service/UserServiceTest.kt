package co.edu.eam.disenosoftware.ejercicioJPA.service

import co.edu.eam.disenosoftware.ejercicioJPA.exception.BusinessException
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var userService: UserService

    @Test
    fun createUserHappyPathTest(){
        userService.createUser(User("1", "Mondragon", "Brayan"))

        val userToAssert = entityManager.find(User::class.java, "1")
        Assertions.assertNotNull(userToAssert)
        Assertions.assertEquals("Javier", userToAssert.name)
        Assertions.assertEquals("Rodriguez", userToAssert.lastName)
    }

    @Test
    fun createUserAlreadyExistsTest(){
        //Prereqquisitos
        entityManager.persist(User("1", "Mondragon", "Brayan"))

        try {
            userService.createUser(User("1", "Mondragon", "Brayan"))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This person already exists", e.message)
        }
    }
}