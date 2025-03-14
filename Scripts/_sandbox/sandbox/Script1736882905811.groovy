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
import org.sikuli.script.*
import java.awt.Desktop as Desktop
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
//import com.kazurayam.ks.globalvariable.ExecutionProfilesLoader

//new ExecutionProfilesLoader().loadProfile("Education Candidate 14")

msg = 'Exception Occured-org.openqa.selenium.ElementClickInterceptedException: element click intercepted: Element <input id="profile_group-1449971279.254_time_commitment" type="checkbox" name="profile[group-1449971279.254][time_commitment][]" value="One year to two years"> is not clickable at point (64, 287). Other element would receive the click: <div class="col-sm-offset-3 col-sm-9 text-danger">...</div>'

lastIndex = 0
colons = []
colons = msg.findAll(~/:/) { match ->
	lastIndex = msg.indexOf(match, lastIndex+1)
}
error = msg.substring(colons[0]+1, colons[1])
println(error)

typeIndex = msg.indexOf('type=')
typeEnd = msg.indexOf('" ',typeIndex+1)
type = msg.substring(typeIndex+5, typeEnd).replace('"', '')
println(type)

valueIndex = msg.indexOf('value=')
valueEnd = msg.indexOf('"',valueIndex+7)
value = msg.substring(valueIndex+7, valueEnd).replace('"', '')
println(value)

errorMsg = '##### ERROR: ' + error + ' on ' + type + ' ' + value

println(errorMsg)
