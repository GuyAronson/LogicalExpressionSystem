import java.util.Map;

/**
 * A class for a Not expression.
 * @author Guy Aronson.
 */
public class Not extends UnaryExpression implements Expression {

    /**
     * A constructor for the Not expression.
     * @param e - The expression to negate.
     */
    public Not(Expression e) {
        super(e);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {

        //Returning the negation of the expression's evaluation.
        return !(this.getExpression().evaluate(assignment));
    }

    @Override
    public Boolean evaluate() throws Exception {

        //Returning the negation of the expression's evaluation.
        return !(this.getExpression().evaluate());
    }

    @Override
    public Expression assign(String var, Expression expression) {

        //Returning new Not with the changes in the expressions.
        return new Not(this.getExpression().assign(var, expression));
    }

    @Override
    public Expression norify() {
        Expression a = this.getExpression();
        // A NOR A
        return new Nor(a, a).norify();
    }

    @Override
    public Expression nandify() {
        Expression a = this.getExpression();
        //A NAND A
        return new Nand(a, a).nandify();
    }

    @Override
    public Expression simplify() {
        Expression eSimplified = this.getExpression().simplify();

        if (eSimplified.toString().equals("T")) {
            return new Val(false);
        } else if (eSimplified.toString().equals("F")) {
            return new Val(true);
        } else {
            return new Not(eSimplified);
        }
    }

    @Override
    public String toString() {
        return ("~(" + this.getExpression().toString() + ")");
    }
}