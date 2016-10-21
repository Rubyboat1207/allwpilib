/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.cameraserver;

import org.opencv.core.Mat;

/// A source that represents a video camera.
public class CvSource extends VideoSource {
  /// Create an OpenCV source.
  /// @param name Source name (arbitrary unique identifier)
  /// @param mode Video mode being generated
  public CvSource(String name, VideoMode mode) {
    super(CameraServerJNI.createCvSource(name, mode.pixelFormat.getValue(), mode.width, mode.height, mode.fps));
  }

  /// Create an OpenCV source.
  /// @param name Source name (arbitrary unique identifier)
  /// @param pixelFormat Pixel format
  /// @param width width
  /// @param height height
  /// @param fps fps
  public CvSource(String name, VideoMode.PixelFormat pixelFormat, int width, int height, int fps) {
    super(CameraServerJNI.createCvSource(name, pixelFormat.getValue(), width, height, fps));
  }

  /// Put an OpenCV image and notify sinks.
  /// @param image OpenCV image
  public void putFrame(Mat image) {
    CameraServerJNI.putSourceFrame(m_handle, image.nativeObj);
  }

  /// Signal sinks that an error has occurred.  This should be called instead
  /// of NotifyFrame when an error occurs.
  public void notifyError(String msg) {
    CameraServerJNI.notifySourceError(m_handle, msg);
  }

  /// Set source connection status.  Defaults to true.
  /// @param connected True for connected, false for disconnected
  public void setConnected(boolean connected) {
    CameraServerJNI.setSourceConnected(m_handle, connected);
  }

  /// Create a property.
  /// @param name Property name
  /// @param type Property type
  /// @return Property
  public VideoProperty createProperty(String name, VideoProperty.Type type) {
    return new VideoProperty(
        CameraServerJNI.createSourceProperty(m_handle, name, type.getValue()));
  }

  /// Create a property with a change callback.
  /// @param name Property name
  /// @param type Property type
  /// @param onChange Callback to call when the property value changes
  /// @return Property
  //public VideoProperty createProperty(
  //    String name, VideoProperty.Type type,
  //    std::function<void(String name, VideoProperty property)>
  //        onChange);

  /// Remove a property.
  /// @param name Property name
  public void removeProperty(VideoProperty property) {
    CameraServerJNI.removeSourceProperty(m_handle, property.m_handle);
  }

  /// Remove a property.
  /// @param name Property name
  public void removeProperty(String name) {
    CameraServerJNI.removeSourcePropertyByName(m_handle, name);
  }
}
