# vision-camera-object-detection.podspec

require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "vision-camera-object-detection"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  vision-camera-object-detection
                   DESC
  s.homepage     = "https://github.com/psycheangel/vision-camera-object-detection"
  # brief license entry:
  s.license      = "MIT"
  # optional - use expanded license entry instead:
  # s.license    = { :type => "MIT", :file => "LICENSE" }
  s.authors      = { "Dwinanto Saputra" => "mozymanx@gmail.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/psycheangel/vision-camera-object-detection.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,c,cc,cpp,m,mm,swift}"
  s.requires_arc = true

  s.dependency "React-Core"
  s.dependency "GoogleMLKit/ObjectDetection", "2.6.0"
  # ...
  # s.dependency "..."
end

