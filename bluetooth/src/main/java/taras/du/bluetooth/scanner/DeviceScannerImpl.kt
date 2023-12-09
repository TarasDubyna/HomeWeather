package taras.du.bluetooth.scanner

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import taras.du.bluetooth.model.ScanningState
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Singleton
class DeviceScannerImpl @Inject constructor(@ApplicationContext private val appContext: Context): DeviceScanner {

    private val TAG = "DeviceScanner"

    private val _scannerState: MutableStateFlow<ScanningState> = MutableStateFlow(ScanningState.STOPPED)
    override val scannerState: StateFlow<ScanningState> = _scannerState
    private val _foundDevice: MutableSharedFlow<BluetoothDevice> = MutableSharedFlow()

    private var timeoutDuration: Duration = 20.seconds

    private val callback = object : BluetoothService.OnBluetoothScanCallback {
        @SuppressLint("MissingPermission")
        override fun onDeviceDiscovered(device: BluetoothDevice?, rssi: Int) {
            _scannerState.tryEmit(ScanningState.IN_PROCESS)
            device?.let {
                Log.d(TAG, "FOUND DEVICE: ${device.name} ${device.address}")
            }
        }

        override fun onStartScan() {
            Log.d(TAG, "SCANNING STATE: STARTED")
            _scannerState.tryEmit(ScanningState.STARTED)
        }

        override fun onStopScan() {
            Log.d(TAG, "SCANNING STATE: STOPPED")
            timerJob.cancel()
            _scannerState.tryEmit(ScanningState.STOPPED)
        }

    }



    private val timerJob: Job = CoroutineScope(Dispatchers.Default).launch {
        delay(timeoutDuration)
        if (_scannerState.value != ScanningState.STOPPED) {
            Log.d(TAG, "SCANNING DEVICES TIMEOUT: $timeoutDuration")
            BluetoothService.getDefaultInstance().stopScan()
        }
    }

    override fun startScanning(timeout: Duration): SharedFlow<BluetoothDevice> {
        BluetoothService.getDefaultInstance().setOnScanCallback(callback)
        BluetoothService.getDefaultInstance().startScan()
        timerJob.start()
        return _foundDevice.shareIn(scope = CoroutineScope(Dispatchers.Default), started = SharingStarted.WhileSubscribed())
    }

    override fun stopScanning() {
        BluetoothService.getDefaultInstance().stopScan()
    }

}
