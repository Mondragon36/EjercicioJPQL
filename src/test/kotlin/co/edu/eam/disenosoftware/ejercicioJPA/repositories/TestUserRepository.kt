package co.edu.eam.disenosoftware.ejercicioJPA.repositories

import co.edu.eam.disenosoftware.ejercicioJPA.models.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class TestUserRepository {
    @Autowired
    lateinit var userRepository: UserRepository
    @AutoConfigureOrder
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {

    }

    @Test
    fun testCreate() {
        userRepository.create(User("1", "Mondragon", "Brayan"))
        val user = entityManager.find(User::class.java, "1")
        Assertions.assertNotNull(user)
        Assertions.assertEquals("Mondragon", user.lastName)
    }

    @Test
    fun testFind() {
        entityManager.persist(User("1", "Mondragon", "Brayan"))
        val user = userRepository.find("1")
        if (user != null) {
            Assertions.assertNotNull(user)
            Assertions.assertEquals("Brayan", user?.name)
        } else {
            println("Is not found")
        }
    }

    @Test
    fun testUpdate() {
        entityManager.persist(User("1", "Mondragon", "Brayan"))

        val user = entityManager.find(User::class.java, "1")
        user.name = "Frank"
        userRepository.update(user)
        val userToAssert = entityManager.find(User::class.java, "1")
        Assertions.assertEquals("Frank", userToAssert.name)
    }

    @Test
    fun testDelete() {
        entityManager.persist(User("1", "Mondragon", "Brayan"))
        userRepository.delete("1")

        val user = entityManager.find(User::class.java, "1")
        Assertions.assertNull(user)
    }

}