package codes.mydna.sequence_bank.services;

import codes.mydna.auth.common.models.User;
import codes.mydna.sequence_bank.lib.Sequence;
import codes.mydna.sequence_bank.lib.enums.SequenceType;

/**
 * @see codes.mydna.sequence_bank.services.impl.SequenceServiceImpl
 */
public interface SequenceService {

    Sequence insertSequence(Sequence sequence, SequenceType sequenceType, User user);
    Sequence updateSequence(Sequence sequence, SequenceType sequenceType, String id, User user);

}
