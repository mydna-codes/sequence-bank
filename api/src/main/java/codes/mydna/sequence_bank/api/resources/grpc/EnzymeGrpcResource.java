package codes.mydna.sequence_bank.api.resources.grpc;

import codes.mydna.rest.exceptions.RestException;
import codes.mydna.sequence_bank.lib.Enzyme;
import codes.mydna.lib.grpc.EnzymeServiceGrpc;
import codes.mydna.lib.grpc.EnzymeServiceProto;
import codes.mydna.sequence_bank.lib.grpc.mappers.GrpcEnzymeMapper;
import codes.mydna.sequence_bank.lib.grpc.mappers.GrpcUserMapper;
import codes.mydna.sequence_bank.services.EnzymeService;
import com.kumuluz.ee.grpc.annotations.GrpcService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import javax.enterprise.inject.spi.CDI;
import java.util.List;
import java.util.logging.Logger;

@GrpcService
public class EnzymeGrpcResource extends EnzymeServiceGrpc.EnzymeServiceImplBase {

    private static final Logger LOG = Logger.getLogger(EnzymeGrpcResource.class.getName());

    @Override
    public void getMultipleEnzymes(EnzymeServiceProto.MultipleEnzymesRequest request, StreamObserver<EnzymeServiceProto.MultipleEnzymesResponse> responseObserver) {

        LOG.info("Received request for multiple enzymes");

        EnzymeService enzymeService = CDI.current().select(EnzymeService.class).get();

        var response = EnzymeServiceProto.MultipleEnzymesResponse.newBuilder();

        try {
            List<String> ids = request.getIdList();

            for (String id : ids) {

                EnzymeServiceProto.Enzyme grpcEnzyme;

                try {
                    Enzyme enzyme = enzymeService.getEnzyme(id, GrpcUserMapper.fromGrpcUser(request.getUser()));
                    grpcEnzyme = GrpcEnzymeMapper.toGrpcEnzyme(enzyme);
                    response.addEnzyme(grpcEnzyme);

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
