import java.util.Map;
/**
 * A class for an Xor expression between 2 variables.
 * @author Guy Aronson.
 */
public class Xor extends BinaryExpression implements Expression {

    /**
     * A constructor for the XOr expression.
     * @param e1 - The first expression - Left one.
     * @param e2 - The second expression - Right one.
     */
    public Xor(Expression e1, Expression e2) {
        super(e1, e2);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {

        //Returning the evaluation of each expression with a 'xor' in between.
        return (this.getExpression1().evaluate(assignment) ^ this.getExpression2().evaluate(assignment));
    }

    @Override
    public Boolean evaluate() throws Exception {

        //Returning the evaluation of each expression with a 'xor' in between.
        return (this.getExpression1().evaluate() ^ this.getExpression2().evaluate());
    }

    @Override
    public Expression assign(String var, Expression expression) {

        //Returning new Xor with the changes in the left and right expressions.
        return new Xor(this.getExpression1().assign(var, expression), this.getExpression2().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        Expression a = this.getExpression1();
        Expression b = this.getExpression2();

        //[ A NAND ( A NAND B ) ] NAND [ B NAND ( A NAND B ) ]
        return new Nand(new Nand(a, new Nand(a, b).nandify()).nandify(),
                new Nand(b, new Nand(a, b).nandify()).nandify()).nandify();
    }

    @Override
    public Expression norify() {
        Expression a = this.getExpression1();
        Expression b = this.getExpression2();

        //[ ( A NOR A ) NOR ( B NOR B ) ] NOR ( A NOR B )
        return new Nor(new Nor(new Nor(a, a).norify(), new Nor(b, b).norify()).norify(),
                new Nor(a, b).norify()).norify();
    }

    @Override
    public Expression simplify() {
        Expression aSimplified = this.getExpression1().simplify();
        Expression bSimplified = this.getExpression2().simplify();

        // The catch block is ignored, as we don't want to throw exception and crush the program.
        try {

            /*Checks if the left expression is true, returns the negation of the right expression
            T ^ x = ~(x) */
            if (aSimplified.evaluate()) {
                return new Not(bSimplified).simplify();
            } else {

                //if the left expression is false, returns the right expression (F ^ x = x)
                return bSimplified;
            }
        } catch (Exception ignored) {
            ;
        }

        try {

            /*Checks if the right expression is true, returns the negation of the left expression
            X ^ T = ~(x) */
            if (bSimplified.evaluate()) {
                return new Not(aSimplified).simplify();
            } else {

                //if the right expression is false, returns the left expression (x ^ F = x)
                return aSimplified;
            }
        } catch (Exception ignored) {
            ;
        }

        try {

            //Checks if the right expression equals to the left expression - returns false. (x ^ x = F)
            if (aSimplified.toString().equals(bSimplified.toString())) {
                return new Val(false);
            }
        } catch (Exception ignored) {
            ;
        }

        //If not simplification succeeded.
        return new Xor(aSimplified, bSimplified);
    }

    @Override
    public String toString() {
        return ("(" + this.getExpression1().toString() + " ^ " + this.getExpression2().toString() + ")");
    }
}