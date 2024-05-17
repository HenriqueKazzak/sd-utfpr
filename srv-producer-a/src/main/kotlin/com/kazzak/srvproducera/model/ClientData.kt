package com.kazzak.srvproducera.model

data class ClientData(val name: String, val cpf: String, val email: String) {
    fun toByteArray(): ByteArray {
        return "{\"name\":\"$name\",\"cpf\":\"$cpf\",\"email\":\"$email\"}".toByteArray()
    }
}
