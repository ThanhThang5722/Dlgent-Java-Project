package com.example.IS216_Dlegent.payload.dto;

import java.util.ArrayList;
import java.util.List;

public class BookingListDTO {
    private List<BookedRoomDTO> upcomingRoom;
    private List<BookedRoomDTO> completedRoom;
    private List<BookedRoomDTO> cancelledRoom;

    public BookingListDTO() {
        this.upcomingRoom = new ArrayList<>();
        this.completedRoom = new ArrayList<>();
        this.cancelledRoom = new ArrayList<>();
    }

    public BookingListDTO(List<BookedRoomDTO> upcomingRoom, List<BookedRoomDTO> completedRoom,
            List<BookedRoomDTO> cancelledRoom) {
        this.upcomingRoom = upcomingRoom;
        this.completedRoom = completedRoom;
        this.cancelledRoom = cancelledRoom;
    }

    public List<BookedRoomDTO> getUpcomingRoom() {
        return upcomingRoom;
    }

    public void setUpcomingRoom(List<BookedRoomDTO> upcomingRoom) {
        this.upcomingRoom = upcomingRoom;
    }

    public List<BookedRoomDTO> getCompletedRoom() {
        return completedRoom;
    }

    public void setCompletedRoom(List<BookedRoomDTO> completedRoom) {
        this.completedRoom = completedRoom;
    }

    public List<BookedRoomDTO> getCancelledRoom() {
        return cancelledRoom;
    }

    public void setCancelledRoom(List<BookedRoomDTO> cancelledRoom) {
        this.cancelledRoom = cancelledRoom;
    }

}
