package co.edu.eam.disenosoftware.ejercicioJPA.models.requests

data class BookRequet(
    val idBorrow: Long,
    val idUser: String,
    val idBook: String,
)
