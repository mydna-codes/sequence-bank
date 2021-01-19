package codes.mydna.sequence_bank.lib.grpc.mappers;

import codes.mydna.sequence_bank.lib.Gene;
import codes.mydna.lib.grpc.GeneServiceProto;

public class GrpcGeneMapper {

    public static Gene fromGrpcGene(GeneServiceProto.Gene grpcGene){

        if(grpcGene == null)
            return null;

        Gene gene = new Gene();
        GrpcBaseSequenceMapper.fromGrpcBaseSequence(gene, grpcGene.getBaseSequenceInfo());
        gene.setSequence(GrpcSequenceMapper.fromGrpcSequence(grpcGene.getSequence()));
        return gene;
    }

    public static GeneServiceProto.Gene toGrpcGene(Gene gene){

        if(gene == null)
            return GeneServiceProto.Gene.newBuilder().build();

        return GeneServiceProto.Gene.newBuilder()
                .setBaseSequenceInfo(GrpcBaseSequenceMapper.toGrpcBaseSequence(gene))
                .setSequence(GrpcSequenceMapper.toGrpcSequence(gene.getSequence()))
                .build();
    }
    
}
