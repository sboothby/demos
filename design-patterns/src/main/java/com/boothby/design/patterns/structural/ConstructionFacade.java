package com.boothby.design.patterns.structural;

import java.util.List;

import com.boothby.design.patterns.model.AbstractFloor;
import com.boothby.design.patterns.model.CeilingMaterials;
import com.boothby.design.patterns.model.Dimensions;
import com.boothby.design.patterns.model.Door;
import com.boothby.design.patterns.model.DoorKnob;
import com.boothby.design.patterns.model.DoorMaterials;
import com.boothby.design.patterns.model.Drywall;
import com.boothby.design.patterns.model.FloorMaterials;
import com.boothby.design.patterns.model.FloorTrim;
import com.boothby.design.patterns.model.HardwoodFloor;
import com.boothby.design.patterns.model.HardwoodFloor.WoodType;
import com.boothby.design.patterns.model.MaterialSource;
import com.boothby.design.patterns.model.Paint;
import com.boothby.design.patterns.model.Subfloor;
import com.boothby.design.patterns.model.TileFloor;
import com.boothby.design.patterns.model.TileFloor.TileType;
import com.boothby.design.patterns.model.WallMaterials;
import com.boothby.design.patterns.model.WindowBorder;
import com.boothby.design.patterns.model.WindowImpl;
import com.boothby.design.patterns.model.WindowImpl.WindowType;
import com.boothby.design.patterns.model.WindowMaterials;
import com.boothby.design.patterns.structural.MaterialSetCreator.MaterialCreator;

public class ConstructionFacade {

    public enum FloorType {
        HARDWOOD,
        TILE
    }
    
    private MaterialCreator<Paint> paintCreator = new MaterialCreator<>() {
        @Override
        public Paint buildMaterial() {
            return new Paint();
        }
    };
    private MaterialSetCreator<Paint> paintSetCreator = new MaterialSetCreator<>(paintCreator);
    
    MaterialCreator<Drywall> drywallCreator = new MaterialCreator<>() {
        @Override
        public Drywall buildMaterial() {
            return new Drywall();
        }
    };
    MaterialSetCreator<Drywall> drywallSetCreator = new MaterialSetCreator<>(drywallCreator);

    public float estimateRoomConstructionCosts(MaterialSource materialSource,
                                                   int numDoors,
                                                   int numWindows,
                                                   float roomWidth,
                                                   float roomLength,
                                                   FloorType floorType) {
        
        validate(materialSource, numDoors, numWindows, roomWidth, roomLength);
        
        // Create the room materials using configuration, defaults, builders, or other parameters.
        // Calculate and return the estimate. 
        DoorMaterials doorMaterials = createDoorMaterials(numDoors);
        WindowMaterials windowMaterials = createWindowMaterials(numWindows);
        FloorMaterials floorMaterials = createFloorMaterials(roomWidth, roomLength, floorType);
        CeilingMaterials ceilingMaterials = createCeilingMaterials(roomWidth, roomLength);
        WallMaterials wallMaterials = createWallMaterials(roomWidth, roomLength);
        
        float estimate = 
                doorMaterials.getCost(materialSource) +
                windowMaterials.getCost(materialSource) + 
                floorMaterials.getCost(materialSource) + 
                ceilingMaterials.getCost(materialSource) +
                wallMaterials.getCost(materialSource);
        
        return estimate;
    }

    private DoorMaterials createDoorMaterials(int numDoors) {
        MaterialCreator<Door> doorCreator = new MaterialCreator<>() {
            @Override
            public Door buildMaterial() {
                return new Door();
            }
        };
        MaterialSetCreator<Door> doorSetCreator = new MaterialSetCreator<Door>(doorCreator);
        List<Door> doors = doorSetCreator.createListOfMaterials(numDoors);
        
        MaterialCreator<DoorKnob> doorKnobCreator = new MaterialCreator<>() {
            @Override
            public DoorKnob buildMaterial() {
                return new DoorKnob();
            }
        };
        MaterialSetCreator<DoorKnob> doorKnobSetCreator = new MaterialSetCreator<>(doorKnobCreator);
        List<DoorKnob> doorKnobs = doorKnobSetCreator.createListOfMaterials(numDoors);
        float gallonsPaintForDoors = numDoors * 2.0f;
        List<Paint> doorPaints = paintSetCreator.createListOfMaterials((int)gallonsPaintForDoors);
        
        DoorMaterials doorMaterials = new DoorMaterials(doors, doorKnobs, doorPaints);
        return doorMaterials;
    }
    
    private WindowMaterials createWindowMaterials(int numWindows) {
        MaterialCreator<WindowImpl> windowCreator = new MaterialCreator<>() {
            @Override
            public WindowImpl buildMaterial() {
                return new WindowImpl(WindowType.CASEMENT, new Dimensions(2.0f, 3.0f, .25f), "Anderson");
            }
        };
        MaterialSetCreator<WindowImpl> windowSetCreator = new MaterialSetCreator<>(windowCreator);
        List<WindowImpl> windows = windowSetCreator.createListOfMaterials(numWindows);

        MaterialCreator<WindowBorder> borderCreator = new MaterialCreator<>() {
            @Override
            public WindowBorder buildMaterial() {
                return new WindowBorder();
            }
        };
        MaterialSetCreator<WindowBorder> windowBorderSetCreator = new MaterialSetCreator<>(borderCreator);
        List<WindowBorder> windowBorders = windowBorderSetCreator.createListOfMaterials(numWindows * 4);   // 4 borders per window
        
        WindowMaterials windowMaterials = new WindowMaterials(windows, windowBorders);
        return windowMaterials;
    }

    private FloorMaterials createFloorMaterials(float roomWidth, float roomLength, FloorType floorType) {
        float gallonsPaintFloor = (float)(roomWidth * roomLength) / 10.0f;
        List<Paint> floorPaints = paintSetCreator.createListOfMaterials((int)gallonsPaintFloor);
        
        MaterialCreator<FloorTrim> floorTrimCreator = new MaterialCreator<>() {
            @Override
            public FloorTrim buildMaterial() {
                return new FloorTrim();
            }
        };
        MaterialSetCreator<FloorTrim> floorTrimSetCreator = new MaterialSetCreator<>(floorTrimCreator);
        float numFloorTrimsForRoom = (float)(roomWidth * roomLength) / 5.0f;
        List<FloorTrim> floorTrims = floorTrimSetCreator.createListOfMaterials((int)numFloorTrimsForRoom);
        
        Subfloor subFloor = new Subfloor();

        AbstractFloor abstractFloor = null;
        if (floorType == FloorType.HARDWOOD) {
            abstractFloor = new HardwoodFloor(WoodType.OAK, true);
        } else if (floorType == FloorType.TILE) {
            abstractFloor = new TileFloor("OffWhite", TileType.CERAMIC);
        }
        
        FloorMaterials floorMaterials = new FloorMaterials(abstractFloor, floorPaints, floorTrims, subFloor);
        return floorMaterials;
    }
    
    private CeilingMaterials createCeilingMaterials(float roomWidth, float roomLength) {
        float gallonsPaintCeiling = (float)(roomWidth * roomLength) / 10.0f;
        List<Paint> ceilingPaints = paintSetCreator.createListOfMaterials((int)gallonsPaintCeiling);
        
        float numDrywallsForCeiling = (float)(roomWidth * roomLength) / 2.0f;
        List<Drywall> drywallsForCeiling = drywallSetCreator.createListOfMaterials((int)numDrywallsForCeiling);
        
        CeilingMaterials ceilingMaterials = new CeilingMaterials(ceilingPaints, drywallsForCeiling);
        return ceilingMaterials;
    }
    
    private WallMaterials createWallMaterials(float roomWidth, float roomLength) {
        float gallonsPaintWall = (float)(roomWidth * roomLength) / 2.0f;
        List<Paint> wallPaints = paintSetCreator.createListOfMaterials((int)gallonsPaintWall);
        
        float numDrywallsForWalls = (float)(roomWidth * roomLength) / 2.0f;
        List<Drywall> drywallsForWalls =  drywallSetCreator.createListOfMaterials((int)numDrywallsForWalls);
        
        WallMaterials wallMaterials = new WallMaterials(wallPaints, drywallsForWalls);
        return wallMaterials;
    }
        
    private void validate(MaterialSource materialSource,
                                int numDoors,
                                int numWindows,
                                float roomWidth,
                                float roomLength) {
        if (materialSource == null) {
            throw new RuntimeException("Need source of material.");
        }
        if (numDoors <= 0) {
            throw new RuntimeException("No doors - how will you get in?!");
        }
        if (numWindows <= 0) {
            throw new RuntimeException("No windows - it's too dark?!");
        }
        if (roomWidth <= 0) {
            throw new RuntimeException("Invalid room width, must be > 0 ft.");
        }
        if (roomLength <= 0) {
            throw new RuntimeException("Invalid room length, must be > 0 ft.");
        }
    }
    
}
