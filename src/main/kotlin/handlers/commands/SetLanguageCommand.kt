package handlers.commands

import Languages
import Subscriber
import SubscriberService
import com.elbekD.bot.Bot
import com.elbekD.bot.feature.chain.chain
import com.elbekD.bot.feature.chain.jumpToAndFire
import com.elbekD.bot.types.*
import handlers.ICommand
import utilites.initialiseSuccess
import utilites.subscriberChecker
import mu.KotlinLogging
import utilites.sendButton

class SetLanguageCommand(
        override val bot: Bot,
        override val subscribers: SubscriberService,
        override val commandDescription: BotCommand) : ICommand {

    private val logger = KotlinLogging.logger {}

    override fun initialise() {
        bot.chain("/setLang") { msg ->
            subscriberChecker(bot, msg)
        }
            .then("sub_command"){msg ->
            bot.sendButton(msg.chat.id,"Выберите язык словаря из предложенных:", setLangButtons())
            bot.jumpToAndFire("confirmation", msg)
            }
            .then("confirmation") { msg ->
                callbackLangButtons(msg)
            }
            .build()
        initialiseSuccess()
    }

    private fun setDictionary(subscriber: Subscriber, lang: Languages ) {
        subscriber.settings.lang = lang
        logger.info { "User ${subscribers.getNickName(subscriber)} set $lang dictionary" }
    }

    private fun setLangButtons(): InlineKeyboardMarkup {
        val languagesArray = Languages.values()
        val buttonsList = mutableListOf<List<InlineKeyboardButton>>()
        for (language in languagesArray) {
            buttonsList.add(listOf(InlineKeyboardButton(language.title, null, null, language.title)))
        }
        return InlineKeyboardMarkup(buttonsList)
    }

    private fun callbackLangButtons(msg: Message) {
        for (lang in Languages.values()) {
            bot.onCallbackQuery(lang.title) {
                bot.answerCallbackQuery(it.id, "Установлен ${lang.title} словарь")
                bot.sendMessage(msg.chat.id, "Установлен ${lang.title} словарь")
                bot.editMessageReplyMarkup(msg.chat.id, it.message!!.message_id)
                setDictionary(subscribers.getSubscriber(msg.from!!), lang)
            }
        }
    }

}