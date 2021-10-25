import java.util.List;

/**
 * A class for the unary expressions.
 * @author Guy Aronson.
 */
public abstract class UnaryExpression extends BaseExpression implements Expression {

    //Fields:
    private final Expression ex;

    /**
     * A constructor for an unary expression.
     * @param e - The expression to negate.
     */
    public UnaryExpression(Expression e) {
        this.ex = e;
    }

    @Override
    public List<String> getVariables() {

        //Getting the variables from the expression
        return this.ex.getVariables();
    }

    @Override
    public abstract Expression nandify();

    @Override
    public abstract Expression norify();

    /**
     * Accessor.
     * @return the Unary expression.
     */
    public Expression getExpression() {
         return this.ex;
    }
}
