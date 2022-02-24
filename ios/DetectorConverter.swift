import MLKitObjectDetection

class DetectorConverter {
    public static func convertToArray(labels: [ObjectLabel]?) -> Any! {
        return labels?.map(convertToMap(label:))
    }

    public static func convertToMap(label: ObjectLabel?) -> Any! {
        var map: [String: Any] = [:]

        map["index"] = label?.index
        map["text"] = label?.text
        map["confidence"] = label?.confidence

        return map;
    }


}