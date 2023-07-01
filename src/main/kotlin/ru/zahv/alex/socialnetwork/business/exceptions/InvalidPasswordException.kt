package ru.zahv.alex.socialnetwork.business.exceptions

import java.lang.RuntimeException

class InvalidPasswordException: RuntimeException {
    constructor(message: String?) : super(message)
    constructor() : super("Невалидные данные")
}