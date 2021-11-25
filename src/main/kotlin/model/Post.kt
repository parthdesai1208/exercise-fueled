package model

class Post : ArrayList<Post.PostItem>(){
    data class PostItem(
        val body: String,
        val id: Int,
        val title: String,
        val userId: Int
    )
}