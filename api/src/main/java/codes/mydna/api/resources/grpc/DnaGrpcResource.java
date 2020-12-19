package codes.mydna.api.resources.grpc;

import codes.mydna.exceptions.RestException;
import codes.mydna.lib.Dna;
import codes.mydna.lib.grpc.CommonProto;
import codes.mydna.lib.grpc.DnaServiceGrpc;
import codes.mydna.lib.grpc.DnaServiceProto;
import codes.mydna.services.DnaService;
import codes.mydna.status.Status;
import com.kumuluz.ee.grpc.annotations.GrpcService;
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

            DnaServiceProto.Dna dnaResponse;
            try {
                Dna dna = dnaService.getDna(request.getId());
                dnaResponse = DnaServiceProto.Dna.newBuilder()
                        .setBaseSequenceInfo(CommonProto.BaseSequenceInfo.newBuilder()
                                .setId(dna.getId())
                                .setName(dna.getName())
                                .build())
                        .setSequence(CommonProto.Sequence.newBuilder()
                                .setValue(dna.getSequence().getValue())
                                .build())
                        .setEntityStatus(Status.OK.toString())
                        .build();
            } catch (RestException e) {
                dnaResponse = DnaServiceProto.Dna.newBuilder()
                        .setEntityStatus(Status.NOT_FOUND.name())
                        .build();
            }

            DnaServiceProto.DnaResponse response = DnaServiceProto.DnaResponse.newBuilder()
                    .setDna(dnaResponse)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            LOG.warning(e.getMessage());
            responseObserver.onError(new Throwable(Status.INTERNAL_SERVER_ERROR.name()));
        }
    }

}
