package com.example.delachat

class Message {
    var message: String? = null
    var senderId: String? = null
    val isImage: Boolean = false

    constructor(){}

    constructor(message: String?, senderId: String?){
        this.message = message
        this.senderId = senderId
    }
}