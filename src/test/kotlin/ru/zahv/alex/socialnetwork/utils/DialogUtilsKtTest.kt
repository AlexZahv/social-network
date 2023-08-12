package ru.zahv.alex.socialnetwork.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
internal class DialogUtilsKtTest {

    @Test
    fun getDialogId() {
        val from = "3507b540-3d2c-4d4e-a2f8-e05fb7c6579e"
        val to = "165d4b5d-c058-47d5-a89d-ae10cd80fd45"
        assertThat(getDialogId(from, to)).isEqualTo("$to:$from")
    }
}