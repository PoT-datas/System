
package api.pot.system.provider.orientation;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.view.Surface;
import android.view.WindowManager;

import api.pot.system.provider.ProviderAccelerometerListener;

public class Orientation implements SensorEventListener {

  private static final int SENSOR_DELAY_MICROS = 16 * 1000; // 16ms

  private final WindowManager mWindowManager;

  private final SensorManager mSensorManager;

  @Nullable
  private final Sensor mRotationSensor;

  private int mLastAccuracy;
  private ProviderAccelerometerListener pAccelerometerListener;

  public Orientation(Activity activity) {
    mWindowManager = activity.getWindow().getWindowManager();
    mSensorManager = (SensorManager) activity.getSystemService(Activity.SENSOR_SERVICE);

    // Can be null if the sensor hardware is not available
    mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
  }

  public void startListening(ProviderAccelerometerListener pAccelerometerListener) {
    if (this.pAccelerometerListener == pAccelerometerListener) {
      return;
    }
      this.pAccelerometerListener = pAccelerometerListener;
    if (mRotationSensor == null) {
      LogUtil.w("Rotation vector sensor not available; will not provide orientation data.");
      return;
    }
    mSensorManager.registerListener(this, mRotationSensor, SENSOR_DELAY_MICROS);
  }

  public void stopListening() {
    mSensorManager.unregisterListener(this);
    this.pAccelerometerListener = null;
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    if (mLastAccuracy != accuracy) {
      mLastAccuracy = accuracy;
    }
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    if (this.pAccelerometerListener == null) {
      return;
    }
    if (mLastAccuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
      return;
    }
    if (event.sensor == mRotationSensor) {
      updateOrientation(event.values);
    }
  }

  @SuppressWarnings("SuspiciousNameCombination")
  private void updateOrientation(float[] rotationVector) {
    float[] rotationMatrix = new float[9];
    SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector);

    final int worldAxisForDeviceAxisX;
    final int worldAxisForDeviceAxisY;

    // Remap the axes as if the device screen was the instrument panel,
    // and adjust the rotation matrix for the device orientation.
    switch (mWindowManager.getDefaultDisplay().getRotation()) {
      case Surface.ROTATION_0:
      default:
        worldAxisForDeviceAxisX = SensorManager.AXIS_X;
        worldAxisForDeviceAxisY = SensorManager.AXIS_Z;
        break;
      case Surface.ROTATION_90:
        worldAxisForDeviceAxisX = SensorManager.AXIS_Z;
        worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_X;
        break;
      case Surface.ROTATION_180:
        worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_X;
        worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_Z;
        break;
      case Surface.ROTATION_270:
        worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_Z;
        worldAxisForDeviceAxisY = SensorManager.AXIS_X;
        break;
    }

    float[] adjustedRotationMatrix = new float[9];
    SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisForDeviceAxisX,
        worldAxisForDeviceAxisY, adjustedRotationMatrix);

    // Transform rotation matrix into azimuth/pitch/roll
    float[] orientation = new float[3];
    SensorManager.getOrientation(adjustedRotationMatrix, orientation);

    /// Convert radians to degrees
    /*float roll = (float) Math.toDegrees(orientation[0]);
    float pitch = (float) Math.toDegrees(orientation[1]);
    float azimuth = (float) Math.toDegrees(orientation[2]);*/
    //
    float roll = orientation[0] * -57;
    float pitch = orientation[1] * -57;
    float azimuth = orientation[2] * -57;

    this.pAccelerometerListener.onOrientationChanged(roll, pitch, azimuth);
  }
}
