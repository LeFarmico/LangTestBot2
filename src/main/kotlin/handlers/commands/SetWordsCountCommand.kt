package handlers.commands

import SubscriberService
import com.elbekD.bot.Bot
import com.elbekD.bot.feature.chain.chain
import com.elbekD.bot.feature.chain.jumpTo
import com.elbekD.bot.types.BotCommand
import com.elbekD.bot.types.User
import handlers.ICommand
import utilites.initialiseSuccess
import utilites.subscriberChecker
import mu.KotlinLogging

class SetWordsCountCommand(
        override val bot: Bot,
        override val subscribers: SubscriberService,
        override val commandDescription: BotCommand) : ICommand {

    private val logger = KotlinLogging.logger {}

    override fun initialise() {
        bot.chain("/setWordsCount") { msg ->
            subscriberChecker(bot, msg)
        }
            .then("sub_command") {msg ->
                bot.sendMessage(msg.chat.id,
                        "Введите желаемое число слов в тесте,\n" +
                                "число должно быть более 1 и не более 30-ти")
            }
            .then("setter") { msg ->
                if(msg.text!!.contains("[\\D]".toRegex()) || msg.text!!.toInt() !in 1..30) {
                    bot.sendMessage(msg.chat.id,
                            "Простите вы ввели невеное число,\n" +
                                    "ответ должен быть в виде числа от 1 до 30, попробуйте еще раз.")
                    bot.jumpTo("setter", msg)
                }
                else{
                    setWordsCount(msg.from!!, msg.text!!.toInt())
                    bot.sendMessage(msg.chat.id, "Число слов в упражнении равно: ${msg.text}")
                }
            }
            .build()
        initialiseSuccess()
    }
    private fun setWordsCount(user: User, wordsCount: Int){
        subscribers.getSubscriber(user).settings.testsCount = wordsCount
        logger.info { "User ${subscribers.getNickName(user)} set words $wordsCount in test " }
    }
}