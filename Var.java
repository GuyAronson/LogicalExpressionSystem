import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class for the variables in the expressions.
 */
public class Var implements Expression {
    private final String variable;

    /**
     * Constructor for the var class.
     * @param newVar -  the new variable gets in.
     */
    public Var(String newVar) {
        variable = newVar;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        if (assignment.containsKey(this.variable)) {
            return assignment.get(this.variable);
        } else {
            throw new Exception("A variable is missing in the assignment.");
        }
    }

    @Override
    public Boolean evaluate() throws Exception {
        throw new Exception("There is no map..");
    }

    @Override
    public List<String> getVariables() {
        List<String> vars = new ArrayList<>();
        vars.add(this.variable);
        return vars;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        if (var.equals(this.variable)) {
            return expression;
        }
        return this;
    }

    @Override
    public Expression norify() {
        return this;
    }

    @Override
    public Expression nandify() {
        return this;
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public String toString() {
        return variable;
    }
}