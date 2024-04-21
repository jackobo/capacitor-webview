import Foundation

@objc public class CapacitorWebview: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
