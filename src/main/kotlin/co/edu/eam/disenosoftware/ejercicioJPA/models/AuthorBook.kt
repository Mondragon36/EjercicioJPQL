package co.edu.eam.disenosoftware.ejercicioJPA.models

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "tbl_libro_autor")
data class AuthorBook(

    @Id
    @Column(name = "id")
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "id_autor")
    val author: Author,

    @ManyToOne
    @JoinColumn(name = "id_libro")
    val book: Book,
):Serializable
