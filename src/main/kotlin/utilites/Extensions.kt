package utilites

import com.elbekD.bot.Bot
import com.elbekD.bot.feature.chain.jumpToAndFire
import com.elbekD.bot.feature.chain.terminateChain
import com.elbekD.bot.http.TelegramApiError
import com.elbekD.bot.types.Message
import com.elbekD.bot.types.ReplyKeyboard
import handlers.ICommand
import logger

/**
 * @param chatId is `Int`, `Long` or `String`
 * @throws IllegalArgumentException if `chatId` neither integer nor string
 * @throws TelegramApiError if error returned in response
 */
fun Bot.sendButton(chatId: Any, text: String, markup: ReplyKeyboard ){
    sendMessage(chatId,
            text,
            null,
            null,
            null,
            null,
            markup)
}
fun ICommand.initialiseSuccess(){
    logger.info { this.javaClass.name + "${commandDescription.command}. Initialise success" }
}
fun ICommand.subscriberChecker(bot: Bot, msg: Message){
    if (!subscribers.isSubscriberExist(msg.from!!)) {
        bot.sendMessage(msg.chat.id, "Мы еще не знакомы, сперва введите /start")
        bot.terminateChain(msg.chat.id)
    }
    else {
        bot.jumpToAndFire("sub_command", msg)
    }
}