package net.sourceforge.sillyexceptions;

/**
 * This exception should be used at places where you don't expect an exceptional situation to occur.
 * <p>
 * For example, sometimes you <b>have</b> to catch an exception (because an API demands it), while
 * you are absolutely sure that the exception will never occur. In that case you can better:
 * <pre>
 *    throw new OutOfTheBlueException("Properties could not be loaded, although they are always there.");
 * </pre>
 * instead of:
 * <pre>
 *    //This can never happen, since the properties are always there.
 * </pre>
 *
 * @author W.H. Schraal
 * @version $id:$
 * @since 1.0
 */
public class OutOfTheBlueException extends RuntimeException {
}
