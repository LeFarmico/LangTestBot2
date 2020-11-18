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

class SetIntervalCommand(
        override val bot: Bot,
        override val subscribers: SubscriberService,
        override val commandDescription: BotCommand) : ICommand {

    private val logger = KotlinLogging.logger {}

    override fun initialise() {
        bot.chain("/setInterval"){ msg ->
            subscriberChecker(bot, msg)
        }
            .then("sub_command") {msg ->
                bot.sendMessage(msg.chat.id,
                        "Введите желаемое время между упражнениями,\n" +
                                "число должно быть более 1")
        }
            .then("setter") { msg ->
                if(msg.text!!.contains("[\\D]".toRegex()) || msg.text!!.toInt() < 1) {
                    bot.sendMessage(msg.chat.id,
                            "Простите, неверный формат,\n" +
                                    "ответ должен быть в виде числа, попробуйте еще раз.")
                    bot.jumpTo("setter", msg)
                }
                else{
                    setInterval(msg.from!!, msg.text!!.toInt())
                    bot.sendMessage(msg.chat.id, "Время между упражнениями равно: ${msg.text} мин.")
                }
            }
            .build()
        initialiseSuccess()
    }
    private fun setInterval(user: User, interval: Int){
        subscribers.getSubscriber(user).settings.interval = interval
        logger.info { "User ${subscribers.getNickName(user)} set interval between tests $interval in test " }
    }

}