package org.itsci.dao;

import org.itsci.model.Room;

import java.util.List;

public interface RoomDao {
    List<Room> findAll();

    void save(Room room1);

    Room getRoomById(long id);
}
