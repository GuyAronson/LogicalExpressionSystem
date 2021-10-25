import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class for the Val objects.
 * @author Guy Aronson.
 */
public class Val implements Expression {
    private final boolean value;

    /**
     * A constructor for the Val instances.
     * @param val - the new value assigned to.
     */
    public Val(boolean val) {
        this.value = val;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.value;
    }

    @Override
    public Boolean evaluate() throws Exception {
        return this.value;
    }

    @Override
    public List<String> getVariables() {
        return new ArrayList<>();
    }

    @Override
    public Expression assign(String var, Expression expression) {
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
        if (this.value) {
            return ("T");
        } else {
            return ("F");
        }
    }
}