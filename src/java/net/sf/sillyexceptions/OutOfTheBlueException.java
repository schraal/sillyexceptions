/*
Silly exceptions - A library of funny sounding, but useful Java Exceptions.
Copyright (C) 2004  W.H. Schraal, sillyexceptions.sf.net

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package net.sf.sillyexceptions;

/**
 * This exception should be used at places where you don't expect an exceptional
 * situation to occur.
 * <p>
 * For example, sometimes you <b>have</b> to catch an exception (because an API
 * demands it), while you are absolutely sure that the exception will never
 * occur. In that case you can better:
 * <pre>
 *    throw new OutOfTheBlueException("Properties could not be loaded, although they are always there.");
 * </pre>
 * instead of:
 * <pre>
 *    //This can never happen, since the properties are always there.
 * </pre>
 *
 * @author W.H. Schraal
 * @version $Id: OutOfTheBlueException.java,v 1.3 2004/09/06 16:53:22 hippe Exp $
 * @since 1.0
 */
public class OutOfTheBlueException extends RuntimeException {

  /**
   * Constructs a new OutOfTheBlueException with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   *
   * @param   message   the detail message. The detail message is saved for
   *          later retrieval by the {@link #getMessage()} method.
   */
  public OutOfTheBlueException(String message) {
    super(message);
  }

  /**
   * Constructs a new OutOfTheBlueException with the specified detail message
   * and cause.  <p>Note that the detail message associated with
   * <code>cause</code> is <i>not</i> automatically incorporated in
   * this exception's detail message.
   *
   * @param  message the detail message (which is saved for later retrieval
   *         by the {@link #getMessage()} method).
   * @param  cause the cause (which is saved for later retrieval by the
   *         {@link #getCause()} method).  (A <tt>null</tt> value is
   *         permitted, and indicates that the cause is nonexistent or
   *         unknown.)
   */
  public OutOfTheBlueException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new OutOfTheBlueException with the specified cause and a
   * detail message of <tt>(cause==null ? null : cause.toString())</tt>
   * (which typically contains the class and detail message of
   * <tt>cause</tt>).  This constructor is useful for exceptions
   * that are little more than wrappers for other throwables.
   *
   * @param  cause the cause (which is saved for later retrieval by the
   *         {@link #getCause()} method).  (A <tt>null</tt> value is
   *         permitted, and indicates that the cause is nonexistent or
   *         unknown.)
   */
  public OutOfTheBlueException(Throwable cause) {
    super(cause);
  }

}
