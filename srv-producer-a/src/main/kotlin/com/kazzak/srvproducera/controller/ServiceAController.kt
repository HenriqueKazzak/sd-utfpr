package com.kazzak.srvproducera.controller

import com.kazzak.srvproducera.model.ClientData
import com.kazzak.srvproducera.service.ServiceWebClient
import jakarta.annotation.PostConstruct
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.io.File
import java.io.StringReader
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher


@RestController
class ServiceAController(private val serviceWebClient: ServiceWebClient) {

//    read the public key from a file public_key.pem
    private lateinit var publicKey: PublicKey
    private val encryptCipher = Cipher.getInstance("RSA")


    @PostMapping("/api/v1/service-a")
    fun serviceA(@RequestBody request: String): Mono<String> {

        val encrypted = encryptCipher.doFinal(request.toByteArray())
        val encodedMessage = Base64.getEncoder().encodeToString(encrypted)
        return serviceWebClient.post(encodedMessage)
    }

    @PostConstruct
    fun init() {
       /* try {
            StringReader(publicKeyAsString).use { reader ->
                PemReader(reader).use { pemReader ->
                    val fact = KeyFactory.getInstance("RSA")
                    val pemObject: PemObject = pemReader.readPemObject()
                    val keyContentAsBytesFromBC: ByteArray = pemObject.getContent()
                    val pubKeySpec =
                        X509EncodedKeySpec(keyContentAsBytesFromBC)
                    val publicKey = fact.generatePublic(pubKeySpec)
                    println(publicKey)
                }
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }*/



        val publicKeyText = File(Paths.get("C:\\Users\\henrique.karmierczak\\IdeaProjects\\sd-utfpr\\srv-producer-a\\src\\main\\resources\\public_key.pem").toUri()).readText()
        val publicKeyBytes = Base64.getDecoder().decode(publicKeyText)
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKeySpec = X509EncodedKeySpec(publicKeyBytes)
        this.publicKey = keyFactory.generatePublic(publicKeySpec)

        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey)
    }
}