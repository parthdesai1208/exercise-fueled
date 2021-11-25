import com.google.gson.Gson
import model.Comment
import model.Post
import model.User
import org.ietf.jgss.GSSName
import resources.Data
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**

    # Fueled Kotlin Exercise

    A blogging platform stores the following information that is available through separate API endpoints:
    + user accounts
    + blog posts for each user
    + comments for each blog post

    ### Objective
    The organization needs to identify the 3 most engaging bloggers on the platform. Using only Kotlin and the Kotlin standard library, output the top 3 users with the highest average number of comments per post in the following format:

    `[name]` - `[id]`, Score: `[average_comments]`

    Instead of connecting to a remote API, we are providing this data in form of JSON files, which have been made accessible through a custom Resource enum with a `data` method that provides the contents of the file.

    ### What we're looking to evaluate
    1. How you choose to model your data
    2. How you transform the provided JSON data to your data model
    3. How you use your models to calculate this average value
    4. How you use this data point to sort the users

 */

// 1. First, start by modeling the data objects that will be used.






fun main() {

    val userList : ArrayList<User.UserItem> = ArrayList()
    val totalPostOfUserList : ArrayList<Int> = ArrayList()
    val userWisePost : HashMap<Int,List<Post.PostItem>> = HashMap()

    val postList : ArrayList<Post.PostItem> = ArrayList()
    val postPerCommentRatioList : ArrayList<Int> = ArrayList()

    val commentList : ArrayList<Comment.CommentItem> = ArrayList()

    val userAverageCommentList : HashMap<Int,Int> = HashMap()

    userList.addAll(Data.getUsers())
    postList.addAll(Data.getPosts())
    commentList.addAll(Data.getComments())

    userList.forEachIndexed { index, it ->
        var counter = 0
        val tempPostList : ArrayList<Post.PostItem> = ArrayList()
        postList.forEach { it1 ->
            if(it1.userId == it.id){
                counter++
                tempPostList.add(it1)
            }
        }
        totalPostOfUserList.add(counter)
        userWisePost[index] = tempPostList
    }

    userWisePost.forEach {
        postPerCommentRatioList.clear()
        it.value.forEach { postItem ->
            var counter = 0
            commentList.forEach { it1 ->
                if(it1.postId == postItem.id){
                    counter++
                }
            }
            postPerCommentRatioList.add(((counter.toDouble().div(totalPostOfUserList[it.key].toDouble())) * 100).toInt())
        }
        var sum  = 0
        postPerCommentRatioList.forEach { it1 ->
            sum += it1
        }
        userAverageCommentList[it.value[0].userId] = (sum.toDouble()/totalPostOfUserList[it.key].toDouble()).toInt()
    }

        val result = userAverageCommentList.entries.sortedByDescending { it.value }

        var user = userList.find { it1 -> it1.id == result[0].key }
        println(user?.name + " - " + user?.id + ", Score: " + result[0].value)
        user = userList.find { it1 -> it1.id == result[1].key }
        println(user?.name + " - " + user?.id + ", Score: " + result[1].value)
        user = userList.find { it1 -> it1.id == result[2].key }
        println(user?.name + " - " + user?.id + ", Score: " + result[2].value)

}