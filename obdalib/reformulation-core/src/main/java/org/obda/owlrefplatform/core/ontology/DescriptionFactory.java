package org.obda.owlrefplatform.core.ontology;

import inf.unibz.it.obda.model.Predicate;

import java.util.List;


public interface DescriptionFactory {

	public ConceptDescription getConceptDescription(Predicate p, boolean negated, boolean inverse);

	 public ConceptDescription getConceptDescription(Predicate p, boolean negated);

	 public ConceptDescription getConceptDescription(Predicate p);

	 public ConceptDescription getConceptDescription(List<Predicate> p, boolean[] inverseMark, BasicConceptDescription tailConcept);

	 public ConceptDescription getConceptDescription(List<Predicate> p, boolean[] inverseMark);

	 public ConceptDescription getConceptDescription(List<Predicate> p);

	 public ConceptDescription getConceptDescription(List<ConceptDescription> descriptions, boolean isConjunction);

	 public RoleDescription getRoleDescription(Predicate p, boolean inverse, boolean negated);

	 public RoleDescription getRoleDescription(Predicate p, boolean inverse);

	 public RoleDescription getRoleDescription(Predicate p)	;

}
