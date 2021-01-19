package codes.mydna.sequence_bank.mappers;

import codes.mydna.sequence_bank.entities.BaseEntity;
import codes.mydna.sequence_bank.lib.BaseType;

public class BaseMapper {

    public static void fromEntity(BaseEntity entity, BaseType baseType) {
        baseType.setId(entity.getId());
        baseType.setCreated(entity.getCreated());
        baseType.setLastModified(entity.getLastModified());
    }

    // Don't convert from type to entity
}
