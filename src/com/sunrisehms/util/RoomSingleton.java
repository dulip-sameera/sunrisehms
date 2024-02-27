
package com.sunrisehms.util;

public class RoomSingleton {
    private static RoomSingleton roomSinngleton;
    
    private RoomTableRow roomTableRow;
    
    private RoomSingleton() {}
    
    public static RoomSingleton getInstance() {
        if (roomSinngleton == null) {
            roomSinngleton = new RoomSingleton();
        }
        
        return roomSinngleton;
    }
    
    public void setRoom(RoomTableRow roomTableRow) {
        this.roomTableRow = roomTableRow;
    }
    
    public RoomTableRow getRoom() {
        return this.roomTableRow;
    }
}
