package ru.zahv.alex.socialnetwork.business.exceptions

class UserNotFountException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor() : super("Пользователь не найден")
}