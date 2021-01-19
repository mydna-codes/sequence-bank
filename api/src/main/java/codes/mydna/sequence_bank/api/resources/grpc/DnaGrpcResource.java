package codes.mydna.sequence_bank.api.resources.grpc;

import codes.mydna.auth.common.RoleAccess;
import codes.mydna.auth.common.enums.Privilege;
import codes.mydna.auth.common.models.User;
import codes.mydna.rest.exceptions.RestException;
import codes.mydna.sequence_bank.lib.Dna;
import codes.mydna.lib.grpc.DnaServiceGrpc;
import codes.mydna.lib.grpc.DnaServiceProto;
import codes.mydna.sequence_bank.lib.grpc.mappers.GrpcDnaMapper;
import codes.mydna.sequence_bank.lib.grpc.mappers.GrpcUserMapper;
import codes.mydna.sequence_bank.services.DnaService;
import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
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

        LOG.info("Received request for dna sequence: " + request.getId());

        DnaService dnaService = CDI.current().select(DnaService.class).get();

        try {
            DnaServiceProto.Dna grpcDna;

            try {
                User user = GrpcUserMapper.fromGrpcUser(request.getUser());
                Dna dna = dnaService.getDna(request.getId(), user);
                grpcDna = GrpcDnaMapper.toGrpcDna(dna);

                int threshold = ConfigurationUtil.getInstance()
                        .getInteger("mydnacodes.thresholds.large-scale.min-seq-length")
                        .orElse(100000);

                // Check if sequence is long enough for large scale analysis service
                if (grpcDna.getSequence().getValue().length() > threshold) {

                    // Verify that user can use this kind of service
                    if (!RoleAccess.canAccess(Privilege.LARGE_SCALE_ANALYSIS, user)) {
                        responseObserver.onError(Status.PERMISSION_DENIED.asException());
                        return;
                    }

                    // FAILED_PRECONDITION -> Service that can handle long sequences should request this dna
                    if (request.getServiceType() != DnaServiceProto.DnaRequest.ServiceType.LARGE_SCALE) {
                        responseObserver.onError(Status.FAILED_PRECONDITION.asException());
                        return;
                    }
                }

                DnaServiceProto.DnaResponse response = DnaServiceProto.DnaResponse.newBuilder()
                        .setDna(grpcDna)
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();

            } catch (RestException e) {
                responseObserver.onError(Status.NOT_FOUND.asException());
            }

        } catch (Exception e) {
            LOG.warning("ERROR while processing DNA request: " + e.getMessage());
            responseObserver.onError(Status.INTERNAL.asException());
        }
    }

}
