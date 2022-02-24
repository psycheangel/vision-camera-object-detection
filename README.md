# vision-camera-object-detection

## Getting started

`$ npm install @psycheangel/vision-camera-object-detection --save`

### Mostly automatic installation

`$ react-native link @psycheangel/vision-camera-object-detection`
```javascript
module.exports = {
  presets: ['module:metro-react-native-babel-preset'],
  plugins: [
    [
      'react-native-reanimated/plugin',
      {
        globals: [ "__detectObject"],
      },
    ],
  ],
};

```
## Usage
```javascript
import {detectObject} from "vision-camera-object-detection";

// TODO: What to do with the module?
  const frameProcessor = useFrameProcessor((frame) => {
    'worklet';
   
      const values = detectObject(frame);
      console.log(values);
   
  }, []);
```
