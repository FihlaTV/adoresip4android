/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.doubango.tinyWRAP;

public class MsrpEvent {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  public MsrpEvent(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  public static long getCPtr(MsrpEvent obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        tinyWRAPJNI.delete_MsrpEvent(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public tmsrp_event_type_t getType() {
    return tmsrp_event_type_t.swigToEnum(tinyWRAPJNI.MsrpEvent_getType(swigCPtr, this));
  }

  public MsrpSession getSipSession() {
    long cPtr = tinyWRAPJNI.MsrpEvent_getSipSession(swigCPtr, this);
    return (cPtr == 0) ? null : new MsrpSession(cPtr, false);
  }

  public MsrpMessage getMessage() {
    long cPtr = tinyWRAPJNI.MsrpEvent_getMessage(swigCPtr, this);
    return (cPtr == 0) ? null : new MsrpMessage(cPtr, false);
  }

}
