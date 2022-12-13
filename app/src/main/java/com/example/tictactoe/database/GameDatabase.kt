package com.example.tictactoe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [GameState::class], version = 2, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {

    abstract val gameDatabaseDao: GamedatabaseDao
//the reason to write this instead of do GameDataBase a singleton is that the function needs params
    //singleton doesn't have params
    companion object{
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): GameDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GameDatabase::class.java,
                        "game_history_database"
                    ).fallbackToDestructiveMigration()
                        .addCallback(GameDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }


    private class GameDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.gameDatabaseDao)
                }
            }
        }
        suspend fun populateDatabase(gameDao: GamedatabaseDao) {
            // Delete all content here.
            gameDao.clear()
        }
    }
}