import java.util.Map;

/**
 * A class for an NAND expression between 2 variables.
 * @author Guy Aronson
 */
public class Nand extends BinaryExpression implements Expression {

    /**
     * A constructor for an Nand Expression.
     * @param e1 - gets the left expression.
     * @param e2 - the Right expression.
     */
    public Nand(Expression e1, Expression e2) {
        super(e1, e2);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {

        //Returning the negation of an 'and' evaluation.
        return !(this.getExpression1().evaluate(assignment) && this.getExpression2().evaluate(assignment));
    }

    @Override
    public Boolean evaluate() throws Exception {

        //Returning the negation of an 'and' evaluation.
        return !(this.getExpression1().evaluate() && this.getExpression2().evaluate());
    }

    @Override
    public Expression assign(String var, Expression expression) {

        //Returning new Nand with the changes in the left and right expressions.
        return new Nand(this.getExpression1().assign(var, expression), this.getExpression2().assign(var, expression));
    }

    @Override
    public Expression norify() {
        Expression a = this.getExpression1();
        Expression b = this.getExpression2();

        // [( A NOR A ) NOR ( B NOR B ) ] NOR [ ( A NOR A ) NOR ( B NOR B ) ]
        return new Nor(new Nor(new Nor(a, a).norify(), new Nor(b, b).norify()).norify(),
                new Nor(new Nor(a, a).norify(), new Nor(b, b).norify()).norify()).norify();
    }

    @Override
    public Expression nandify() {
        return new Nand(this.getExpression1().nandify(), this.getExpression2().nandify());
    }

    @Override
    public Expression simplify() {
        Expression aSimplified = this.getExpression1().simplify();
        Expression bSimplified = this.getExpression2().simplify();

        // The catch block is ignored, as we don't want to throw exception and crush the program.
        try {

            /*Checks if the left expression is true, returns the negation of the right expression.
            T A x = ~(x) */
            if (aSimplified.evaluate()) {
                return new Not(bSimplified).simplify();
            } else {

                //If the left expression is false - returns true (F A x = T)
                return new Val(true);
            }
        } catch (Exception ignored) {
            ;
        }

        try {

            /*Checks if the right expression is true, returns the negation of the left expression.
            x A T = ~(x) */
            if (bSimplified.evaluate()) {
                return new Not(aSimplified).simplify();
            } else {

                //If the right expression is false - returns true (x A F = T)
                return new Val(true);
            }
        } catch (Exception ignored) {
            ;
        }

        try {

            //Checks if the expressions are equal - returns the negation of one of them.
            if (aSimplified.toString().equals(bSimplified.toString())) {
               return new Not(aSimplified).simplify();
            }
        } catch (Exception ignored) {
            ;
        }

        //If no simplification succeeded.
        return new Nand(aSimplified, bSimplified);
    }

    @Override
    public String toString() {
        return ("(" + this.getExpression1().toString() + " A " + this.getExpression2().toString() + ")");
    }
}