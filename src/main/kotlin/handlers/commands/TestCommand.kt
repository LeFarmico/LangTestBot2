package handlers.commands

import SubscriberService
import com.elbekD.bot.Bot
import com.elbekD.bot.feature.chain.chain
import com.elbekD.bot.feature.chain.jumpToAndFire
import com.elbekD.bot.feature.chain.terminateChain
import com.elbekD.bot.types.*
import handlers.ICommand

class TestCommand(
        override val bot: Bot,
        override val subscribers: SubscriberService,
        override val commandDescription: BotCommand) : ICommand {

    override fun initialise() {

        bot.chain("/test") { msg ->
            if (!subscribers.isSubscriberExist(msg.from!!)) {
                bot.sendMessage(msg.chat.id, "Мы еще не знакомы, сперва введите /start")
                bot.terminateChain(msg.chat.id)
            }
            bot.jumpToAndFire("sub_command", msg)
        }.then("sub_command") { msg ->
            bot.sendMessage(msg.chat.id, "введите чтонить")
        }
        .build()
    }
    private fun sendContact(msg: Message){
        val button = KeyboardButton("Отправить контакт", true)
        val buttonsList = listOf(button)
        val buttonMarkup = ReplyKeyboardMarkup(listOf(buttonsList), true, true)
        bot.sendMessage(
                msg.chat.id,
                "Отправте нам свой контакт, не переживайте, ваши личные данные мы держим в секрете",
                null,
                false,
                false,
                null,
                buttonMarkup)
    }
    private fun yesNoButtons(): InlineKeyboardMarkup{
        val yesButton = InlineKeyboardButton("Да",null, null,"Да" )
        val noButton = InlineKeyboardButton("Нет",null, null,"Нет" )
        val buttonsList = listOf(yesButton,noButton)
        return InlineKeyboardMarkup(listOf(buttonsList))
    }
}