package sanmi.labs.zemogaandroidtest.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import sanmi.labs.zemogaandroidtest.db.entity.CommentEntity
import sanmi.labs.zemogaandroidtest.db.entity.PostCommentCrossRef
import sanmi.labs.zemogaandroidtest.db.entity.PostEntity
import sanmi.labs.zemogaandroidtest.db.entity.PostWithComments
import sanmi.labs.zemogaandroidtest.db.entity.UserEntity
import sanmi.labs.zemogaandroidtest.db.entity.UserWithPostsAndComments

@Dao
interface ApplicationDatabaseDao {
    @Query("SELECT * FROM post_table ORDER BY isFavorite DESC")
    fun getPosts(): LiveData<List<PostEntity>>

    @Transaction
    @Query("SELECT * FROM user_table WHERE userId = :userId LIMIT 1")
    suspend fun getUserWithPostsAndComments(userId: Long): UserWithPostsAndComments?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllPosts(vararg posts: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllComments(vararg comments: CommentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPostCommentCrossRef(postCommentCrossRef: PostCommentCrossRef)

    @Query("SELECT * FROM post_table WHERE postId = :postId")
    suspend fun getPost(postId: Long): PostEntity

    @Update
    suspend fun updatePost(post: PostEntity)

    @Delete
    suspend fun deletePost(post: PostEntity)

    @Delete
    suspend fun deleteComments(vararg comments: CommentEntity)

    @Transaction
    @Query("SELECT * FROM post_table WHERE postId = :postId")
    suspend fun getPostWithComments(postId: Long): PostWithComments
}