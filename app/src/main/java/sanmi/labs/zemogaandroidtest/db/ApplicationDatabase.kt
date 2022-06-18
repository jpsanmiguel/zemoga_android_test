package sanmi.labs.zemogaandroidtest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import sanmi.labs.zemogaandroidtest.db.entity.CommentEntity
import sanmi.labs.zemogaandroidtest.db.entity.PostCommentCrossRef
import sanmi.labs.zemogaandroidtest.db.entity.PostEntity
import sanmi.labs.zemogaandroidtest.db.entity.UserEntity
import sanmi.labs.zemogaandroidtest.db.entity.typeconverters.Converters

@Database(
    entities = [PostEntity::class, UserEntity::class, CommentEntity::class, PostCommentCrossRef::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract val applicationDatabaseDao: ApplicationDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ApplicationDatabase::class.java,
                        "post_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}