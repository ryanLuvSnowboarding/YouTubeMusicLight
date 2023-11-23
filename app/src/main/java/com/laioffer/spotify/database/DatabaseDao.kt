package com.laioffer.spotify.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.laioffer.spotify.datamodel.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun favoriteAlbum(album: Album)

    @Query("SELECT EXISTS(SELECT * FROM Album WHERE id = :id)")
    fun isFavoriteAlbum(id : Int) : Flow<Boolean>
    //是否当前的id存在
    //flow代表长时间的检测,是个长的链接
    //其他普通的http request是一次性的

    @Delete
    fun unFavoriteAlbum(album: Album)

    @Query("SELECT * FROM Album")
    fun fetchFavoriteAlbums(): Flow<List<Album>>
}