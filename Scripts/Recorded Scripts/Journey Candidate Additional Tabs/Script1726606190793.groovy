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

// Set user data
username = 'cktest05jc'
password = 'Wghj4XX0rEWAthMh0R18Tg=='
email = 'cktest05@missionnext.org'

WebUI.openBrowser('')

WebUI.navigateToUrl('https://journey.explorenext.org/login-here/')

WebUI.setText(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Username_log'), username)

WebUI.setEncryptedText(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Password_pwd'), password)

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/span_Log In'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Female_profilegroup-1444754265.711gender'))

WebUI.selectOptionByValue(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/select_AlabamaAlaskaArizonaArkansasCaliforn_3be247'), 
    'Minnesota', true)

WebUI.setText(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Birth Year_profilegroup-1444754265.71_ff2ac6'), 
    '1949')

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Complete Submit_submit'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/a_Situation'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/div_I have served in ministry or missions f_5bccaa'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_I have attended a missions event_prof_889a05'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Complete Submit_submit'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/a_Availability'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Part Time  20 HoursWeek_profilegroup-_f6bb02'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Complete Submit_submit'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/a_Your Ministry Prefs'))

WebUI.delay(3)

//WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Job Categories_profilegroup-162398760_a72ad7'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Job Categories_profilegroup-162398760_a72ad7'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Agriculture_profilegroup-1623987608.4_f8dbe0'))

/*
WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Agriculture_profilegroup-1623987608.4_f8dbe0'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Agriculture_profilegroup-1623987608.4_f8dbe0'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Agriculture_profilegroup-1623987608.4_f8dbe0'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Agriculture_profilegroup-1623987608.4_f8dbe0'))
*/

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/xxxJourney Candidate Additional Tabs/input_Complete Submit_submit'))

