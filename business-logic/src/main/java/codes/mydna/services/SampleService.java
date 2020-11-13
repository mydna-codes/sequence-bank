package codes.mydna.services;

import codes.mydna.entities.SampleEntity;

import java.util.List;

public interface SampleService {

    List<SampleEntity> getSamples();
    SampleEntity addSampleEntity(SampleEntity entity);

}