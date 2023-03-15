package api.pot.system.provider;

public interface ProviderAccelerometerListener {
    void onOrientationChanged(float roll, float pitch, float azimuth);
}
