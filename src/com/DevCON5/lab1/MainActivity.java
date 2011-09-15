package com.DevCON5.lab1;


import android.app.Activity;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import java.text.DecimalFormat;

public class MainActivity extends Activity {
    
	/***Functionality variables
	 * 
	 * 
	 ***Correspond to Widgets in the UI**/
	
	private EditText txt_bill_amount;
	private EditText txt_tip_percentage;
	private EditText txt_bill_amt_display;
	private EditText txt_tip_amt_display;
	private Button go_button_calc;
	private Button reset_button;
	private double bill_amount = 0;
	private double percentage = 0;
	private double tip_amount = 0;
	private double total_bill = 0;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initializeControls();
    }
    
    private void initializeControls()
    {
    	//---Set functionality variables to the corresponding UI widgets via R.id.*
    	
    	txt_bill_amount = (EditText) findViewById(R.id.bill_amt_input);
    	txt_bill_amount.requestFocus();		//Place cursor here on load
    	
    	txt_tip_percentage = (EditText) findViewById(R.id.tip_percent_input);
    	
    	txt_bill_amt_display = (EditText) findViewById(R.id.bill_amt_display);
    	txt_bill_amt_display.setFocusable(false); //Can't enter these fields
    	
    	txt_tip_amt_display = (EditText) findViewById(R.id.tip_amt_display);
    	txt_tip_amt_display.setFocusable(false); //Can't enter these fields
    	
    	go_button_calc = (Button) findViewById(R.id.go_button);
    	go_button_calc.setEnabled(false);	//Disable go button 
    	
    	reset_button = (Button) findViewById(R.id.reset_button);
    	
    	
    	/*
         * Attach a KeyListener to the Bill Amount and tip Percentage text fields
         */
        txt_bill_amount.setOnKeyListener(mKeyListener);
        txt_tip_percentage.setOnKeyListener(mKeyListener);
        
        /* Attach listener to the Calculate and Reset buttons */
        go_button_calc.setOnClickListener(mClickListener);
        reset_button.setOnClickListener(mClickListener);
    }
        
        /*
         * ClickListener for the Calculate and Reset buttons.
         * Depending on the button clicked, the corresponding
         * method is called.
         */
        private OnClickListener mClickListener = new OnClickListener() {
         
            public void onClick(View v) {
                if (v.getId() == R.id.go_button) {
                    calculateNeededValues();
                } else {
                    resetFields();
                }
            }
        };
    
    /*
     * KeyListener for the Bill Amount and Tip Percentage fields.
     * We need to apply this key listener to check for following
     * conditions:
     *
     * If user does not enter values in the Bill Amount and Tip Percentage,
     * we cannot perform the calculations. Hence enable the Calculate button
     * only when user enters a valid values.
     */
    private OnKeyListener mKeyListener = new OnKeyListener() {
        
   	 public boolean onKey(View v, int keyCode, KeyEvent event) {
     
        switch (v.getId()) {
        case R.id.bill_amt_input:
        case R.id.tip_percent_input:
            go_button_calc.setEnabled(txt_bill_amount.getText().length() > 0
                    && txt_tip_percentage.getText().length() > 0); 
            break;
        
        }
        return false;
        }
    };
    
    private void calculateNeededValues()
    {
    	//---Parse the input from the bill amount and tip percentage EditText widgets
    	//---into double variables for computation
    	bill_amount = Double.parseDouble(
    			txt_bill_amount.getText().toString());
    	percentage = Double.parseDouble(
    			txt_tip_percentage.getText().toString());
    	
    	boolean isError = false; 
    	if (bill_amount < 1.0) {				//Check for invalid bill amounts
            txt_bill_amount.requestFocus();
            isError = true;
    	}

    	/*
    	 * If all fields are populated with valid values, then proceed to
    	 * calculate the tips
    	 */
    	if (!isError) {
    		tip_amount = (bill_amount*percentage)/100;		/*calculate the tip*/
    		total_bill = bill_amount + tip_amount;			/*calculate the total bill*/
        
    		displayToScreen();
    	}
    	
    	else{
    		resetFields();
    	}
    	}
    	
    
    	private void displayToScreen()
    	{
    		//---displays formatted decimal text to the tip amount and bill amount EditText widgets in the UI
    		txt_tip_amt_display.setText(customFormat("####.00", tip_amount)); //Format for US dollar
    		txt_bill_amt_display.setText(customFormat("####.00", total_bill));
    	}	
    		
    	static public String customFormat(String pattern, Double s)
    	{
    		//---Takes a double value, formats it according to the input pattern, and returns a String value
    		DecimalFormat myFormatter = new DecimalFormat(pattern);
    		String formattedOutput = myFormatter.format(s);
    		return formattedOutput;
    }
    	
    	
    	/*
    	 * Resets the results text views at the bottom of the screen as well as
    	 * resets the text fields and radio buttons.
    	 */
    	private void resetFields() {
    		txt_tip_amt_display.setText("0.00");
    		txt_bill_amt_display.setText("0.00");
    		
    		txt_bill_amount.setText("");
    		go_button_calc.setEnabled(false); //Disable the go button once more
    		
    		txt_tip_percentage.setText("15");
    		// set focus on the first field
    		txt_bill_amount.requestFocus();
    	}
}