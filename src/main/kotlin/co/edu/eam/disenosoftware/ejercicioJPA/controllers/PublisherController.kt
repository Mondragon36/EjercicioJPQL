package co.edu.eam.disenosoftware.ejercicioJPA.controllers

import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Publisher
import co.edu.eam.disenosoftware.ejercicioJPA.service.PublisherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

class PublisherController {
    @Autowired
    lateinit var publisherService: PublisherService

    @PostMapping
    fun createPublisher(@RequestBody publisher: Publisher) {
        publisherService.createPublisher(publisher)
    }
}