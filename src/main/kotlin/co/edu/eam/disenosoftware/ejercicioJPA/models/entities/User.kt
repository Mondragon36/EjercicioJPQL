package co.edu.eam.disenosoftware.ejercicioJPA.models.entities

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_usuario")
data class User(

    @Id
    @Column(name = "user_identification")
    val identification: String,

    @Column(name = "apellido_usuario")
    var lastName: String,

    @Column(name = "nombre_usuario")
    var name: String,
):Serializable
