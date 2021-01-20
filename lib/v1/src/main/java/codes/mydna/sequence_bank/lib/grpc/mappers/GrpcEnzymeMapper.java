package codes.mydna.sequence_bank.lib.grpc.mappers;

import codes.mydna.sequence_bank.lib.Enzyme;
import codes.mydna.sequence_bank.lib.grpc.EnzymeServiceProto;

public class GrpcEnzymeMapper {

    public static Enzyme fromGrpcEnzyme(EnzymeServiceProto.Enzyme grpcEnzyme){

        if(grpcEnzyme == null)
            return null;

        Enzyme enzyme = new Enzyme();
        GrpcBaseSequenceMapper.fromGrpcBaseSequence(enzyme, grpcEnzyme.getBaseSequenceInfo());
        enzyme.setSequence(GrpcSequenceMapper.fromGrpcSequence(grpcEnzyme.getSequence()));
        enzyme.setUpperCut(grpcEnzyme.getUpperCut());
        enzyme.setLowerCut(grpcEnzyme.getLowerCut());
        return enzyme;
    }

    public static EnzymeServiceProto.Enzyme toGrpcEnzyme(Enzyme enzyme){

        if(enzyme == null)
            return EnzymeServiceProto.Enzyme.newBuilder().build();

        return EnzymeServiceProto.Enzyme.newBuilder()
                .setBaseSequenceInfo(GrpcBaseSequenceMapper.toGrpcBaseSequence(enzyme))
                .setSequence(GrpcSequenceMapper.toGrpcSequence(enzyme.getSequence()))
                .setUpperCut(enzyme.getUpperCut())
                .setLowerCut(enzyme.getLowerCut())
                .build();
    }


}
