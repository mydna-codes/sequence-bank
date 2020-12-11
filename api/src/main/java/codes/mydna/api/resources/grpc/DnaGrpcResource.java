package codes.mydna.api.resources.grpc;

import codes.mydna.exceptions.RestException;
import codes.mydna.lib.Dna;
import codes.mydna.lib.Sequence;
import codes.mydna.lib.grpc.DnaServiceGrpc;
import codes.mydna.lib.grpc.DnaServiceProto;
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
    public void getDna(DnaServiceProto.Request request, StreamObserver<DnaServiceProto.Response> responseObserver) {

        DnaService dnaService = CDI.current().select(DnaService.class).get();

        try {
            Dna dna = dnaService.getDna(request.getId());
            Sequence sequence = dna.getSequence();

            var seqResponseBuilder = DnaServiceProto.Sequence.newBuilder()
                    .setValue(sequence.getValue());

            var dnaResponseBuilder = DnaServiceProto.Dna.newBuilder()
                    .setId(dna.getId())
                    .setName(dna.getName())
                    .setSequence(seqResponseBuilder.build());

            var response = DnaServiceProto.Response.newBuilder()
                    .setDna(dnaResponseBuilder)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (RestException e) {
            LOG.info(e.getMessage());
            Throwable throwable = (e.getStatusCode() == 404)
                    ? new Throwable(Status.NOT_FOUND.asRuntimeException())
                    : new Throwable(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
            responseObserver.onError(throwable);
        } catch (Exception e) {
            LOG.warning(e.getMessage());
            responseObserver.onError(new Throwable(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException())); ;
        }
    }

}
