package co.edu.eam.disenosoftware.ejercicioJPA.models

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_editorial")
data class Publisher(

    @Id
    @Column(name = "codigo_editorial")
    val code: String,

    @Column(name = "nombre_editorial")
    var name: String,
):Serializable
