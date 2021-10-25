import java.util.Map;

/**
 * A class for an AND expression between 2 variables.
 * @author Guy Aronson
 */
public class And extends BinaryExpression implements Expression {

    /**
     * A constructor for an and Expression.
     * @param e1 - gets the left expression.
     * @param e2 - the Right expression.
     */
    public And(Expression e1, Expression e2) {
        super(e1, e2);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {

        //Returning the evaluation of each expression with an 'and' in the middle.
        return (this.getExpression1().evaluate(assignment) && this.getExpression2().evaluate(assignment));
    }

    @Override
    public Boolean evaluate() throws Exception {

        //Returning the evaluation of each expression with an 'and' in the middle.
        return (this.getExpression1().evaluate() && this.getExpression2().evaluate());
    }

    @Override
    public Expression assign(String var, Expression expression) {

        //Returning new And with the changes in the left and right expressions.
        return new And(this.getExpression1().assign(var, expression), this.getExpression2().assign(var, expression));
    }

    @Override
    public Expression nandify() {
        Expression a = this.getExpression1();
        Expression b = this.getExpression2();

        // ((A NAND B) NAND (A NAND B))
        return new Nand(new Nand(a, b).nandify(), new Nand(a, b).nandify()).nandify();
    }

    @Override
    public Expression norify() {
        Expression a = this.getExpression1();
        Expression b = this.getExpression2();

        //(A NOR A) NOR (B NOR B))
        return new Nor(new Nor(a, a).norify(), new Nor(b, b).norify()).norify();

    }

    @Override
    public Expression simplify() {
        Expression aSimplified = this.getExpression1().simplify();
        Expression bSimplified = this.getExpression2().simplify();

        // The catch block is ignored, as we don't want to throw exception and crush the program.
        try {

            //Checks if the left expression is true - returns the right expression. (T & x) = x
            if (aSimplified.evaluate()) {
                return bSimplified;
            } else {

                //If the left expression is false - returns false. (F & x) = F
                return new Val(false);
            }
        } catch (Exception ignored) {
            ;
        }

        try {

            //Checks if the right expression is true - returns the left expression. (x & T) = x
            if (bSimplified.evaluate()) {
                return aSimplified;
            } else {

                //If the right expression is false - returns false. (x & F) = F
                return new Val(false);
            }
        } catch (Exception ignored) {
            ;
        }

        try {

            //Checks if the expressions are equal - (x & x = x)
            if (aSimplified.toString().equals(bSimplified.toString())) {
                return aSimplified;
            }
        } catch (Exception ignored) {
            ;
        }

        //If no simplification succeed.
        return new And(aSimplified, bSimplified);
    }

    @Override
    public String toString() {
        return ("(" + this.getExpression1().toString() + " & " + this.getExpression2().toString() + ")");
    }
}