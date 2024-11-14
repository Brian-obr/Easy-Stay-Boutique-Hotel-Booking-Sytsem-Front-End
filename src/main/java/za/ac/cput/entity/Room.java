package za.ac.cput.entity;
import za.ac.cput.entity.enums.RoomType;

import java.util.Objects;
public class Room {
    private long roomNumber;
    private double pricePerNight;
    private RoomType roomType;

    protected Room() {}

    private Room(Builder builder) {
        this.roomNumber = builder.roomNumber;
        this.pricePerNight = builder.pricePerNight;
        this.roomType = builder.roomType;
    }

    public long getRoomNumber() {
        return roomNumber;
    }
    public double getPricePerNight() {
        return pricePerNight;
    }
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber && Double.compare(pricePerNight, room.pricePerNight) == 0 && roomType == room.roomType;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", pricePerNight=" + pricePerNight +
                ", roomType=" + roomType +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, pricePerNight, roomType);
    }

    public static class Builder {
        private long roomNumber;
        private double pricePerNight;
        private RoomType roomType;

        public Builder setRoomNumber(long roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        public Builder setPricePerNight(double pricePerNight) {
            this.pricePerNight = pricePerNight;
            return this;
        }

        public Builder setRoomType(RoomType roomType) {
            this.roomType = roomType;
            return this;
        }

        public Builder copy(Room room) {
            this.roomNumber = room.roomNumber;
            this.pricePerNight = room.pricePerNight;
            this.roomType = room.roomType;
            return this;
        }

        public Room build() {
            return new Room(this);
        }
    }
}
