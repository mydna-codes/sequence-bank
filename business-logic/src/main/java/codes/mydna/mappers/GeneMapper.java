package codes.mydna.mappers;

import codes.mydna.entities.GeneEntity;
import codes.mydna.lib.Gene;

public class GeneMapper {

    public static Gene fromEntity(GeneEntity entity){
        if(entity == null)
            return null;
        Gene gene = new Gene();
        BaseSequenceMapper.fromEntity(entity, gene);
        return gene;
    }

    public static GeneEntity toEntity(Gene gene){
        if(gene == null)
            return null;
        GeneEntity entity = new GeneEntity();
        BaseSequenceMapper.toEntity(gene, entity);
        return entity;
    }

}