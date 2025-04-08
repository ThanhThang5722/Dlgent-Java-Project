package com.example.IS216_Dlegent.payload.request;

import java.util.List;

public class DeleteRoomTypeRequest {
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

}
