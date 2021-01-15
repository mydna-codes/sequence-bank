package codes.mydna.api.resources.grpc;

import codes.mydna.exceptions.RestException;
import codes.mydna.lib.Dna;
import codes.mydna.lib.grpc.DnaServiceGrpc;
import codes.mydna.lib.grpc.DnaServiceProto;
import codes.mydna.lib.grpc.mappers.GrpcDnaMapper;
import codes.mydna.lib.grpc.mappers.GrpcUserMapper;
import codes.mydna.services.DnaService;
import com.kumuluz.ee.grpc.annotations.GrpcService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import javax.enterprise.inject.spi.CDI;
import java.util.logging.Logger;

@GrpcService
public class DnaGrpcResource extends DnaServiceGrpc.DnaServiceImplBase {

    private static final Logger LOG = Logger.getLogger(DnaGrpcResource.class.getName());

    @Override
    public void getDna(DnaServiceProto.DnaRequest request, StreamObserver<DnaServiceProto.DnaResponse> responseObserver) {

        DnaService dnaService = CDI.current().select(DnaService.class).get();

        try {
            DnaServiceProto.Dna grpcDna;

            try {
                Dna dna = dnaService.getDna(request.getId(), GrpcUserMapper.fromGrpcUser(request.getUser()));
                grpcDna = GrpcDnaMapper.toGrpcDna(dna);

            } catch (RestException e) {
                responseObserver.onError(Status.NOT_FOUND.asException());
                return;
            }

            DnaServiceProto.DnaResponse response = DnaServiceProto.DnaResponse.newBuilder()
                    .setDna(grpcDna)
                    .build();

            LOG.info("ID: " + response.getDna().getBaseSequenceInfo().getId());

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            LOG.warning(e.getMessage());
            responseObserver.onError(Status.INTERNAL.asException());
        }
    }

}
