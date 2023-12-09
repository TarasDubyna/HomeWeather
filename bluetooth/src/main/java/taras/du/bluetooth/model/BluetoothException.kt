package taras.du.bluetooth.model

open class BluetoothException(message: String): Exception(message)

class BluetoothNotEnabledException: BluetoothException("Bluetooth not enabled")
class BluetoothPermissionNotGrantedException: BluetoothException("Permission not granted")
class BluetoothDeviceNotConnectedException: BluetoothException("Device not connected")
class LocationNotEnabledException: BluetoothException("Device not connected")
class ScanningTimeoutException(timeout: Int): BluetoothException("Scanning device timeout - $timeout sec")
class BluetoothOtherException(message: String): BluetoothException(message)

