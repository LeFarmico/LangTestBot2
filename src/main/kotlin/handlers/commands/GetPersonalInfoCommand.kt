package handlers.commands

import SubscriberService
import com.elbekD.bot.Bot
import com.elbekD.bot.feature.chain.chain
import com.elbekD.bot.types.BotCommand
import handlers.ICommand
import utilites.initialiseSuccess
import utilites.subscriberChecker

class GetPersonalInfoCommand(
        override val bot: Bot,
        override val subscribers: SubscriberService,
        override val commandDescription: BotCommand) : ICommand {
    override fun initialise() {
        bot.chain("/getMyInfo"){msg ->
            subscriberChecker(bot, msg)
        }
            .then("sub_command") { msg ->
                bot.sendMessage(msg.chat.id,
                        "Ваши данные\n" +
                            "Ваше имя: ${subscribers.getNickName(msg.from!!)}\n" +
                            "Язык словаря: ${subscribers.getSubscriber(msg.from!!).settings.lang.title}\n"+
                            "Колличество слов в упражнении: ${subscribers.getSubscriber(msg.from!!).settings.testsCount}\n" +
                            "Время между упражнениями: ${subscribers.getSubscriber(msg.from!!).settings.interval}\n" +
                            "Ваш счет: ${subscribers.getSubscriber(msg.from!!).score.personalScore}")
            }
            .build()
        initialiseSuccess()
    }
}