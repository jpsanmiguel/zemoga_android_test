package sanmi.labs.zemogaandroidtest.data.source.remote

import sanmi.labs.zemogaandroidtest.data.source.remote.dto.CommentDTO
import sanmi.labs.zemogaandroidtest.data.source.remote.dto.PostDTO
import sanmi.labs.zemogaandroidtest.data.source.remote.dto.UserDTO
import sanmi.labs.zemogaandroidtest.util.commentsDTO
import sanmi.labs.zemogaandroidtest.util.postsDTO
import sanmi.labs.zemogaandroidtest.util.usersDTO

class FakePostService : PostService {
    override suspend fun getPosts(): List<PostDTO> {
        return postsDTO
    }

    override suspend fun getPostComments(id: Long): List<CommentDTO> {
        return commentsDTO
    }

    override suspend fun getPostUser(id: Long): UserDTO {
        return usersDTO.first { it.id == id }
    }
}