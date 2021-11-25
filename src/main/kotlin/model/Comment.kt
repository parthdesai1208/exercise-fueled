package model

class Comment : ArrayList<Comment.CommentItem>(){
    data class CommentItem(
        val body: String,
        val email: String,
        val id: Int,
        val name: String,
        val postId: Int
    )
}