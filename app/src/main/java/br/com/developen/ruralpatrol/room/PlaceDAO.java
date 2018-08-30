package br.com.developen.ruralpatrol.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PlaceDAO {

    @Insert
    Long create(PlaceVO placeVO);

    @Query("SELECT P.* FROM Place P WHERE P.identifier = :identifier")
    PlaceVO retrieve(int identifier);

    @Query("SELECT COUNT(*) > 0 FROM Place P WHERE P.identifier = :identifier")
    Boolean exists(int identifier);

    @Update
    void update(PlaceVO placeVO);

    @Delete
    void delete(PlaceVO placeVO);

    @Query("SELECT P.* FROM Place P")
    List<PlaceVO> list();

    @Query("SELECT P.* FROM Place P WHERE P.denomination LIKE :denomination")
    List<PlaceVO> listByDenomination(String denomination);

}