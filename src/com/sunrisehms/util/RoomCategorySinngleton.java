
package com.sunrisehms.util;

public class RoomCategorySinngleton {
    private static RoomCategorySinngleton roomCategorySinngleton;
    
    private RoomCategoryTableRow roomCategoryTableRow;
    
    private RoomCategorySinngleton() {}
    
    public static RoomCategorySinngleton getInstance() {
        if (roomCategorySinngleton == null) {
            roomCategorySinngleton = new RoomCategorySinngleton();
        }
        
        return roomCategorySinngleton;
    }
    
    public void setRoomCategory(RoomCategoryTableRow roomCategoryTableRow) {
        this.roomCategoryTableRow = roomCategoryTableRow;
    }
    
    public RoomCategoryTableRow getRoomCategory() {
        return this.roomCategoryTableRow;
    }
}
