package co.edu.eam.disenosoftware.ejercicioJPA.controllers

import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.Borrow
import co.edu.eam.disenosoftware.ejercicioJPA.service.BorrowService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/borrows")
class BorrowController {
    @Autowired
    lateinit var borrowService: BorrowService

    @PostMapping("/{idUsser}/{idBook}")
    fun createBorrowBook(@PathVariable("idUser") idUser: String, @PathVariable("idBook") idBook: String, @Validated @RequestBody borrow: Borrow) {
        borrowService.lendBook(borrow, idUser, idBook)
    }
}