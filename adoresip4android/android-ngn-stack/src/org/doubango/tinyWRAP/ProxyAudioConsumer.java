/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.doubango.tinyWRAP;

public class ProxyAudioConsumer extends ProxyPlugin {
  private long swigCPtr;

  public ProxyAudioConsumer(long cPtr, boolean cMemoryOwn) {
    super(tinyWRAPJNI.ProxyAudioConsumer_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  public static long getCPtr(ProxyAudioConsumer obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        tinyWRAPJNI.delete_ProxyAudioConsumer(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public boolean queryForResampler(int nInFreq, int nOutFreq, int nFrameDuration, int nChannels, int nResamplerQuality) {
    return tinyWRAPJNI.ProxyAudioConsumer_queryForResampler(swigCPtr, this, nInFreq, nOutFreq, nFrameDuration, nChannels, nResamplerQuality);
  }

  public boolean setPullBuffer(java.nio.ByteBuffer pPullBufferPtr, long nPullBufferSize) {
    return tinyWRAPJNI.ProxyAudioConsumer_setPullBuffer(swigCPtr, this, pPullBufferPtr, nPullBufferSize);
  }

  public long pull(java.nio.ByteBuffer pOutput, long nSize) {
    return tinyWRAPJNI.ProxyAudioConsumer_pull__SWIG_0(swigCPtr, this, pOutput, nSize);
  }

  public long pull(java.nio.ByteBuffer pOutput) {
    return tinyWRAPJNI.ProxyAudioConsumer_pull__SWIG_1(swigCPtr, this, pOutput);
  }

  public long pull() {
    return tinyWRAPJNI.ProxyAudioConsumer_pull__SWIG_2(swigCPtr, this);
  }

  public boolean setGain(long nGain) {
    return tinyWRAPJNI.ProxyAudioConsumer_setGain(swigCPtr, this, nGain);
  }

  public long getGain() {
    return tinyWRAPJNI.ProxyAudioConsumer_getGain(swigCPtr, this);
  }

  public boolean reset() {
    return tinyWRAPJNI.ProxyAudioConsumer_reset(swigCPtr, this);
  }

  public void setCallback(ProxyAudioConsumerCallback pCallback) {
    tinyWRAPJNI.ProxyAudioConsumer_setCallback(swigCPtr, this, ProxyAudioConsumerCallback.getCPtr(pCallback), pCallback);
  }

  public java.math.BigInteger getMediaSessionId() {
    return tinyWRAPJNI.ProxyAudioConsumer_getMediaSessionId(swigCPtr, this);
  }

  public static boolean registerPlugin() {
    return tinyWRAPJNI.ProxyAudioConsumer_registerPlugin();
  }

}
