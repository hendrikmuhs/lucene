package org.apache.lucene.util.keyvi;

/** Interface for an entry in the minimization hash. */
public interface MinimizationHashEntry {

  /** A key in the minimization hash. */
  public interface Key {

    void set(int offset, int hashcode, int numberOutgoingStatesAndCookie);

    int getExtra();

    int recalculateExtra(int extra, int newCookie);

    int getCookie();

    void setCookie(int value);

    int getOffset();

    boolean isEmpty();
  }

  int getCookie(int numberOutgoingStatesAndCookie);

  int getMaxCookieSize();
}
