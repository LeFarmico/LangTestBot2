
enum class Languages(val title: String) {
    RUS("Русский"),
    ENG("Английский"),
    SPN("Испанский")
}
data class Settings(var testsCount: Int = 3, var interval: Int = 30, var lang: Languages = Languages.ENG)
data class Score(var personalScore: Int = 0, var highScore: Int = 0 )
