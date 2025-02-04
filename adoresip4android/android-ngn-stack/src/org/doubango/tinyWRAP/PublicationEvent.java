/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.doubango.tinyWRAP;

public class PublicationEvent extends SipEvent {
  private long swigCPtr;

  public PublicationEvent(long cPtr, boolean cMemoryOwn) {
    super(tinyWRAPJNI.PublicationEvent_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  public static long getCPtr(PublicationEvent obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        tinyWRAPJNI.delete_PublicationEvent(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public tsip_publish_event_type_t getType() {
    return tsip_publish_event_type_t.swigToEnum(tinyWRAPJNI.PublicationEvent_getType(swigCPtr, this));
  }

  public PublicationSession getSession() {
    long cPtr = tinyWRAPJNI.PublicationEvent_getSession(swigCPtr, this);
    return (cPtr == 0) ? null : new PublicationSession(cPtr, false);
  }

}
