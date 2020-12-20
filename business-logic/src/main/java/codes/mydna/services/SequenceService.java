package codes.mydna.services;

import codes.mydna.entities.enums.SequenceType;
import codes.mydna.lib.Sequence;

/**
 * @see codes.mydna.services.impl.SequenceServiceImpl
 */
public interface SequenceService {

    Sequence insertSequence(Sequence sequence, SequenceType sequenceType);
    Sequence updateSequence(Sequence sequence, SequenceType sequenceType, String id);

}
