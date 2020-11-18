package utilites

import java.lang.IllegalArgumentException

data class Command(val command: String, val properties: String)

class Parser{
    fun getParsedCommand(msg: String): Command{
        var trimmedText = msg.trim()
        return if(isCommand(trimmedText)) {
            trimmedText = trimmedText.replace("[\\s]+".toRegex(), " ")
            Command(cutCommand(trimmedText), cutProperties(trimmedText))
        }
        else throw IllegalArgumentException()
    }
    private fun isCommand(text: String): Boolean{
        return text.startsWith("/")
    }
    private fun cutCommand(text: String): String{
        return if(text.contains(" "))
            text.substring(0, text.indexOf(" "))
        else text
    }
    private fun cutProperties(text: String): String{
        return if (text.contains(" "))
            text.substring(text.indexOf(" ")+1)
        else ""
    }
}