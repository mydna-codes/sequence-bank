package codes.mydna.lib.grpc.mappers;

import codes.mydna.lib.Dna;
import codes.mydna.lib.grpc.DnaServiceProto;

public class GrpcDnaMapper {

    public static Dna fromGrpcDna(DnaServiceProto.Dna grpcDna){

        if(grpcDna == null)
            return null;

        Dna dna = new Dna();
        GrpcBaseSequenceMapper.fromGrpcBaseSequence(dna, grpcDna.getBaseSequenceInfo());
        dna.setSequence(GrpcSequenceMapper.fromGrpcSequence(grpcDna.getSequence()));
        return dna;
    }

    public static DnaServiceProto.Dna toGrpcDna(Dna dna){

        if(dna == null)
            return DnaServiceProto.Dna.newBuilder().build();

        return DnaServiceProto.Dna.newBuilder()
                .setBaseSequenceInfo(GrpcBaseSequenceMapper.toGrpcBaseSequence(dna))
                .setSequence(GrpcSequenceMapper.toGrpcSequence(dna.getSequence()))
                .build();
    }

}
