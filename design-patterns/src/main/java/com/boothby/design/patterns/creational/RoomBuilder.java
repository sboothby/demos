package com.boothby.design.patterns.creational;

import org.apache.commons.lang3.StringUtils;

import com.boothby.design.patterns.model.Carpet;
import com.boothby.design.patterns.model.CarpetFloor;
import com.boothby.design.patterns.model.CarpetFloor.CarpetType;
import com.boothby.design.patterns.model.Ceiling;
import com.boothby.design.patterns.model.Ceiling.CeilingType;
import com.boothby.design.patterns.model.Dimensions;
import com.boothby.design.patterns.model.Door;
import com.boothby.design.patterns.model.Door.DoorType;
import com.boothby.design.patterns.model.DoorPlacement;
import com.boothby.design.patterns.model.DoorPlacement.DoorLocation;
import com.boothby.design.patterns.model.FloorBoard;
import com.boothby.design.patterns.model.HardwoodFloor;
import com.boothby.design.patterns.model.HardwoodFloor.WoodType;
import com.boothby.design.patterns.model.Room;
import com.boothby.design.patterns.model.Tile;
import com.boothby.design.patterns.model.TileFloor;
import com.boothby.design.patterns.model.TileFloor.TileType;
import com.boothby.design.patterns.model.Wall;
import com.boothby.design.patterns.model.WallPlacement;
import com.boothby.design.patterns.model.WallPlacement.WallLocation;
import com.boothby.design.patterns.model.WindowImpl;
import com.boothby.design.patterns.model.WindowImpl.WindowType;
import com.boothby.design.patterns.model.WindowPlacement;
import com.boothby.design.patterns.model.WindowPlacement.WindowLocation;

/**
 * Builder (fluent) design pattern. Creates an initialized room.
 */
public class RoomBuilder {

    private Room room;

    public RoomBuilder() {
        init();
    }

    private void init() {
        room = new Room();
        room.setDimensions(new Dimensions());
        room.setCeiling(new Ceiling());
    }

    public Room build() {
        return room;
    }
    
    public RoomBuilder name(String name) {
        if (StringUtils.isBlank(name) || name.length() > 255) {
            throw new RuntimeException("Name is required field, and must be 255 characters or less.");
        }
        room.setName(name);
        return this;
    }

    public RoomBuilder roomWidth(float widthFt) {
        if (widthFt <= 0) {
            throw new RuntimeException("Room width must be greater than 0 feet.");
        }
        room.getDimensions().setWidthFt(widthFt);
        return this;
    }

    public RoomBuilder roomHeight(float heightFt) {
        if (heightFt <= 0) {
            throw new RuntimeException("Room height must be greater than 0 feet.");
        }
        room.getDimensions().setHeightFt(heightFt);
        return this;
    }

    public RoomBuilder roomDepth(float depthFt) {
        if (depthFt <= 0) {
            throw new RuntimeException("Rooom depth must be greater than 0 feet.");
        }
        room.getDimensions().setDepthFt(depthFt);
        return this;
    }

    public RoomBuilder ceilingType(CeilingType type) {
        room.getCeiling().setType(type);
        return this;
    }

    public RoomBuilder ceilingColor(String color) {
        room.getCeiling().setColor(color);
        return this;
    }

    public RoomBuilder door(DoorLocation doorLocation, DoorType type, float doorWidthFt, float doorHeightFt,
            float doorDepthIn, String color, String manufacturer) {
        Door door = new Door();
        door.setColor(color);
        door.setDimensions(new Dimensions(doorWidthFt, doorHeightFt, doorDepthIn * 12.0f));
        door.setManufacturer(manufacturer);
        door.setType(type);

        DoorPlacement placement = new DoorPlacement();
        placement.setDoor(door);
        placement.setDoorLocation(doorLocation);
        room.getDoors().add(placement);

        return this;
    }

    public RoomBuilder window(WindowLocation windowLocation, WindowType windowType, float windowWidthFt, float windowHeightFt, String manufacturer) {
        WindowImpl window = new WindowImpl();
        window.setType(windowType);
        window.setDimensions(new Dimensions(windowWidthFt, windowHeightFt, 0));
        window.setManufacturer(manufacturer);
        
        WindowPlacement placement = new WindowPlacement();
        placement.setWindow(window);
        placement.setWindowLocation(windowLocation);
        room.getWindows().add(placement);
        
        return this;
    }

    public RoomBuilder wall(WallLocation wallLocation, String paintColor) {
        Wall wall = new Wall();
        wall.setPaintColor(paintColor);
        
        WallPlacement placement = new WallPlacement();
        placement.setWall(wall);
        placement.setWallLocation(wallLocation);
        room.getWalls().add(placement);
        
        return this;
    }
    
    public RoomBuilder floorManufacturer(String manufacturer) {
        if (room.getFloor() != null) {
            room.getFloor().setManufacturer(manufacturer);
        } else {
            throw new RuntimeException("Invalid floor.");
        }
        return this;
    }

    /*************************************************************************
     * TILE FLOORING
     *************************************************************************/

    public RoomBuilder tileFloor(TileType type) {
        room.setFloor(new TileFloor());
        ((TileFloor)(room.getFloor())).setType(type);
        return this;
    }

    public RoomBuilder tileFloorColor(String tileColor) {
        if (room.getFloor() != null && room.getFloor() instanceof TileFloor) {
            ((TileFloor) (room.getFloor())).setTileColor(tileColor);
        } else {
            throw new RuntimeException("Invalid floor type.");
        }
        return this;
    }

    public RoomBuilder floorTiles(int numTiles, float tileDepthIn, int tileWidthIn) {
        if (room.getFloor() != null && room.getFloor() instanceof TileFloor) {
            for (int i = 0; i < numTiles; i++) {
                Tile tile = new Tile(new Dimensions(tileWidthIn * 12.0f, 0, tileDepthIn * 12.0f));
                room.getFloor().getFloorItems().add(tile);
            }
        } else {
            throw new RuntimeException("Invalid floor type.");
        }
        return this;
    }

    /*************************************************************************
     * HARDWOOD FLOORING
     *************************************************************************/

    public RoomBuilder hardwoodFloor(WoodType woodType) {
        room.setFloor(new HardwoodFloor());
        ((HardwoodFloor) (room.getFloor())).setType(woodType);
        return this;
    }

    public RoomBuilder hardwoodFloorPreFinished() {
        if (room.getFloor() != null && room.getFloor() instanceof HardwoodFloor) {
            ((HardwoodFloor) (room.getFloor())).setPreFinished(true);
        } else {
            throw new RuntimeException("Invalid floor type.");
        }
        return this;
    }

    public RoomBuilder hardwoodFloorPostFinished() {
        if (room.getFloor() != null && room.getFloor() instanceof HardwoodFloor) {
            ((HardwoodFloor) (room.getFloor())).setPreFinished(false);
        } else {
            throw new RuntimeException("Invalid floor type.");
        }
        return this;
    }

    public RoomBuilder hardwood(int numBoards, float boardDepthFt, float boardWidthFt) {
        if (room.getFloor() != null && room.getFloor() instanceof HardwoodFloor) {
            for (int i = 0; i < numBoards; i++) {
                FloorBoard floorBoard = new FloorBoard(new Dimensions(boardWidthFt, 0, boardDepthFt));
                room.getFloor().getFloorItems().add(floorBoard);
            }
        } else {
            throw new RuntimeException("Invalid floor type.");
        }
        return this;
    }

    /*************************************************************************
     * CARPET FLOORING
     *************************************************************************/

    public RoomBuilder carpetFloor(CarpetType carpetType) {
        room.setFloor(new CarpetFloor());
        ((CarpetFloor) room.getFloor()).setType(carpetType);
        return this;
    }

    public RoomBuilder carpeting(int numPieces, float carpetDepthFt, float carpetWidthFt) {
        if (room.getFloor() != null && room.getFloor() instanceof CarpetFloor) {
            for (int i = 0; i < numPieces; i++) {
                Carpet carpet = new Carpet(new Dimensions(carpetWidthFt, 0, carpetDepthFt));
                room.getFloor().getFloorItems().add(carpet);
            }
        } else {
            throw new RuntimeException("Invalid floor type.");
        }
        return this;
    }
}
