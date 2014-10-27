package it.unibz.krdb.obda.ontology.impl;

/*
 * #%L
 * ontop-obdalib-core
 * %%
 * Copyright (C) 2009 - 2014 Free University of Bozen-Bolzano
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import it.unibz.krdb.obda.ontology.BasicClassDescription;
import it.unibz.krdb.obda.ontology.DisjointClassesAxiom;

public class DisjointClassesAxiomImpl implements DisjointClassesAxiom {

	private static final long serialVersionUID = 4576840836473365808L;
	
	private final BasicClassDescription class1;
	private final BasicClassDescription class2;
	
	DisjointClassesAxiomImpl(BasicClassDescription c1, BasicClassDescription c2) {
		this.class1 = c1;
		this.class2 = c2;
	}
	
	@Override
	public String toString() {
		return "disjoint(" + class1 + ", " + class2 + ")";
	}

	@Override
	public BasicClassDescription getFirst() {
		return this.class1;
	}

	@Override
	public BasicClassDescription getSecond() {
		return this.class2;
	}
}