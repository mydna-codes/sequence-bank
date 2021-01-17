package codes.mydna.api.resources.grpc;

import codes.mydna.exceptions.RestException;
import codes.mydna.lib.Gene;
import codes.mydna.lib.grpc.GeneServiceGrpc;
import codes.mydna.lib.grpc.GeneServiceProto;
import codes.mydna.lib.grpc.mappers.GrpcGeneMapper;
import codes.mydna.lib.grpc.mappers.GrpcUserMapper;
import codes.mydna.services.GeneService;
import com.kumuluz.ee.grpc.annotations.GrpcService;
import io.grpc.Status;
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
            Logger.getLogger("nekns").info("GENENES");
            List<String> ids = request.getIdList();

            for (String id : ids) {

                GeneServiceProto.Gene grpcGene;

                try {
                    Gene gene = geneService.getGene(id, GrpcUserMapper.fromGrpcUser(request.getUser()));
                    grpcGene = GrpcGeneMapper.toGrpcGene(gene);
                    response.addGene(grpcGene);

                } catch (RestException e) {
                    // do nothing
                }
            }

            responseObserver.onNext(response.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            LOG.warning(e.getMessage());
            responseObserver.onError(Status.INTERNAL.asException());
        }
    }
}
