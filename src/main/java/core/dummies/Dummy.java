package core.dummies;

import java.math.BigInteger;
import java.util.Random;

/**
 * Base class for dummies
 *
 * Dummy implementation must be immutable
 * @param <T>
 */
public abstract class Dummy<T>
{
    abstract public T getEntity();
    abstract public T create();
    abstract public T create(T entity);

    protected String random() {
        return new BigInteger(20, new Random()).toString(10);
    }
}
