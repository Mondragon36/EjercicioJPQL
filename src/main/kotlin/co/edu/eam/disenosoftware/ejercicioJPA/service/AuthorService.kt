package co.edu.eam.disenosoftware.ejercicioJPA.service

import co.edu.eam.disenosoftware.ejercicioJPA.exception.BusinessException
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Author
import co.edu.eam.disenosoftware.ejercicioJPA.repositories.AuthorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class AuthorService {
    @Autowired
    lateinit var authorRepository: AuthorRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createAuthor(author: Author) {
        val authorById = authorRepository.find(author.id)

        if (authorById != null) {
            throw BusinessException("This author already exist")
        }
        authorRepository.create(author)
    }
}