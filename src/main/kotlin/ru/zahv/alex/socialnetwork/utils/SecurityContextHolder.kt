package ru.zahv.alex.socialnetwork.utils

class SecurityContextHolder {
    companion object{
        private val threadLocal: ThreadLocal<SecurityContext> = ThreadLocal()

        fun storeCurrentUser(userId: String) = threadLocal.set(SecurityContext(userId))

        fun getCurrentUser() = threadLocal.get().userId
    }
}