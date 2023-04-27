package net.deechael.genshincraft.util

import java.util.*

object StringUtil {

    private val LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

    fun random(length: Int): String {
        val builder = StringBuilder()
        val random = Random()
        for (i in 0 until length) {
            builder.append(LETTERS[random.nextInt(LETTERS.length)])
        }
        return builder.toString()
    }

    fun random(length: Int, excepts: Collection<String?>): String {
        var result = random(length)
        while (excepts.contains(result)) result = random(length)
        return result
    }

    fun count(container: String, content: String): Int {
        val length = content.length
        return if (length == 0) 0 else (container.length - container.replace(content, "").length) / length
    }

    fun isInteger(string: String): Boolean {
        return try {
            string.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

}