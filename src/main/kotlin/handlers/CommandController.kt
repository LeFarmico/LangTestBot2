package handlers

import com.elbekD.bot.Bot
import com.elbekD.bot.types.BotCommand

class CommandController{

    private var commandsList: HashMap<String, ICommand> = hashMapOf()

    fun addCommand(command: ICommand){
        commandsList[command.commandDescription.command] = command
    }

    fun deleteCommand(command: ICommand){
        commandsList.remove(command.commandDescription.command)
    }

    fun initialiseCommands(){
        commandsList.forEach { cmd ->
            cmd.value.initialise()
        }
    }

    fun setCommandDescriptionList(bot: Bot){
        val commandDescriptionList: MutableList<BotCommand> = mutableListOf()
        commandsList.forEach {cmd ->
            commandDescriptionList.add(cmd.value.commandDescription)
        }
        bot.setMyCommands(commandDescriptionList)
    }
}
