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
// ** DESIGNATES FINISHED \\

// **When running test suites, build an error log file and write the file names into it of test result files that contain errors. Use timestamp in file name.
// 
// Need a test to verify consistency between candidate matches and job matches - both Education and Journey
// Copy matching test cases to match from candidates' perspective.
//		(Can I create a bunch of candidate profiles without causing undesired statistics of new users?)
//		(If I delete and recreate a profile does that go into the stats?. Other wise I need to do maintenance on the profile and wait for the hour to change)
//		IDEA: Capture candidates' profile entries and store in csv files, then create or edit test candidate profiles to mimic them.
//		Maybe create a script to capture job definitions from a real user and recreate them for my test user
//
// **(Seems to be more rounding errors now, but overall it's good) Need to retest matching now that the job's profile has had Time/Hours added. (Issue 424)
//
// Add a test to verify that the correct app is assigned to both Journey and Education candidates at the time they complete their profile.
//
// Add creating jobs to Journey and Education partners. Force contact and email to me.
//
// Add tests to verify that designed matches are actually occuring in Journey and Education (known candidates and jobs)
//
// Rewrite test for profile creation success to account for the immediate grandting of access