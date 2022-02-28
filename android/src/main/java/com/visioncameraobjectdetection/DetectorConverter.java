package com.visioncameraobjectdetection;

import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.google.mlkit.vision.objects.DetectedObject;

import java.util.List;

/**
 * Converter util class used to convert Barcode related variables to either WritableNativeArray or
 * WritableNativeMap
 */
public class DetectorConverter {
 
  public static WritableNativeArray convertLabelList(@NonNull List<DetectedObject.Label> labels) {
    WritableNativeArray array = new WritableNativeArray();

    for (DetectedObject.Label label: labels) {
      array.pushMap(convertToMap(label));
    }

    return array;
  }

  public static WritableNativeMap convertToMap(@NonNull DetectedObject.Label label) {
    WritableNativeMap map = new WritableNativeMap();

    map.putInt("index", label.getIndex());
    map.putString("text", label.getText());
    map.putDouble("confidence", label.getConfidence());
    return map;
  }

  public static WritableNativeMap convertToMap(@NonNull Rect rect) {
    WritableNativeMap map = new WritableNativeMap();

    map.putInt("bottom", rect.bottom);
    map.putInt("left", rect.left);
    map.putInt("right", rect.right);
    map.putInt("top", rect.top);
    map.putInt("width", rect.width());
    map.putInt("height", rect.height());
    return map;
  }

}