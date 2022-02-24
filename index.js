/* globals __surfaceMesh */
import { NativeEventEmitter, NativeModules, Platform } from 'react-native';


/**
 * Returns an array of matching `ImageLabel`s for the given frame.
 *
 * This algorithm executes within **~60ms**, so a frameRate of **16 FPS** perfectly allows the algorithm to run without dropping a frame. Anything higher might make video recording stutter, but works too.
 */

export function detectObject(frame){
  'worklet';
  // @ts-expect-error Frame Processors are not typed.
  return __detectObject(frame);
}