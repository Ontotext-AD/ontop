package it.unibz.inf.ontop.rdf4j.repository.test;

/*
 * #%L
 * ontop-test
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

import it.unibz.inf.ontop.exception.DuplicateMappingException;
import it.unibz.inf.ontop.exception.InvalidMappingException;
import it.unibz.inf.ontop.injection.OBDACoreConfiguration;
import it.unibz.inf.ontop.io.InvalidDataSourceException;
import it.unibz.inf.ontop.model.OBDAModel;
import it.unibz.inf.ontop.ontology.Ontology;
import it.unibz.inf.ontop.owlapi.OWLAPITranslatorUtility;
import it.unibz.inf.ontop.owlapi.QuestOWLIndividualAxiomIterator;
import it.unibz.inf.ontop.owlrefplatform.owlapi.OWLAPIMaterializer;
import it.unibz.inf.ontop.rdf4j.SesameMaterializer;
import junit.framework.TestCase;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.rio.RDFHandler;
import org.eclipse.rdf4j.rio.n3.N3Writer;
import org.eclipse.rdf4j.rio.rdfxml.RDFXMLWriter;
import org.eclipse.rdf4j.rio.turtle.TurtleWriter;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.io.WriterDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.io.*;
import java.util.Iterator;

public class SesameMaterializerCmdTest extends TestCase {

	private final OBDAModel model;
	private Ontology onto;
	private OWLOntology ontology = null;

	/**
	 * Necessary for materialize large RDF graphs without
	 * storing all the SQL results of one big query in memory.
	 */
	private static boolean DO_STREAM_RESULTS = true;

    public SesameMaterializerCmdTest() throws IOException, InvalidMappingException, DuplicateMappingException, InvalidDataSourceException {

		OBDACoreConfiguration configuration = OBDACoreConfiguration.defaultBuilder()
				.nativeOntopMappingFile("src/test/resources/materializer/MaterializeTest.obda")
				.build();
        model = configuration.loadProvidedMapping();
    }
	
	public void setUpOnto() throws OWLOntologyCreationException {
		//create onto
		
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		File f = new File("src/test/resources/materializer/MaterializeTest.owl");
		// Loading the OWL ontology from the file as with normal OWLReasoners
		ontology = manager.loadOntologyFromOntologyDocument(f);
		onto =  OWLAPITranslatorUtility.translate(ontology);
	}
	
	public void testModelN3() throws Exception {
		// output
		File out = new File("src/test/resources/materializer/materializeN3.N3");
		String outfile = out.getAbsolutePath();
		System.out.println(outfile);
		SesameMaterializer materializer = new SesameMaterializer(model, DO_STREAM_RESULTS);
		Iterator<Statement> iterator = materializer.getIterator();
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out), "UTF-8")); 
		RDFHandler handler = new N3Writer(writer);
		handler.startRDF();
		while(iterator.hasNext())
			handler.handleStatement(iterator.next());
		handler.endRDF();
		
		assertEquals(27, materializer.getTriplesCount());
		assertEquals(3, materializer.getVocabularySize());
		
		materializer.disconnect();
		if (out!=null)
			writer.close();
		
		if (out.exists())
			out.delete();
	}
	
	public void testModelTurtle() throws Exception {
		// output
		File out = new File("src/test/resources/materializer/materializeTurtle.ttl");
		String outfile = out.getAbsolutePath();
		System.out.println(outfile);

		// output
		SesameMaterializer materializer = new SesameMaterializer(model, DO_STREAM_RESULTS);
		Iterator<Statement> iterator = materializer.getIterator();
		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(out), "UTF-8"));
		RDFHandler handler = new TurtleWriter(writer);
		handler.startRDF();
		while (iterator.hasNext())
			handler.handleStatement(iterator.next());
		handler.endRDF();

		assertEquals(27, materializer.getTriplesCount());
		assertEquals(3, materializer.getVocabularySize());

		materializer.disconnect();
		if (out != null)
			writer.close();
		if (out.exists())
			out.delete();
	}

	public void testModelRdfXml() throws Exception {
		// output
		File out = new File("src/test/resources/materializer/materializeRdf.owl");
		String outfile = out.getAbsolutePath();
		System.out.println(outfile);

		// output
		SesameMaterializer materializer = new SesameMaterializer(model, DO_STREAM_RESULTS);
		Iterator<Statement> iterator = materializer.getIterator();
		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(out), "UTF-8"));
		RDFHandler handler = new RDFXMLWriter(writer);
		handler.startRDF();
		while (iterator.hasNext())
			handler.handleStatement(iterator.next());
		handler.endRDF();

		assertEquals(27, materializer.getTriplesCount());
		assertEquals(3, materializer.getVocabularySize());

		materializer.disconnect();
		if (out != null)
			writer.close();
		if (out.exists())
			out.delete();
	}
	
	public void testModelOntoN3() throws Exception {
		// output
		File out = new File("src/test/resources/materializer/materializeN3.N3");
		String outfile = out.getAbsolutePath();
		System.out.println(outfile);
		
		setUpOnto();
		SesameMaterializer materializer = new SesameMaterializer(model, onto, DO_STREAM_RESULTS);
		Iterator<Statement> iterator = materializer.getIterator();
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out), "UTF-8")); 
		RDFHandler handler = new N3Writer(writer);
		handler.startRDF();
		while(iterator.hasNext())
			handler.handleStatement(iterator.next());
		handler.endRDF();
		
		assertEquals(51, materializer.getTriplesCount());
		assertEquals(5, materializer.getVocabularySize());
		
		materializer.disconnect();
		if (out!=null)
			writer.close();
		if (out.exists())
			out.delete();
	}
	
	public void testModelOntoTurtle() throws Exception {
		// output
		File out = new File("src/test/resources/materializer/materializeTurtle.ttl");
		String outfile = out.getAbsolutePath();
		System.out.println(outfile);

		setUpOnto();
		// output
		SesameMaterializer materializer = new SesameMaterializer(model, onto, DO_STREAM_RESULTS);
		Iterator<Statement> iterator = materializer.getIterator();
		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(out), "UTF-8"));
		RDFHandler handler = new TurtleWriter(writer);
		handler.startRDF();
		while (iterator.hasNext())
			handler.handleStatement(iterator.next());
		handler.endRDF();

		assertEquals(51, materializer.getTriplesCount());
		assertEquals(5, materializer.getVocabularySize());

		materializer.disconnect();
		if (out != null)
			writer.close();
		if (out.exists())
			out.delete();
	}

	public void testModelOntoRdfXml() throws Exception {
		// output
		File out = new File("src/test/resources/materializer/materializeRdf.owl");
		String outfile = out.getAbsolutePath();
		System.out.println(outfile);

		setUpOnto();
		// output
		SesameMaterializer materializer = new SesameMaterializer(model, onto, DO_STREAM_RESULTS);
		Iterator<Statement> iterator = materializer.getIterator();
		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(out), "UTF-8"));
		RDFHandler handler = new RDFXMLWriter(writer);
		handler.startRDF();
		while (iterator.hasNext())
			handler.handleStatement(iterator.next());
		handler.endRDF();

		assertEquals(51, materializer.getTriplesCount());
		assertEquals(5, materializer.getVocabularySize());

		materializer.disconnect();
		if (out != null)
			writer.close();
		if (out.exists())
			out.delete();
	}
	
	public void testOWLApiModel() throws Exception {
		File out = new File("src/test/resources/materializer/materializeOWL.owl");
		String outfile = out.getAbsolutePath();
		System.out.println(outfile);
		BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(out)); 
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
		try {
		
	
		
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.createOntology(IRI.create(out));
		manager = ontology.getOWLOntologyManager();
		OWLAPIMaterializer materializer = new OWLAPIMaterializer(model, DO_STREAM_RESULTS);

		
		QuestOWLIndividualAxiomIterator iterator = materializer.getIterator();
		
		while(iterator.hasNext()) 
			manager.addAxiom(ontology, iterator.next());
		manager.saveOntology(ontology, new OWLXMLDocumentFormat(), new WriterDocumentTarget(writer));
		
		assertEquals(27, materializer.getTriplesCount());
		assertEquals(3, materializer.getVocabularySize());
		
		materializer.disconnect();
		}catch (Exception e) {throw e; }
		finally {
		if (out!=null) {
			output.close();
		}
		if (out.exists()) {	
			out.delete();
		}
		}
	}
	
	public void testOWLApiModeOnto() throws Exception {
		File out = new File("src/test/resources/materializer/materializeOWL2.owl");
		String outfile = out.getAbsolutePath();
		System.out.println(outfile);
		
		setUpOnto();
		
		OWLOntologyManager manager = ontology.getOWLOntologyManager();
		OWLAPIMaterializer materializer = new OWLAPIMaterializer(model, onto, DO_STREAM_RESULTS);
		BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(out)); 
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
		QuestOWLIndividualAxiomIterator iterator = materializer.getIterator();

		while(iterator.hasNext()) 
			manager.addAxiom(ontology, iterator.next());
		manager.saveOntology(ontology, new OWLXMLDocumentFormat(), new WriterDocumentTarget(writer));
		
		assertEquals(51, materializer.getTriplesCount());
		assertEquals(5, materializer.getVocabularySize());
		
		materializer.disconnect();
		if (out!=null)
			output.close();
		if (out.exists())
			out.delete();
	}
}