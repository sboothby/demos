package com.boothby.design.patterns.structural;

import java.util.ArrayList;
import java.util.List;

public class MaterialSetCreator<T> {

    public interface MaterialCreator<T> {
        T buildMaterial();
    }
    
    private MaterialCreator<T> materialCreator;
    
    public MaterialSetCreator(MaterialCreator<T> materialCreator) {
        this.materialCreator = materialCreator;
    }
    
    public List<T> createListOfMaterials(int numMaterials) {
        List<T> materials = new ArrayList<>();
        for(int i=0; i<numMaterials; i++) {
            materials.add(materialCreator.buildMaterial());
        }
        return materials;
    }
}
