// VisionCameraObjectDetectionModule.java
package com.visioncameraobjectdetection;

import static com.visioncameraobjectdetection.DetectorConverter.convertLabelList;
import static com.visioncameraobjectdetection.DetectorConverter.convertToMap;

import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import com.google.mlkit.vision.common.InputImage;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.ImageInfo;
import androidx.annotation.NonNull;
import android.media.Image;
import android.annotation.SuppressLint;
import android.util.Log;
import android.graphics.Rect;

import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;

import com.google.mlkit.vision.objects.DetectedObject;

import com.mrousavy.camera.frameprocessor.FrameProcessorPlugin;


import java.util.List;
import java.util.concurrent.ExecutionException;

public class VisionCameraObjectDetectionPlugin extends FrameProcessorPlugin {
 final private ObjectDetectorOptions options =   new ObjectDetectorOptions.Builder()
                .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
                .enableMultipleObjects()
                .enableClassification()  // Optional
                .build();
 final private ObjectDetector objectDetector = ObjectDetection.getClient(options);
  @Override
 public Object callback(ImageProxy frame, Object[] params) {
   
   @SuppressLint("UnsafeOptInUsageError")

    Image mediaImage = frame.getImage();
    ImageInfo imageInfo = frame.getImageInfo();
    if (mediaImage != null) {
      InputImage image = InputImage.fromMediaImage(mediaImage, imageInfo.getRotationDegrees());
      Task<List<DetectedObject>> task = objectDetector.process(image);

      try {
        List<DetectedObject> objects = Tasks.await(task);
        Log.d("XXX", String.valueOf(objects.isEmpty()));

        WritableNativeArray array = new WritableNativeArray();
          for (DetectedObject detector : objects) {
              array.pushMap(convertDetector(detector));
          }
        return array;
      } catch (Exception e) {
        Log.d("XXX", "ERROR");
        e.printStackTrace();
      }
    }
    return null;
  }
  private WritableNativeMap convertDetector(@NonNull DetectedObject detector) {
      WritableNativeMap map = new WritableNativeMap();

      Rect boundingBox = detector.getBoundingBox();
      if (boundingBox != null) {
        map.putMap("boundingBox", convertToMap(boundingBox));
      }

      List<DetectedObject.Label> labels = detector.getLabels();
      if (labels != null) {
        map.putArray("Labels", convertLabelList(labels));
      }

      Integer trackingId = detector.getTrackingId();
      if (trackingId != null) {
        int i = trackingId.intValue();
        map.putInt("trackingId", i);
      }

      map.putInt("hash", detector.hashCode());

      return map;
    }
  VisionCameraObjectDetectionPlugin() {
    super("detectObject");
  }
}
