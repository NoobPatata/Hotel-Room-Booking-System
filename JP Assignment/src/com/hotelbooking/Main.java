package com.hotelbooking;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DataManager dataManager = new DataManager();
        RoomManager roomManager = new RoomManager(dataManager);
        ReservationManager reservationManager = new ReservationManager(dataManager);
        reservationManager.createFolder();
        roomManager.initRooms();
        LoginForm loginForm = new LoginForm();
        loginForm.setLocationRelativeTo(null);
        loginForm.setVisible(true);

    }


}
