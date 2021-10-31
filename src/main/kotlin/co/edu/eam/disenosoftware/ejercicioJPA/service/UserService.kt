package co.edu.eam.disenosoftware.ejercicioJPA.service

import co.edu.eam.disenosoftware.ejercicioJPA.exception.BusinessException
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.User
import co.edu.eam.disenosoftware.ejercicioJPA.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createUser(user: User) {
        val userById = userRepository.find(user.identification)

        if (userById != null) {
            throw BusinessException("This person already exists")
        }
        userRepository.create(user)
    }
}