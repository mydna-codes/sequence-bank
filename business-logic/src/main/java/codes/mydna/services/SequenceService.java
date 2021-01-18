package codes.mydna.services;

import codes.mydna.auth.common.models.User;
import codes.mydna.lib.Sequence;
import codes.mydna.lib.enums.SequenceType;

/**
 * @see codes.mydna.services.impl.SequenceServiceImpl
 */
public interface SequenceService {

    Sequence insertSequence(Sequence sequence, SequenceType sequenceType, User user);
    Sequence updateSequence(Sequence sequence, SequenceType sequenceType, String id, User user);

}
