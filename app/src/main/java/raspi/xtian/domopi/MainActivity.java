package raspi.xtian.domopi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ToggleButton;

import raspi.xtian.domopi.gpio.GPIO;
import raspi.xtian.domopi.gpio.GPIOStatus;
import raspi.xtian.domopi.gpio.PORTFUNCTION;


public class MainActivity extends Activity implements GPIO.PortUpdateListener,GPIO.ConnectionEventListener {

    private GPIO gpioPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.gpioPort = new GPIO(new GPIO.ConnectionInfo("192.168.0.102", 8000,
                "pi", ""));

        final CheckBox cb = (CheckBox) findViewById(R.id.chkIsInput);

        cb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(cb.isChecked()) {
                    gpioPort.setFunction(17, PORTFUNCTION.OUTPUT);
                } else {
                    gpioPort.setFunction(17, PORTFUNCTION.INPUT);
                }
            }
        });

        final ToggleButton tb = (ToggleButton) findViewById(R.id.btnPort);

        tb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Only change port value if the port is an "output"
                if(!cb.isChecked()) {
                    if(!tb.isChecked()) {
                        gpioPort.setValue(17, 0);
                    } else {
                        gpioPort.setValue(17, 1);
                    }
                }
            }
        });

        this.gpioPort.addPortUpdateListener(this);
        (new Thread(this.gpioPort)).start();
    }

    @Override
         public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onPortUpdated(final GPIOStatus stat) {
        runOnUiThread(new Runnable() {
            public void run() {
                // First check if the port is configured
                // as an input or output
                if (stat.ports.get(17).function == PORTFUNCTION.INPUT) {

                    // Check the checkbox
                    ((CheckBox) findViewById(R.id.chkIsInput)).setChecked(true);


                    // If is an Input disable the button
                    ((ToggleButton) findViewById(R.id.btnPort)).setEnabled(false);

                    // Set the checked state based on the current port value
                    ((ToggleButton) findViewById(R.id.btnPort)).setChecked(stat.ports.get(17).value.toBool());
                } else if (stat.ports.get(17).function == PORTFUNCTION.OUTPUT) {

                    // Un-check the checkbox
                    ((CheckBox) findViewById(R.id.chkIsInput)).setChecked(false);


                    // If is an Output enable the button
                    ((ToggleButton) findViewById(R.id.btnPort)).setEnabled(true);

                    // Set the checked state based on the current port value
                    ((ToggleButton) findViewById(R.id.btnPort)).setChecked(stat.ports.get(17).value.toBool());

                } else {
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(String message) {
        // TODO Auto-generated method stub
        int a = 5;
    }


}
