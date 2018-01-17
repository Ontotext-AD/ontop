package it.unibz.inf.ontop.temporal.iq.node.impl;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.AssistedInject;
import it.unibz.inf.ontop.injection.IntermediateQueryFactory;
import it.unibz.inf.ontop.iq.IQProperties;
import it.unibz.inf.ontop.iq.IQTree;
import it.unibz.inf.ontop.iq.IntermediateQuery;
import it.unibz.inf.ontop.iq.UnaryIQTree;
import it.unibz.inf.ontop.iq.exception.InvalidIntermediateQueryException;
import it.unibz.inf.ontop.iq.exception.QueryNodeTransformationException;
import it.unibz.inf.ontop.iq.node.*;
import it.unibz.inf.ontop.iq.transform.IQTransformer;
import it.unibz.inf.ontop.iq.transform.TemporalIQTransformer;
import it.unibz.inf.ontop.iq.transform.node.HeterogeneousQueryNodeTransformer;
import it.unibz.inf.ontop.iq.transform.node.HomogeneousQueryNodeTransformer;
import it.unibz.inf.ontop.model.term.ImmutableExpression;
import it.unibz.inf.ontop.model.term.Variable;
import it.unibz.inf.ontop.model.term.VariableOrGroundTerm;
import it.unibz.inf.ontop.substitution.ImmutableSubstitution;
import it.unibz.inf.ontop.temporal.iq.node.TemporalCoalesceNode;
import it.unibz.inf.ontop.temporal.iq.node.TemporalQueryNodeVisitor;
import it.unibz.inf.ontop.utils.VariableGenerator;
import org.eclipse.rdf4j.query.algebra.Coalesce;

import java.util.Optional;

public class TemporalCoalesceNodeImpl implements TemporalCoalesceNode {

    private static final String COALESCE_NODE_STR = "TEMPORAL COALESCE";


    private final IntermediateQueryFactory iqFactory;

    @AssistedInject
    protected TemporalCoalesceNodeImpl(IntermediateQueryFactory iqFactory) {
        this.iqFactory = iqFactory;
    }

    @Override
    public String toString() {
        return COALESCE_NODE_STR;
    }

    @Override
    public void acceptVisitor(QueryNodeVisitor visitor) {
        ((TemporalQueryNodeVisitor)visitor).visit(this);
    }

    @Override
    public QueryNode clone() {
        return null;
    }

    @Override
    public TemporalCoalesceNode acceptNodeTransformer(HomogeneousQueryNodeTransformer transformer) throws QueryNodeTransformationException {
        return this;
    }

    @Override
    public NodeTransformationProposal acceptNodeTransformer(HeterogeneousQueryNodeTransformer transformer) {
        return null;
    }

    @Override
    public ImmutableSet<Variable> getLocalVariables() {
         return ImmutableSet.of();
    }

    @Override
    public boolean isVariableNullable(IntermediateQuery query, Variable variable) {
        return false;
    }

    @Override
    public boolean isSyntacticallyEquivalentTo(QueryNode node) {
        return isEquivalentTo(node);
    }

    @Override
    public ImmutableSet<Variable> getLocallyRequiredVariables() {
        return null;
    }

    @Override
    public ImmutableSet<Variable> getRequiredVariables(IntermediateQuery query) {
        return null;
    }

    @Override
    public ImmutableSet<Variable> getLocallyDefinedVariables() {
        return null;
    }

    @Override
    public boolean isEquivalentTo(QueryNode queryNode) {
        return queryNode instanceof TemporalCoalesceNode;
    }


    @Override
    public IQTree liftBinding(IQTree childIQTree, VariableGenerator variableGenerator, IQProperties currentIQProperties) {
        IQTree newChild = childIQTree.liftBinding(variableGenerator);
        QueryNode newChildRoot = newChild.getRootNode();
        if(newChildRoot instanceof ConstructionNode ){
            IQTree coalesceLevelTree =  iqFactory.createUnaryIQTree(this, ((UnaryIQTree)newChild).getChild(), currentIQProperties.declareLifted());
            return iqFactory.createUnaryIQTree((ConstructionNode)newChildRoot, coalesceLevelTree);
        }else if(newChildRoot instanceof EmptyNode){
            return newChild;
        }
        return iqFactory.createUnaryIQTree(this, newChild, currentIQProperties.declareLifted());
    }

    @Override
    public IQTree applyDescendingSubstitution(ImmutableSubstitution<? extends VariableOrGroundTerm> descendingSubstitution,
                                              Optional<ImmutableExpression> constraint, IQTree child) {
        IQTree newChild = child.applyDescendingSubstitution(descendingSubstitution, constraint);
        return iqFactory.createUnaryIQTree(this, newChild);
    }

    @Override
    public ImmutableSet<Variable> getNullableVariables(IQTree child) {
        return child.getNullableVariables();
    }

    @Override
    public boolean isConstructed(Variable variable, IQTree child) {
        return false;
    }

    @Override
    public IQTree liftIncompatibleDefinitions(Variable variable, IQTree child) {
        return null;
    }

    @Override
    public IQTree propagateDownConstraint(ImmutableExpression constraint, IQTree child) {
        return null;
    }

    @Override
    public IQTree acceptTransformer(IQTree tree, IQTransformer transformer, IQTree child) {
        if (transformer instanceof TemporalIQTransformer){
            return ((TemporalIQTransformer) transformer).transformTemporalCoalesce(tree, this, child);
        } else {
            return transformer.transformNonStandardUnaryNode(tree, this, child);
        }
    }

    @Override
    public void validateNode(IQTree child) throws InvalidIntermediateQueryException {

    }
}