package codes.mydna.mappers;

import codes.mydna.entities.EnzymeEntity;
import codes.mydna.lib.Enzyme;

public class EnzymeMapper {

    public static Enzyme fromEntityLazy(EnzymeEntity entity){
        if(entity == null)
            return null;
        Enzyme enzyme = new Enzyme();
        BaseSequenceMapper.fromEntityLazy(entity, enzyme);
        return enzyme;
    }

    public static Enzyme fromEntity(EnzymeEntity entity){
        if(entity == null)
            return null;
        Enzyme enzyme = new Enzyme();
        BaseSequenceMapper.fromEntity(entity, enzyme);
        enzyme.setUpperCut(entity.getUpperCut());
        enzyme.setLowerCut(entity.getLowerCut());
        return enzyme;
    }

    public static EnzymeEntity toEntity(Enzyme enzyme){
        if(enzyme == null)
            return null;
        EnzymeEntity entity = new EnzymeEntity();
        BaseSequenceMapper.toEntity(enzyme, entity);
        entity.setUpperCut(enzyme.getUpperCut());
        entity.setLowerCut(enzyme.getLowerCut());
        return entity;
    }

}
