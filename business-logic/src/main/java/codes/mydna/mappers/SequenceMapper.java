package codes.mydna.mappers;

import codes.mydna.entities.SequenceEntity;
import codes.mydna.lib.Sequence;

public class SequenceMapper {

    public static Sequence fromEntity(SequenceEntity entity){
        if(entity == null)
            return null;
        Sequence sequence = new Sequence();
        sequence.setValue(entity.getValue());
        return sequence;
    }

    public static SequenceEntity toEntity(Sequence sequence){
        if(sequence == null)
            return null;
        SequenceEntity entity = new SequenceEntity();
        entity.setValue(sequence.getValue());
        return entity;
    }

}
