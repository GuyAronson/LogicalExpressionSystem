import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedHashSet;

/**
 * A class for the binary expressions.
 * @author Guy Aronson
 */
public abstract class BinaryExpression extends BaseExpression implements Expression {
    //Fields:
    private final Expression expression1;
    private final Expression expression2;

    /**
     * Constructor for BinaryExpression.
     * @param e1 - The first expression - Left one.
     * @param e2 - The second expression - Right one.
     */
    public BinaryExpression(Expression e1, Expression e2) {
        this.expression1 = e1;
        this.expression2 = e2;
    }

    @Override
    public List<String> getVariables() {

        //Getting the variables from each expression
        List<String> ex1Vars = this.expression1.getVariables();
        List<String> ex2Vars = this.expression2.getVariables();

        //Merging the lists.
        List<String> merged = new ArrayList<>(ex1Vars);
        merged.addAll(ex2Vars);

        //Removing duplicates.
        Set<String> set = new LinkedHashSet<>(merged);
        merged.clear();
        merged.addAll(set);

        return merged;
    }

    @Override
    public abstract Expression nandify();

    @Override
    public abstract Expression norify();

    /**
     * Accessor.
     * @return gets the first expression.
     */
    public Expression getExpression1() {
        return this.expression1;
    }

    /**
     * Accessor.
     * @return gets the first expression.
     */
    public Expression getExpression2() {
        return this.expression2;
    }
}
