/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.doubango.tinyWRAP;

public class SafeObject {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  public SafeObject(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  public static long getCPtr(SafeObject obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        tinyWRAPJNI.delete_SafeObject(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public SafeObject() {
    this(tinyWRAPJNI.new_SafeObject(), true);
  }

  public int Lock() {
    return tinyWRAPJNI.SafeObject_Lock(swigCPtr, this);
  }

  public int UnLock() {
    return tinyWRAPJNI.SafeObject_UnLock(swigCPtr, this);
  }

}
