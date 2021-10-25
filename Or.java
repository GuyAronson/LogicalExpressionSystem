import java.util.Map;

/**
 * A class for an Or expression between 2 variables.
 * @author Guy Aronson.
 */
public class Or extends BinaryExpression implements Expression {

    /**
     * A constructor for the Or expression.
     * @param e1 - The first expression - Left one.
     * @param e2 - The second expression - Right one.
     */
    public Or(Expression e1, Expression e2) {
        super(e1, e2);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {

        //Returning the evaluation of each expression with an 'or' in between.
        return (this.getExpression1().evaluate(assignment) || this.getExpression2().evaluate(assignment));
    }

    @Override
    public Boolean evaluate() throws Exception {

        //Returning the evaluation of each expression with an 'or' in between.
        return (this.getExpression1().evaluate() || this.getExpression2().evaluate());
    }

    @Override
    public Expression assign(String var, Expression expression) {

        //Returning new Or with the changes in the left and right expressions.
        return new Or(this.getExpression1().assign(var, expression), this.getExpression2().assign(var, expression));
    }

    @Override
    public Expression norify() {
        Expression a = this.getExpression1();
        Expression b = this.getExpression2();

        //( A NOR B ) NOR ( A NOR B )
        return new Nor(new Nor(a, b).norify(), new Nor(a, b).norify()).norify();
    }

    @Override
    public Expression nandify() {
        Expression a = this.getExpression1();
        Expression b = this.getExpression2();

        //( A NAND A ) NAND ( B NAND B )
        return new Nand(new Nand(a, a).nandify(), new Nand(b, b).nandify()).nandify();
    }

    @Override
    public Expression simplify() {
        Expression aSimplified = this.getExpression1().simplify();
        Expression bSimplified = this.getExpression2().simplify();

        // The catch block is ignored, as we don't want to throw exception and crush the program.
        try {

            //Checks if the left expression is true - returns true. (T | x) = T
            if (aSimplified.evaluate()) {
                return new Val(true);
            } else {

                //If the left expression is false - returns the right expression. (F | x) = x
                return bSimplified;
            }
        } catch (Exception ignored) {
            ;
        }

        try {

            //Checks if the right expression is true - returns true. (x | T) = T
            if (bSimplified.evaluate()) {
                return new Val(true);
            } else {

                //If the right expression is false - returns the left expression. (x | F) = x
                return aSimplified;
            }
        } catch (Exception ignored) {
            ;
        }

        try {

            //Checks if the expressions are equal - (x | x = x)
            if (aSimplified.toString().equals(bSimplified.toString())) {
                return aSimplified;
            }
        } catch (Exception ignored) {
            ;
        }

        //If no simplification succeed.
        return new Or(aSimplified, bSimplified);
    }

    @Override
    public String toString() {
        return ("(" + this.getExpression1().toString() + " | " + this.getExpression2().toString() + ")");
    }
}