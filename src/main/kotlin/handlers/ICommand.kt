package handlers

import SubscriberService
import com.elbekD.bot.Bot
import com.elbekD.bot.feature.chain.jumpToAndFire
import com.elbekD.bot.feature.chain.terminateChain
import com.elbekD.bot.types.BotCommand
import com.elbekD.bot.types.Message
import logger

interface ICommand {
    val commandDescription: BotCommand
    val bot: Bot
    val subscribers: SubscriberService
    fun initialise()
}