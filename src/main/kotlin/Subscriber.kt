import com.elbekD.bot.types.Chat
import com.elbekD.bot.types.User

data class Subscriber(
        val user: User,
        val chat: Chat,
        var displayedName: String = "",
        var score: Score = Score(),
        var settings: Settings = Settings())