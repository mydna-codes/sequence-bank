package codes.mydna.sequence_bank.mappers;

import codes.mydna.sequence_bank.entities.BaseSequenceEntity;
import codes.mydna.sequence_bank.lib.BaseSequenceType;

public class BaseSequenceMapper {

    public static void fromEntityLazy(BaseSequenceEntity entity, BaseSequenceType baseSequenceType) {
        BaseMapper.fromEntity(entity, baseSequenceType);
        baseSequenceType.setName(entity.getName());
        baseSequenceType.setAccess(entity.getAccess());
    }

    public static void fromEntity(BaseSequenceEntity entity, BaseSequenceType baseSequenceType) {
        BaseMapper.fromEntity(entity, baseSequenceType);
        baseSequenceType.setName(entity.getName());
        baseSequenceType.setAccess(entity.getAccess());
        baseSequenceType.setSequence(SequenceMapper.fromEntity(entity.getSequence()));
    }

    public static void toEntity(BaseSequenceType baseSequenceType, BaseSequenceEntity entity){
        entity.setName(baseSequenceType.getName());
        entity.setAccess(baseSequenceType.getAccess());
        entity.setSequence(SequenceMapper.toEntity(baseSequenceType.getSequence()));
    }

}
