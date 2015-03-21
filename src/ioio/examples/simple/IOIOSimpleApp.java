/*
*The MIT License (MIT)
*
*Copyright (c) 2015 Michael Gunderson
*
*Permission is hereby granted, free of charge, to any person obtaining a copy
*of this software and associated documentation files (the "Software"), to deal
*in the Software without restriction, including without limitation the rights
*to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
*copies of the Software, and to permit persons to whom the Software is
*furnished to do so, subject to the following conditions:
*
*The above copyright notice and this permission notice shall be included in
*all copies or substantial portions of the Software.
*
*THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
*IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
*FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
*AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
*LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
*OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
*THE SOFTWARE.
 */
package ioio.examples.simple;

import ioio.lib.api.AnalogInput;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;

public class IOIOSimpleApp extends IOIOActivity {
	private TextView textView;
	private TextView textViewLabel;
	private ToggleButton btnRed;
	private ToggleButton btnGreen;
	private ToggleButton btnBlue;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		textView = (TextView) findViewById(R.id.title);
		textViewLabel = (TextView) findViewById(R.id.label);
		btnRed = (ToggleButton) findViewById(R.id.buttonred);
		btnGreen = (ToggleButton) findViewById(R.id.buttongreen);
		btnBlue = (ToggleButton) findViewById(R.id.buttonblue);
	}

	class Looper extends BaseIOIOLooper {
		private DigitalOutput redLed;  
		private DigitalOutput greenLed;
		private DigitalOutput blueLed;

		@Override
		public void setup() throws ConnectionLostException {
			
			int redPin = 3; // Pin 3 of the IOIO is the output to the red leg of the RGB LED.
			int greenPin = 2;
			int bluePin = 1;
			// Ready each pin for use as an output.
			redLed = ioio_.openDigitalOutput(redPin, false);
			greenLed = ioio_.openDigitalOutput(greenPin,false);
			blueLed = ioio_.openDigitalOutput(bluePin,false);
		}

		@Override
		public void loop() throws ConnectionLostException, InterruptedException {
			// Turn each LED on or off based on their status variables.
//			led_.write(!toggleButton_.isChecked());
		    redLed.write(btnRed.isChecked());
		    greenLed.write(btnGreen.isChecked());
		    blueLed.write(btnBlue.isChecked());
		    setBackgroudColor(btnRed.isChecked(), btnGreen.isChecked(), btnBlue.isChecked());
		    // Don't call this loop again for 100 milliseconds
		    Thread.sleep(100);
		}
	}

	@Override
	protected IOIOLooper createIOIOLooper() {
		return new Looper();
	}
	
	private void setBackgroudColor(final boolean red, final boolean green, final boolean blue) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(red && !green && !blue){
					textViewLabel.setBackgroundColor(Color.RED);	
				} else if (!red && green && !blue) {
					textViewLabel.setBackgroundColor(Color.GREEN);
				} else if (!red && !green && blue) {
					textViewLabel.setBackgroundColor(Color.BLUE);
				} else if (red && green && !blue) {
					textViewLabel.setBackgroundColor(Color.YELLOW);
				} else if (red && !green && blue) {
					textViewLabel.setBackgroundColor(Color.MAGENTA);
				} else if (!red && green && blue) {
					textViewLabel.setBackgroundColor(Color.CYAN);
				} else if (red && green && blue) {
					textViewLabel.setBackgroundColor(Color.WHITE);
				} else {
					textViewLabel.setBackgroundColor(Color.BLACK);
				}
			}
		});
	}
}