package it.unibz.inf.ontop.spec;


import it.unibz.inf.ontop.dbschema.DBMetadata;
import it.unibz.inf.ontop.spec.mapping.TemporalQuadrupleMapping;
import it.unibz.inf.ontop.temporal.model.DatalogMTLProgram;
import it.unibz.inf.ontop.temporal.spec.ImmutableTemporalVocabulary;

public interface TemporalOBDASpecification extends OBDASpecification {

    DatalogMTLProgram getDatalogMTLProgram();

    ImmutableTemporalVocabulary getTemporalVocabulary();

    TemporalQuadrupleMapping getTemporalSaturatedMapping();

    DBMetadata getTemporalDBMetadata();
}