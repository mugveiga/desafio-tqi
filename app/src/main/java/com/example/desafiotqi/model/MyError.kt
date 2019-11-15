package com.example.desafiotqi.model

class MyError {

    var message: String? = null
    var title: String? = null
    var code: Int = 0

    constructor(message: String?) {
        this.message = message
    }

    constructor(message: String?, code: Int) {
        this.message = message
        this.code = code
    }

    constructor(title: String?, message: String?, code: Int) {
        this.title = title
        this.message = message
        this.code = code
    }
}
