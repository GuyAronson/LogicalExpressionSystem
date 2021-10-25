import java.util.Map;

/**
 * A class for an Xnor expression between 2 variables.
 * @author Guy Aronson.
 */
public class Xnor extends BinaryExpression implements Expression {


    /**
     * A constructor for the Xnor expression.
     * @param e1 - The first expression - Left one.
     * @param e2 - The second expression - Right one.
     */
    public Xnor(Expression e1, Expression e2) {
        super(e1, e2);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {

        /* Returns true if both of the expressions evaluation are true or false together.
        * False otherwise.*/
        return (this.getExpression1().evaluate(assignment) == this.getExpression2().evaluate(assignment));
    }

    @Override
    public Boolean evaluate() throws Exception {

        /* Returns true if both of the expressions evaluation are true or false together.
         * False otherwise.*/
        return (this.getExpression1().evaluate() == this.getExpression2().evaluate());
    }

    @Override
    public Expression assign(String var, Expression expression) {

        //Returning new Xnor with the changes in the left and right expressions.
        return new Xnor(this.getExpression1().assign(var, expression), this.getExpression2().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        Expression a = this.getExpression1();
        Expression b = this.getExpression2();

        //[ ( A NAND A ) NAND ( B NAND B ) ] NAND ( A NAND B )
        return new Nand(new Nand(new Nand(a, a).nandify(), new Nand(b, b).nandify()).nandify(),
                new Nand(a, b).nandify()).nandify();
    }

    @Override
    public Expression norify() {
        Expression a = this.getExpression1();
        Expression b = this.getExpression2();

        //[ A NOR ( A NOR B ) ] NOR [ B NOR ( A NOR B ) ]
        return new Nor(new Nor(a, new Nor(a, b).norify()).norify(),
                new Nor(b, new Nor(a, b).norify()).norify()).norify();
    }

    @Override
    public Expression simplify() {
        Expression aSimplified = this.getExpression1().simplify();
        Expression bSimplified = this.getExpression2().simplify();

        // The catch block is ignored, as we don't want to throw exception and crush the program.
        try {

            //Checks if the expressions are equal - returns true if they do (x # x = T)
            if (aSimplified.toString().equals(bSimplified.toString())) {
                return new Val(true);
            }
        } catch (Exception ignored) {
            ;
        }

        //Check if one of the expression is false and the other one is true:
        try {

            //Checks if the left expression is true AND the right expression is false - F # T = F
            if (aSimplified.evaluate() && !bSimplified.evaluate()) {
                return new Val(false);
            }
        } catch (Exception ignored) {
            ;
        }

        try {

            //Checks if the right expression is true AND the left expression is false - T # F = F
            if (bSimplified.evaluate() && !aSimplified.evaluate()) {
                return new Val(false);
            }
        } catch (Exception ignored) {
            ;
        }

        //If the simplification has not succeed
        return new Xnor(aSimplified, bSimplified);
    }

    @Override
    public String toString() {
        return ("(" + this.getExpression1().toString() + " # " + this.getExpression2().toString() + ")");
    }
}