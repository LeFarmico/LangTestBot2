package handlers

import com.elbekD.bot.types.BotCommand

val startCommandDescription: BotCommand = BotCommand("start", "Приветственное сообщение")
val signUpCommandDescription: BotCommand = BotCommand("setLang", "Выбрать язык словаря")
val setWordsCountCommandDescription: BotCommand = BotCommand("setWordsCount", "Выбрать число слов в одном упражнении")
val setIntervalCommandDescription: BotCommand = BotCommand("setInterval", "Выбрать время между упражнениями")
val GetPersonalInfCommandDescription: BotCommand = BotCommand("getMyInfo", "Получить информацию о себе")

val testCommandDescription: BotCommand = BotCommand("test", "Тестовая команда")
