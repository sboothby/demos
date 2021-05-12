package com.boothby.design.patterns.creational;

import com.boothby.design.patterns.model.Ceiling.CeilingType;
import com.boothby.design.patterns.model.Door.DoorType;
import com.boothby.design.patterns.model.DoorPlacement.DoorLocation;
import com.boothby.design.patterns.model.HardwoodFloor.WoodType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boothby.design.patterns.model.Room;
import com.boothby.design.patterns.model.CarpetFloor.CarpetType;
import com.boothby.design.patterns.model.WallPlacement.WallLocation;

public class RoomBuilderMain {

    private static final Logger logger = LoggerFactory.getLogger(RoomBuilderMain.class);
    
    public static void main(String[] args) {

        Room diningRoom = new RoomBuilder()
            .name("Dining")
            .roomDepth(10)
            .roomHeight(8)
            .roomWidth(12)
            .ceilingType(CeilingType.DRYWALL)
            .ceilingColor("white")
            .hardwoodFloor(WoodType.OAK)
            .floorManufacturer("ACME Floors")
            .hardwoodFloorPreFinished()
            .hardwood(20, 5, .3f)
            .wall(WallLocation.BACK, "white")
            .wall(WallLocation.LEFT, "white")
            .wall(WallLocation.FRONT, "white")
            .wall(WallLocation.RIGHT, "white")
            .door(DoorLocation.BACK_CENTER, DoorType.WOOD, 3, 7, 4, "white", "ACME Doors")
            .door(DoorLocation.FRONT_CENTER, DoorType.WOOD, 3, 7, 4, "white", "ACME Doors")
            .build();
        logger.info("{} {}", diningRoom.getName(), diningRoom.toString());
        logger.info("");
        Room livingRoom = new RoomBuilder()
                .name("Living")
                .roomDepth(12)
                .roomHeight(10)
                .roomWidth(14)
                .ceilingType(CeilingType.DRYWALL)
                .ceilingColor("white")
                .carpetFloor(CarpetType.POLYESTER)
                .carpeting(4, 4, 6)
                .wall(WallLocation.BACK, "white")
                .wall(WallLocation.LEFT, "white")
                .wall(WallLocation.FRONT, "white")
                .wall(WallLocation.RIGHT, "white")
                .door(DoorLocation.BACK_CENTER, DoorType.GLASS, 3, 7, 4, "white", "ACME Doors")
                .build();
        logger.info("{} {}", livingRoom.getName(), livingRoom.toString());
    }
}
