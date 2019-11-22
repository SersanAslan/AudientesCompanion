package com.a2electricboogaloo.audientes.ui.welcome

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.a2electricboogaloo.audientes.MainActivity
import com.a2electricboogaloo.audientes.R


class WelcomeActivity : AppCompatActivity() {
    
    private var nextButton: Button? = null
    private var titleText: TextView? = null
    private var contentText: TextView? = null
    private val REQUEST_ENABLE_BT = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        supportActionBar?.hide()

        nextButton = findViewById<Button>(R.id.button11)
        titleText = findViewById<TextView>(R.id.titleView)
        contentText = findViewById<TextView>(R.id.contentView)

        nextButton?.setOnClickListener {
        activateBT()
        }

        // Register for broadcasts when a device is discovered.
        val filter = IntentFilter (BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
    }



    fun activateBT(){
        titleText?.text = "Loading..."
        contentText?.text = "Connecting to device."

        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter?.isEnabled == false) {
            val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 100)
            }
            startActivity(discoverableIntent)
            println("forbinder til BT")
        }
        if (bluetoothAdapter?.isEnabled == true){
            println("BT er aktiveret")
            val intent = Intent(this, MainActivity::class.java)
            val lambda = { -> startActivity(intent) }
            finish()
            lambda()
        }
    }


        // Create a BroadcastReceiver for ACTION_FOUND.
    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action: String = intent.action
            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device.name
                    val deviceHardwareAddress = device.address // MAC address
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()


        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver)
    }
}
