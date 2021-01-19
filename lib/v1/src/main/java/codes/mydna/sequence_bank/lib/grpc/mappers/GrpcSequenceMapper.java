package codes.mydna.sequence_bank.lib.grpc.mappers;

import codes.mydna.sequence_bank.lib.Sequence;
import codes.mydna.lib.grpc.CommonProto;

public class GrpcSequenceMapper {

    public static Sequence fromGrpcSequence(CommonProto.Sequence grpcSequence){

        if(grpcSequence == null)
            return null;

        Sequence sequence = new Sequence();
        sequence.setValue(grpcSequence.getValue());
        return sequence;
    }

    public static CommonProto.Sequence toGrpcSequence(Sequence sequence){

        if(sequence == null)
            return CommonProto.Sequence.newBuilder().build();

        return CommonProto.Sequence.newBuilder()
                .setValue(sequence.getValue())
                .build();

    }

}
