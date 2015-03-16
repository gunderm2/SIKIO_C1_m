/*
 * Copyright 2015 Michael Gunderson. All rights reserved.
 *
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL ARSHAN POURSOHI OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied.
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