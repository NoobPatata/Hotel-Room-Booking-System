package com.hotelbooking;

import java.util.ArrayList;

/**
 * @author Chye Wei Lun
 */
public class RoomManager {
    /**
     * Attributes of RoomManager
     */
    private final DataManager dataManager;
    private final String filename = "rooms";
    ArrayList<Room> rooms;

    /**
     * Constructor
     *
     * @param dataManager
     */
    public RoomManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * Check if the file rooms exist or not
     * If not, execute the function createRoom
     */
    public void initRooms() {
        ArrayList<Room> roomObjList = (ArrayList) dataManager.readSerializedObjectList(this.filename);
        if (roomObjList == null) {
            this.rooms = new ArrayList<>();
            createRoom();
            dataManager.writeSerializedObjectList(this.filename, this.rooms);
            roomObjList = (ArrayList) dataManager.readSerializedObjectList(this.filename);
        }
        this.rooms = roomObjList;
    }

    /**
     * Create the room that are available in the hotel
     */
    public void createRoom() {
        //check if rooms exist
        if(rooms.size() > 0) {
            return;
        }

        String roomID = null;

        //first floor
        for (int i = 0; i < 10 ; i++) {

            roomID = "010" + i;
            Room room = new Room(roomID);
//            room.setRoomNumber(roomNumber);
            rooms.add(room);
        }

        //second floor
        for (int i = 0; i < 10 ; i++) {
            roomID = "020" + i;
            Room room = new Room(roomID);
            rooms.add(room);
        }
    }
}
