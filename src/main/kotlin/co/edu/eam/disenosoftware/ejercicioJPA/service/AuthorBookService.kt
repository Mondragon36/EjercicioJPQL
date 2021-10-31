package co.edu.eam.disenosoftware.ejercicioJPA.service

import co.edu.eam.disenosoftware.ejercicioJPA.repositories.AuthorBookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class AuthorBookService {
    @Autowired
    lateinit var authorBookRepository: AuthorBookRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun listAuthorByAuthorBook(id: Long) = authorBookRepository.findByAuthor(id)

}