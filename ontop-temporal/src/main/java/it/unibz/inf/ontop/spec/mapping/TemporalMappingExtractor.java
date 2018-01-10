package it.unibz.inf.ontop.spec.mapping;

import it.unibz.inf.ontop.dbschema.DBMetadata;
import it.unibz.inf.ontop.exception.DBMetadataExtractionException;
import it.unibz.inf.ontop.exception.MappingException;
import it.unibz.inf.ontop.iq.tools.ExecutorRegistry;
import it.unibz.inf.ontop.spec.OBDASpecInput;
import it.unibz.inf.ontop.spec.mapping.pp.PreProcessedMapping;
import it.unibz.inf.ontop.spec.ontology.ClassifiedTBox;

import javax.annotation.Nonnull;
import java.util.Optional;

//@ImplementedBy(it.unibz.inf.ontop.spec.mapping.impl.TemporalMappingExtractorImpl.class)
public interface TemporalMappingExtractor extends MappingExtractor {
    interface MappingAndDBMetadata extends MappingExtractor.MappingAndDBMetadata {
        TemporalMapping getTemporalMapping();
    }

    TemporalMappingExtractor.MappingAndDBMetadata extract(@Nonnull OBDASpecInput specInput,
                                                  @Nonnull Optional<DBMetadata> dbMetadata,
                                                  @Nonnull Optional<ClassifiedTBox> saturatedTBox,
                                                  @Nonnull ExecutorRegistry executorRegistry)
            throws MappingException, DBMetadataExtractionException;

    TemporalMappingExtractor.MappingAndDBMetadata extract(@Nonnull PreProcessedMapping ppMapping,
                                                  @Nonnull OBDASpecInput specInput,
                                                  @Nonnull Optional<DBMetadata> dbMetadata,
                                                  @Nonnull Optional<ClassifiedTBox> saturatedTBox,
                                                  @Nonnull ExecutorRegistry executorRegistry)
            throws MappingException, DBMetadataExtractionException;


}
