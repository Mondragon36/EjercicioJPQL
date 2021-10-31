package co.edu.eam.disenosoftware.ejercicioJPA.service

import co.edu.eam.disenosoftware.ejercicioJPA.exception.BusinessException
import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Publisher
import co.edu.eam.disenosoftware.ejercicioJPA.repositories.BorrowRepository
import co.edu.eam.disenosoftware.ejercicioJPA.repositories.PublisherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class PublisherService {
    @Autowired lateinit var publisherRepository: PublisherRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createPublisher(publisher: Publisher) {
        val publisherById = publisherRepository.find(publisher.code)

        if (publisherById != null ) {
            throw BusinessException("This publisher already exists")
        }
        publisherRepository.create(publisher)
    }
}