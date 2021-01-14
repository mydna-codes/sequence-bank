package codes.mydna.api.resources.grpc;

import codes.mydna.exceptions.RestException;
import codes.mydna.lib.Gene;
import codes.mydna.lib.grpc.CommonProto;
import codes.mydna.lib.grpc.GeneServiceGrpc;
import codes.mydna.lib.grpc.GeneServiceProto;
import codes.mydna.lib.mappers.grpc.GrpcUserMapper;
import codes.mydna.services.GeneService;
import codes.mydna.status.Status;
import com.kumuluz.ee.grpc.annotations.GrpcService;
import io.grpc.stub.StreamObserver;

import javax.enterprise.inject.spi.CDI;
import java.util.List;
import java.util.logging.Logger;

@GrpcService
public class GeneGrpcResource extends GeneServiceGrpc.GeneServiceImplBase {

    private static final Logger LOG = Logger.getLogger(GeneGrpcResource.class.getName());

    @Override
    public void getMultipleGenes(GeneServiceProto.MultipleGenesRequest request, StreamObserver<GeneServiceProto.MultipleGenesResponse> responseObserver) {
        GeneService geneService = CDI.current().select(GeneService.class).get();

        var response = GeneServiceProto.MultipleGenesResponse.newBuilder();

        try {

            List<String> ids = request.getIdList();
            for (String id : ids) {

                GeneServiceProto.Gene protoGene;
                try {
                    Gene gene = geneService.getGene(id, GrpcUserMapper.fromGrpcUser(request.getUser()));
                    protoGene = GeneServiceProto.Gene.newBuilder()
                            .setBaseSequenceInfo(CommonProto.BaseSequenceInfo.newBuilder()
                                    .setId(gene.getId())
                                    .setName(gene.getName())
                                    .build())
                            .setSequence(CommonProto.Sequence.newBuilder()
                                    .setValue(gene.getSequence().getValue())
                                    .build())
                            .setEntityStatus(Status.OK.name())
                            .build();

                } catch (RestException e) {
                    protoGene = GeneServiceProto.Gene.newBuilder()
                            .setBaseSequenceInfo(CommonProto.BaseSequenceInfo.newBuilder()
                                    .setId(id)
                                    .build())
                            .setEntityStatus(Status.NOT_FOUND.name())
                            .build();
                }

                response.addGene(protoGene);
            }

            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            LOG.warning(e.getMessage());
            responseObserver.onError(new Throwable(Status.INTERNAL_SERVER_ERROR.name()));
        }
    }
}
