package it.unibz.inf.ontop.model.term.functionsymbol;


import com.google.common.collect.ImmutableList;
import it.unibz.inf.ontop.model.term.ImmutableExpression;
import it.unibz.inf.ontop.model.term.ImmutableTerm;
import it.unibz.inf.ontop.model.term.TermFactory;

public interface BooleanFunctionSymbol extends OperationPredicate {

    /**
     * Returns TRUE if the NOT operator has to stay ABOVE the expression
     *  (i.e. cannot lead to a change in the operator or cannot be delegated to sub-expressions)
     */
    boolean blocksNegation();

    ImmutableExpression negate(ImmutableList<? extends ImmutableTerm> subTerms, TermFactory termFactory);
}
