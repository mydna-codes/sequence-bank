package codes.mydna.mappers;

import codes.mydna.entities.BaseSequenceEntity;
import codes.mydna.lib.BaseSequenceType;

public class BaseSequenceMapper {

    public static void fromEntityLazy(BaseSequenceEntity entity, BaseSequenceType baseSequenceType) {
        BaseMapper.fromEntity(entity, baseSequenceType);
        baseSequenceType.setName(entity.getName());
    }

    public static void fromEntity(BaseSequenceEntity entity, BaseSequenceType baseSequenceType) {
        BaseMapper.fromEntity(entity, baseSequenceType);
        baseSequenceType.setName(entity.getName());
        baseSequenceType.setSequence(SequenceMapper.fromEntity(entity.getSequence()));
    }

    public static void toEntity(BaseSequenceType baseSequenceType, BaseSequenceEntity entity){
        entity.setName(baseSequenceType.getName());
        entity.setSequence(SequenceMapper.toEntity(baseSequenceType.getSequence()));
    }

}
