package codes.mydna.api.resources.grpc;

import codes.mydna.lib.Dna;
import codes.mydna.lib.Sequence;
import codes.mydna.lib.grpc.DnaServiceGrpc;
import codes.mydna.lib.grpc.Grpc;
import codes.mydna.services.DnaService;
import com.kumuluz.ee.grpc.annotations.GrpcService;
import io.grpc.stub.StreamObserver;

import javax.enterprise.inject.spi.CDI;
import java.util.logging.Logger;

@GrpcService
public class DnaGrpcResource extends DnaServiceGrpc.DnaServiceImplBase {

    private static final Logger LOG = Logger.getLogger(DnaGrpcResource.class.getName());

    @Override
    public void getDna(Grpc.DnaRequest request, StreamObserver<Grpc.DnaResponse> responseObserver) {

        DnaService dnaService = CDI.current().select(DnaService.class).get();

        try {
            Dna dna = dnaService.getDna(request.getId());
            Sequence sequence = dna.getSequence();

            var seqResponseBuilder = Grpc.Sequence.newBuilder()
                    .setValue(sequence.getValue());

            var dnaResponseBuilder = Grpc.Dna.newBuilder()
                    .setId(dna.getId())
                    .setName(dna.getName())
                    .setSequence(seqResponseBuilder.build());

            var response = Grpc.DnaResponse.newBuilder()
                    .setDna(dnaResponseBuilder)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            responseObserver.onError(e);
        }
    }

}
