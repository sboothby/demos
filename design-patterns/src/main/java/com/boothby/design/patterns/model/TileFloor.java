package com.boothby.design.patterns.model;

public class TileFloor extends AbstractFloor {

    public enum TileType {
        CERAMIC, PORCELAIN, LINOLEUM
    };

    private String tileColor;
    private TileType type;

    public String getTileColor() {
        return tileColor;
    }

    public void setTileColor(String tileColor) {
        this.tileColor = tileColor;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }
}
