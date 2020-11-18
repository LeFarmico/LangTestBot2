import com.elbekD.bot.Bot
import handlers.*
import handlers.commands.*
import mu.KotlinLogging

val logger = KotlinLogging.logger {}
const val adminChatId: Long = 505567555

fun main(){

    val token = "1252474717:AAHLoOucGIHJklO6gN505UVOCykBQ34vZ9k"
    val username = "@fitnessBodyTracker_bot"
    val bot = Bot.createPolling(username, token)
    val subscribers = SubscriberService()

    val commandController = CommandController()

    commandController.addCommand(StartCommand(bot, subscribers, startCommandDescription))
    commandController.addCommand(SetLanguageCommand(bot, subscribers, signUpCommandDescription))
    commandController.addCommand(SetWordsCountCommand(bot, subscribers, setWordsCountCommandDescription))
    commandController.addCommand(TestCommand(bot, subscribers, testCommandDescription))
    commandController.addCommand(SetIntervalCommand(bot, subscribers, setIntervalCommandDescription))
    commandController.addCommand(GetPersonalInfoCommand(bot, subscribers, GetPersonalInfCommandDescription))

    commandController.setCommandDescriptionList(bot)
    commandController.initialiseCommands()
    logger.info { "Bot initialisation is done!" }
    bot.start()
}

