import Vision
import MLKitObjectDetection
import MLKitVision
import AVFoundation

@objc(VisionCameraCodeScanner)
class VisionCameraCodeScanner: NSObject, FrameProcessorPluginBase {
     static var ObjectDetectorOption: CommonObjectDetectorOptions = {
        let option = ObjectDetectorOptions()
        option.detectorMode  = .stream
        option.shouldEnableMultipleObjects  = true
        option.shouldEnableClassification  = true
        return option
    }()
    
    static var objectDetector  = ObjectDetector.objectDetector(options: ObjectDetectorOption)
    
    @objc
    public static func callback(_ frame: Frame!, withArgs args: [Any]!) -> Any! {
        let image = VisionImage(buffer: frame.buffer)
        image.orientation = .up
        var detectorAttributes: [Any] = []
        do {
            let detector: [Object] = try objectDetector.results(in: image)
            if (!detector.isEmpty){
                for detect in detector {
                    detectorAttributes.append(self.convertObject(detect: detect))
                }
            }
        } catch _ {
            return nil
        }
        
        return detectorAttributes
    }
    
    private static func processBoundingBox(from detect: Object) -> [String:Any] {
        let frameRect = detect.frame

        let offsetX = (frameRect.midX - ceil(frameRect.width)) / 2.0
        let offsetY = (frameRect.midY - ceil(frameRect.height)) / 2.0

        let x = frameRect.maxX + offsetX
        let y = frameRect.minY + offsetY

        return [
          "x": frameRect.midX + (frameRect.midX - x),
          "y": frameRect.midY + (y - frameRect.midY),
          "width": frameRect.width,
          "height": frameRect.height,
          "boundingCenterX": frameRect.midX,
          "boundingCenterY": frameRect.midY
        ]
    }
    
    static func convertObject(detect: Object) -> Any {
        var map: [String: Any] = [:]
  
        map["Labels"] = DetectorConverter.convertToArray(labels: detect.labels as? [ObjectLabel])
        map["boundingBox"] = processBoundingBox(from: detect)
        map["trackingId"] = detect.trackingID
         
        return map
    }
}