package codes.mydna.api.resources.grpc;

import codes.mydna.exceptions.RestException;
import codes.mydna.lib.Enzyme;
import codes.mydna.lib.grpc.CommonProto;
import codes.mydna.lib.grpc.EnzymeServiceGrpc;
import codes.mydna.lib.grpc.EnzymeServiceProto;
import codes.mydna.lib.mappers.grpc.GrpcUserMapper;
import codes.mydna.services.EnzymeService;
import codes.mydna.status.Status;
import com.kumuluz.ee.grpc.annotations.GrpcService;
import io.grpc.stub.StreamObserver;

import javax.enterprise.inject.spi.CDI;
import java.util.List;
import java.util.logging.Logger;

@GrpcService
public class EnzymeGrpcResource extends EnzymeServiceGrpc.EnzymeServiceImplBase {

    private static final Logger LOG = Logger.getLogger(EnzymeGrpcResource.class.getName());

    @Override
    public void getMultipleEnzymes(EnzymeServiceProto.MultipleEnzymesRequest request, StreamObserver<EnzymeServiceProto.MultipleEnzymesResponse> responseObserver) {
        EnzymeService enzymeService = CDI.current().select(EnzymeService.class).get();

        var response = EnzymeServiceProto.MultipleEnzymesResponse.newBuilder();

        try {

            List<String> ids = request.getIdList();
            for (String id : ids) {

                EnzymeServiceProto.Enzyme protoEnzyme;
                try {
                    Enzyme enzyme = enzymeService.getEnzyme(id, GrpcUserMapper.fromGrpcUser(request.getUser()));
                    protoEnzyme = EnzymeServiceProto.Enzyme.newBuilder()
                            .setBaseSequenceInfo(CommonProto.BaseSequenceInfo.newBuilder()
                                    .setId(enzyme.getId())
                                    .setName(enzyme.getName())
                                    .build())
                            .setSequence(CommonProto.Sequence.newBuilder()
                                    .setValue(enzyme.getSequence().getValue())
                                    .build())
                            .setUpperCut(enzyme.getUpperCut())
                            .setLowerCut(enzyme.getLowerCut())
                            .setEntityStatus(Status.OK.name())
                            .build();

                } catch (RestException e) {
                    protoEnzyme = EnzymeServiceProto.Enzyme.newBuilder()
                            .setBaseSequenceInfo(CommonProto.BaseSequenceInfo.newBuilder()
                                    .setId(id)
                                    .build())
                            .setEntityStatus(Status.NOT_FOUND.name())
                            .build();
                }

                response.addEnzyme(protoEnzyme);
            }

            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            LOG.warning(e.getMessage());
            responseObserver.onError(new Throwable(Status.INTERNAL_SERVER_ERROR.name()));
        }
    }
}
