import java.util.Map;

/**
 * A class for an Nor expression between 2 variables.
 * @author Guy Aronson.
 */
public class Nor extends BinaryExpression implements Expression {

    /**
     * A constructor for the Nor expression.
     * @param e1 - The first expression - Left one.
     * @param e2 - The second expression - Right one.
     */
    public Nor(Expression e1, Expression e2) {
        super(e1, e2);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {

        //Returning the Negation of the expression's evaluation with an 'or' in between.
        return !(this.getExpression1().evaluate(assignment) || this.getExpression2().evaluate(assignment));
    }

    @Override
    public Boolean evaluate() throws Exception {

        //Returning the Negation of the expression's evaluation with an 'or' in between.
        return !(this.getExpression1().evaluate() || this.getExpression2().evaluate());
    }

    @Override
    public Expression assign(String var, Expression expression) {

        //Returning new Nor with the changes in the left and right expressions.
        return new Nor(this.getExpression1().assign(var, expression),
                this.getExpression2().assign(var, expression));
    }

    @Override
    public Expression norify() {
        Expression a = this.getExpression1();
        Expression b = this.getExpression2();

        return new Nor(a.norify(), b.norify());
    }

    @Override
    public Expression nandify() {
        Expression a = this.getExpression1();
        Expression b = this.getExpression2();

        //[ ( A NAND A ) NAND ( B NAND B ) ] NAND [ ( A NAND A ) NAND ( B NAND B )]
        return new Nand(new Nand(new Nand(a, a).nandify(), new Nand(b, b).nandify()).nandify(),
                new Nand(new Nand(a, a).nandify(), new Nand(b, b).nandify()).nandify()).nandify();
    }

    @Override
    public Expression simplify() {
        Expression aSimplified = this.getExpression1().simplify();
        Expression bSimplified = this.getExpression2().simplify();

        // The catch block is ignored, as we don't want to throw exception and crush the program.
        try {

            //Checks if the left expression if true, returns false (T V x = F)
            if (aSimplified.evaluate()) {
                return new Val(false);
            } else {

                /*if the left expression is false, returns the negation of the right expression.
                F V x = ~(x) */
                return new Not(bSimplified).simplify();
            }
        } catch (Exception ignored) {
            ;
        }

        try {

            //Checks if the right expression if true, returns false (x V T = F)
            if (bSimplified.evaluate()) {
                return new Val(false);
            } else {

                /*if the right expression is false, returns the negation of the left expression.
                x V F = ~(x) */
                return new Not(aSimplified).simplify();
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
        return new Nor(aSimplified, bSimplified);
    }

    @Override
    public String toString() {
        return ("(" + this.getExpression1().toString() + " V " + this.getExpression2().toString() + ")");
    }
}