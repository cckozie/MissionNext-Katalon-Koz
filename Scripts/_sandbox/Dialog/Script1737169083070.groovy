import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
//import javax.swing.JFrame
//import java.awt.Frame
//import javax.swing.JOptionPane
//import java.awt.event.*;
//import java.awt.*;
import javax.swing.*;

frame = new JFrame("");       
JPanel p = new JPanel();
JLabel l = new JLabel("WAITING...", SwingConstants.CENTER);
frame.add(l);
frame.setSize(300, 100);
frame.setLocation(600, 0);
frame.setAlwaysOnTop (true)
frame.show();
    
/*
pane = new JOptionPane("This dialog will close automatically after 5 seconds.", JOptionPane.INFORMATION_MESSAGE);
JDialog dialog = pane.createDialog("Message");
dialog.setVisible(true);
*/

WebUI.delay(10)
frame.hide();
WebUI.delay(5)
