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
/*
import org.openqa.selenium.Keys as Keys
//import javax.swing.JOptionPane as JOptionPane
//import javax.swing.JFrame as JFrame
//import javax.swing.JFrame as JFrame
//import javax.swing.JDialog as JDialog
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

JFrame frame = new JFrame("FrameDemo");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

JLabel emptyLabel = new JLabel("");
emptyLabel.setPreferredSize(new Dimension(175, 100));
frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

//Display the window.
frame.pack();
frame.setVisible(true);

WebUI.delay(10)
/*
JFrame frame = new JFrame("Main Window");

JOptionPane.showMessageDialog(frame, "Message for the dialog box goes here.","Error", JOptionPane.ERROR_MESSAGE);

frame.setSize(350,350);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setVisible(true);
*/

/*
WebUI.openBrowser('')

JOptionPane optionPane = new JOptionPane();
JDialog dialog = optionPane.createDialog("Title");
dialog.setAlwaysOnTop(true);
dialog.setVisible(true);

WebUI.closeBrowser()
*/
/*
WebUI.openBrowser('')

def iCnt = 0
while (iCnt <= 15) {
    WebUI.executeJavaScript('alert(\'==> Please wait... <==\')', null, FailureHandling.OPTIONAL);
	Thread.sleep (300);
	iCnt++;
}
WebUI.closeBrowser()
*/

/*
JavascriptExecutor js=(JavascriptExecutor)driver;
js.executeScript("alert('This is an alert');");
WebUI.delay(5)
*/

/*
JFrame frame = new JFrame("Directory list");
frame.requestFocus()
JOptionPane.showMessageDialog(frame,
		"help",
		"Directory Listing",
		JOptionPane.PLAIN_MESSAGE);
*/
	

/*

JFrame frame = new JFrame('User Input')

frame.requestFocus()

String url = JOptionPane.showInputDialog(frame,'URL?')

WebUI.openBrowser('')

WebUI.navigateToUrl(url)

WebUI.delay(10)

WebUI.closeBrowser()
	
*/