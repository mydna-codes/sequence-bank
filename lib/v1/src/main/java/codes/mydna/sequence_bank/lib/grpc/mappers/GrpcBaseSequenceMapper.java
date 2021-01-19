package codes.mydna.sequence_bank.lib.grpc.mappers;

import codes.mydna.sequence_bank.lib.BaseSequenceType;
import codes.mydna.sequence_bank.lib.enums.SequenceAccessType;
import codes.mydna.lib.grpc.CommonProto;

public class GrpcBaseSequenceMapper {

    public static void fromGrpcBaseSequence(BaseSequenceType baseSequence, CommonProto.BaseSequenceInfo grpcBaseSequence){

        if(baseSequence == null || grpcBaseSequence == null)
            return;

        baseSequence.setId(grpcBaseSequence.getId());
        baseSequence.setName(grpcBaseSequence.getName());
        baseSequence.setAccess(SequenceAccessType.valueOf(grpcBaseSequence.getAccess()));
    }

    public static CommonProto.BaseSequenceInfo toGrpcBaseSequence(BaseSequenceType baseSequence){

        if(baseSequence == null)
            return CommonProto.BaseSequenceInfo.newBuilder().build();

        return CommonProto.BaseSequenceInfo.newBuilder()
                .setId(baseSequence.getId())
                .setName(baseSequence.getName())
                .setAccess(baseSequence.getAccess().name())
                .build();
    }

}
