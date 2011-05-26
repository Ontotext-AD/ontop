package inf.unibz.it.obda.gui.swing.treemodel.filter;

import inf.unibz.it.obda.domain.OBDAMappingAxiom;
import inf.unibz.it.obda.model.Atom;
import inf.unibz.it.obda.model.CQIE;
import inf.unibz.it.obda.model.Term;
import inf.unibz.it.obda.model.impl.CQIEImpl;

import java.util.List;



/**
 * @author This Filter receives a string and returns true if any mapping
 *         contains the string given in any of its head atoms
 */
public class MappingHeadVariableTreeModelFilter implements
		TreeModelFilter<OBDAMappingAxiom> {

	private final String srtHeadVariableFilter;

	/**
	 * @param srtHeadVariableFilter
	 *            Constructor of the MappingHeadVariableTreeModelFilter
	 */
	public MappingHeadVariableTreeModelFilter(String srtHeadVariableFilter) {
		this.srtHeadVariableFilter = srtHeadVariableFilter;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * inf.unibz.it.obda.gui.swing.treemodel.filter.TreeModelFilter#match(java
	 * .lang.Object)
	 */
	@Override
	public boolean match(OBDAMappingAxiom object) {
		// TODO Auto-generated method stub
		boolean filterValue = false;
		OBDAMappingAxiom mapping = object;
		CQIE headquery = (CQIEImpl) mapping.getTargetQuery();

		List<Atom> headAtom = headquery.getBody(); // atoms

		for (int i = 0; i < headAtom.size(); i++) {
			Atom atom = headAtom.get(i);
			if (atom.getPredicate().getName().toString().indexOf(srtHeadVariableFilter) != -1) {
				filterValue = true;
			}
			List<Term> terms = atom.getTerms();
			for (int j = 0; j < terms.size(); j++) {
				if ((terms.get(j).toString()).indexOf(srtHeadVariableFilter) != -1) {
					filterValue = true;
				}
			}

		}
		return filterValue;
	}

}
