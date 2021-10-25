import java.util.List;
import java.util.Map;

/**
 * A class for base expressions.
 * @author Guy Aronson.
 */
public abstract class BaseExpression implements Expression {

    @Override
    public abstract List<String> getVariables();

    @Override
    public abstract Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    @Override
    public abstract Boolean evaluate() throws Exception;

    @Override
    public abstract Expression assign(String var, Expression expression);

    @Override
    public abstract String toString();
}
