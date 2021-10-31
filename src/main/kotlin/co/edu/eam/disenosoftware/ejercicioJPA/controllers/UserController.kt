package co.edu.eam.disenosoftware.ejercicioJPA.controllers

import co.edu.eam.disenosoftware.ejercicioJPA.models.entities.User
import co.edu.eam.disenosoftware.ejercicioJPA.service.BorrowService
import co.edu.eam.disenosoftware.ejercicioJPA.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var borrowService: BorrowService

    @PostMapping
    fun createUser(@RequestBody user: User) {
        userService.createUser(user)
    }

    @GetMapping("/{id}/borrows")
    fun getBorrowsByUser(@PathVariable("id") idUser: String) = borrowService.findByUser(idUser)
}