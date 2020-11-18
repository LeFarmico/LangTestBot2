package handlers.commands

import Subscriber
import SubscriberService
import com.elbekD.bot.Bot
import com.elbekD.bot.feature.chain.chain
import com.elbekD.bot.feature.chain.jumpTo
import com.elbekD.bot.feature.chain.jumpToAndFire
import com.elbekD.bot.feature.chain.terminateChain
import com.elbekD.bot.types.BotCommand
import com.elbekD.bot.types.InlineKeyboardButton
import com.elbekD.bot.types.InlineKeyboardMarkup
import handlers.ICommand
import mu.KotlinLogging

class StartCommand(
        override val bot: Bot,
        override val subscribers: SubscriberService,
        override val commandDescription: BotCommand) : ICommand {

    private val logger = KotlinLogging.logger {}

    override fun initialise() {
        bot.chain("/start") { msg ->
            if (!subscribers.isSubscriberExist(msg.from!!)) {
                bot.sendMessage(msg.chat.id, "Здраствуйте, как вас зовут?")
                bot.jumpTo("new_subscriber", msg)
            } else {
                bot.sendMessage(msg.chat.id, "Здраствуйте, наверное мы уже знакомы\n" +
                        "Вас зовут ${subscribers.getNickName(msg.from!!)}?",
                        null,
                        null,
                        null,
                        null,
                        yesNoButtons())
                bot.jumpToAndFire("name_confirm", msg)
            }
        }
            .then("name_confirm") { msg ->
                bot.onCallbackQuery ("Да"){
                    bot.answerCallbackQuery(it.id, "Ваши данные подтверждены")
                    bot.editMessageReplyMarkup(msg.chat.id, it.message!!.message_id)
                    bot.terminateChain(msg.chat.id)
                }
                bot.onCallbackQuery ("Нет"){
                    bot.answerCallbackQuery(it.id, "Вы нажали нет")
                    bot.sendMessage(msg.chat.id, "Тогда давай познакомимся")
                    bot.jumpToAndFire("nickname_setter", msg)
                    bot.editMessageReplyMarkup(msg.chat.id, it.message!!.message_id)

                }
            }
            .then("nickname_setter") { msg ->
                bot.sendMessage(msg.chat.id, "Cкажите, как вас зовут?")
            }
            .then("new_subscriber", isTerminal = true) { msg ->
                bot.sendMessage(msg.chat.id, "Приятно познакомиться, ${msg.text}!")
                subscribers.addSubscriber(Subscriber(msg.from!!, msg.chat, msg.text!!))
                logger.info { "New subscriber was added: ${msg.from.toString()}" }
            }
            .build()
        initialiseSuccess()
    }
    private fun yesNoButtons(): InlineKeyboardMarkup {
        val yesButton = InlineKeyboardButton("Да",null, null,"Да" )
        val noButton = InlineKeyboardButton("Нет",null, null,"Нет" )
        val buttonsList = listOf(yesButton,noButton)
        return InlineKeyboardMarkup(listOf(buttonsList))
    }
}