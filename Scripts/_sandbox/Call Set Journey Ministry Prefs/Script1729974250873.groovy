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

ministries = ['Adult Men', 'Marriage & Family', 'Public Relations', 'Writers', 'Construction/Trade', 'Urban Gardening', 'Electrician'
    , 'Project Manager', 'Mentoring', 'Student (College)', 'Informal Education', 'TESL (English)', 'Training ESL Teachers'
    , 'Electrical', 'Radio', 'JESUS Film Showings/Dist', 'Personal/Group Evangelism', 'Bible Translation', 'People Group Research'
    , 'Career Counseling', 'Nurse', 'Primary Care', 'IT Business Analyst', 'IT Systems Analyst', 'Quality Assurance Engineer'
    , 'Human Trafficking/Prostitution', 'Muslim', 'Jewish', 'Distribution', 'Logistics', 'Tour/Museum Guide', 'Auto Mechanic/Body'
    , 'A&P Mechanic', 'Pilot - Single Engine', 'Project Management', 'Business', 'Engineering', 'Project Managers', 'Agriculture/Horticulture'
    , 'Micro-Enterprise']

GlobalVariable.ministriesList = ministries
WebUI.callTestCase(findTestCase('Journey Partner Profile/Tabs/Set Journey Ministry Prefs'), [('varMinistries') : ministries], FailureHandling.STOP_ON_FAILURE)

