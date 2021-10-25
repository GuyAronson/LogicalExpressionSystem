import java.util.List;
import java.util.Map;
/**
 * An interface for the expressions in this program.
 * @author Guy Aronson.
 */
public interface Expression {

    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.
     * @param assignment - the assignment containing the variables.
     * @return - returns the expression evaluation - (T/F)
     * @throws Exception - If the expression contains a variable which is not in the assignment.
     */
    Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return - returns the expression evaluation
     * @throws Exception If the expression contains a variable which is not in the assignment.
     */
    Boolean evaluate() throws Exception;

    /**
     * @return  a list of the variables in the expression.
     */
    List<String> getVariables();

    /**
     * @return  a nice string representation of the expression.
     */
    String toString();

    /**
     * @param var - given variable to be replaced.
     * @param expression - the expression to replce the variable.
     * @return Returns a new expression in which all occurrences of the variable
     * 'var' are replaced with the provided expression (Does not modify the
     * current expression).
     */
    Expression assign(String var, Expression expression);

    /**
     * @return the expression tree resulting from converting all the operations to the logical Nand
     */
    Expression nandify();

    /**
     * @return the expression tree resulting from converting all the operations to the logical Nor operation.
     */
    Expression norify();

    /**
     * @return Returned a simplified version of the current expression.
     */
    Expression simplify();
}