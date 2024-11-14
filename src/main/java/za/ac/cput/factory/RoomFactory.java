package za.ac.cput.factory;

import za.ac.cput.entity.Room;
import za.ac.cput.entity.enums.RoomType;

public class RoomFactory {

    public static Room buildRoom(long roomNumber, double pricePerNight, RoomType roomType) {
        if (roomNumber <= 0 || pricePerNight <= 0 || roomType == null) {
            return null;
        }
        return new Room.Builder()
                .setRoomNumber(roomNumber)
                .setPricePerNight(pricePerNight)
                .setRoomType(roomType)
                .build();
    }
}
