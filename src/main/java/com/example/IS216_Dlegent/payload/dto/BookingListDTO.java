package com.example.IS216_Dlegent.payload.dto;

import java.util.ArrayList;
import java.util.List;

public class BookingListDTO {
    private List<BookedRoomDTO> upcomingRoom = new ArrayList<>();
    private List<BookedRoomDTO> completedRooom = new ArrayList<>();
    private List<BookedRoomDTO> cancelledRoom = new ArrayList<>();

    public BookingListDTO() {
    }

    public BookingListDTO(List<BookedRoomDTO> upcomingRoom, List<BookedRoomDTO> completedRooom,
            List<BookedRoomDTO> cancelledRoom) {
        this.upcomingRoom = upcomingRoom;
        this.completedRooom = completedRooom;
        this.cancelledRoom = cancelledRoom;
    }

    public List<BookedRoomDTO> getUpcomingRoom() {
        return upcomingRoom;
    }

    public void setUpcomingRoom(List<BookedRoomDTO> upcomingRoom) {
        this.upcomingRoom = upcomingRoom;
    }

    public List<BookedRoomDTO> getCompletedRooom() {
        return completedRooom;
    }

    public void setCompletedRooom(List<BookedRoomDTO> completedRooom) {
        this.completedRooom = completedRooom;
    }

    public List<BookedRoomDTO> getCancelledRoom() {
        return cancelledRoom;
    }

    public void setCancelledRoom(List<BookedRoomDTO> cancelledRoom) {
        this.cancelledRoom = cancelledRoom;
    }

}
