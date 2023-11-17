package taras.du.bluetooth

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object BluetoothUtil {

    val BLUETOOTH_PERMISSIONS = mutableListOf(
        "android.permission.BLUETOOTH",
        "android.permission.BLUETOOTH_ADMIN",
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.BLUETOOTH_CONNECT",
        "android.permission.BLUETOOTH_SCAN")

    fun isBluetoothEnabled(context: Context): Boolean {
        val bluetoothManager = context.getSystemService<BluetoothManager>(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
        return bluetoothAdapter?.isEnabled ?: false
    }

    fun isPermissionsGranted(context: Context): Boolean {
        return BLUETOOTH_PERMISSIONS.all { ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }
    }

    fun requestPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(activity, BLUETOOTH_PERMISSIONS.toTypedArray(), 100)
    }

}

@SuppressLint("MissingPermission")
fun BluetoothDevice.getInfo(): String {
    return StringBuilder()
        .append(this.name).append(" ")
        .append(this.address).append(" ")
        .append("bondState=").append(this.bondState).append(" ")
        .toString()
}
