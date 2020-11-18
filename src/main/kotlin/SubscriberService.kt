import com.elbekD.bot.http.TelegramApiError
import com.elbekD.bot.types.User
import kotlin.NullPointerException

class SubscriberService {

    var mSubscribers: HashSet<Subscriber> = hashSetOf()

    fun addSubscriber(subscriber: Subscriber){
        mSubscribers.add(subscriber)
    }
    fun removeSubscriber(user: User){
        mSubscribers.dropWhile { it.user == user }
    }
    fun isSubscriberExist(user: User): Boolean{
        return mSubscribers.any { it.user == user }
    }
    /**
     * @param subscriber is `User` or `Subscriber`
     * @throws NullPointerException if `subscriber` neither user nor subscriber
     */
    fun getNickName(subscriber: Any): String{
        mSubscribers.find { subscriber
            return it.displayedName}
        return throw  NullPointerException()
    }
    fun setNickName(user: User, name: String){
        mSubscribers
            .stream()
            .filter{it.user == user}
            .forEach { it.displayedName = name }
    }
    fun isNickNameExist(name: String): Boolean{
        return mSubscribers.any { it.displayedName == name }
    }
    fun getSubscriber(user: User) : Subscriber{
         if(isSubscriberExist(user)){
            return mSubscribers.find { it.user == user }!!
        }else
            throw NullPointerException()
    }

}