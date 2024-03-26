package io.appwrite.messagewrite.services

import java.net.InetAddress
import java.net.UnknownHostException

object Network {
    fun isInternetAvailable(): Boolean {
        return try {
            val address = InetAddress.getByName("www.google.com")

            address.hostName.isNotEmpty()
        } catch (e: UnknownHostException) {
            false
        }
    }
}