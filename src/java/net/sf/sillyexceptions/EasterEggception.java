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
 * This exception can be used when programming easter eggs. Easter eggs
 * should normally not produce exceptions but just in case you really
 * need to throw one, the EasterEggception is your new best friend :).
 * <p>
 *
 * @author Age Mooy (<a href="mailto:age.mooy@gmail.com">age.mooy@gmail.com</a>)
 * @version $Id: EasterEggception.java,v 1.1 2004/11/16 12:24:27 amooy Exp $
 * @since 1.0.2
 */
public class EasterEggception extends RuntimeException {

  /**
   * Constructs a new EasterEggception with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   *
   * @param   message   the detail message. The detail message is saved for
   *          later retrieval by the {@link #getMessage()} method.
   */
  public EasterEggception(String message) {
    super(message);
  }

  /**
   * Constructs a new EasterEggception with the specified detail message
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
  public EasterEggception(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new EasterEggception with the specified cause and a
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
  public EasterEggception(Throwable cause) {
    super(cause);
  }

}
