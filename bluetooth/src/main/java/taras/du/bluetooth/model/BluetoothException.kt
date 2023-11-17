package taras.du.bluetooth.model

open class BluetoothException(message: String): Exception(message)

class BluetoothNotEnabledException: BluetoothException("Bluetooth not enabled")
class BluetoothPermissionNotGrantedException: BluetoothException("Permission not granted")
class BluetoothDeviceNotConnectedException: BluetoothException("Device not connected")
class BluetoothOtherException(message: String): BluetoothException(message)

