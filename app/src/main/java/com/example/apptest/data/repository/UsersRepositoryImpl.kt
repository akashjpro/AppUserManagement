import com.example.apptest.data.remote.UsersApi
import com.example.apptest.domain.model.User
import com.example.apptest.domain.model.UserRequest
import com.example.apptest.domain.model.toUserRequest
import com.example.apptest.domain.repository.UsersRepository
import com.example.apptest.util.Resource
import com.example.apptest.util.networkBoundResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class UsersRepositoryImpl(
    private val usersApi: UsersApi,
) : UsersRepository {
    override suspend fun getUsers(): Flow<Resource<List<User>>> =
        networkBoundResource {
            usersApi.getUsers()
        }

    override suspend fun getUser(id: Long): Flow<Resource<User>> =
        networkBoundResource {
            usersApi.getUser(id)
        }

    override suspend fun addUser(user: UserRequest): Flow<Resource<User>> =
        networkBoundResource {
            usersApi.addUser(user)
        }

    override suspend fun updateUser(user: User): Flow<Resource<User>> =
        networkBoundResource {
            usersApi.updateUser(
                user.id,
                user.toUserRequest()
            )
        }

    override suspend fun deleteUser(id: Long): Flow<Resource<User>> =
        networkBoundResource {
            usersApi.deleteUser(id)
        }
}