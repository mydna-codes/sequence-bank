package codes.mydna.sequence_bank.mappers;

import codes.mydna.sequence_bank.entities.GeneEntity;
import codes.mydna.sequence_bank.lib.Gene;

public class GeneMapper {

    public static Gene fromEntityLazy(GeneEntity entity){
        if(entity == null)
            return null;
        Gene gene = new Gene();
        BaseSequenceMapper.fromEntityLazy(entity, gene);
        return gene;
    }

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
