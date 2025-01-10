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
import javax.swing.JFrame
import javax.swing.JOptionPane

JFrame frame = new JFrame("User Input Frame")
frame.requestFocus()
String number = JOptionPane.showInputDialog("frame, Enter user input value!")
int days = Integer.valueOf(number)

a = 'https://education.missionnext.org/education-home/im-a-school'
b = 'https://education.missionnext.org/education-home/im-a-school/'
println(a.substring(a.length()-1,a.length()))
println(b.substring(b.length()-1,b.length()))
if(a.endsWith('/')) {
	println('a-slash')
}
if(b.endsWith('/')) {
	println('b-slash')
}
System.exit(0)

myStack = []
myStack.push("A")
myStack.push("B")
myStack.push("C")
myStack.push("D")
println(myStack.pop())
println(myStack)
println(myStack.removeLast())