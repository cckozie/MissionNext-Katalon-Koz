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
import com.kms.katalon.core.configuration.RunConfiguration
import java.io.File
import groovy.io.FileType

import java.io.*;


path = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/journey candidate/journey contact registration/'

tooltips = ['username', 'email', 'password', 'first_name', 'last_name', 'learn_about_us', 'terms_and_conditions']

for (def tooltip : tooltips) {
	
   // Getting the file by creating object of File class
//   File f = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Test Journey Register.txt')
  myImage = (((path + tooltip) + '_tooltip') + '.png')

  f = new File(myImage)



   // Checking if the specified file exists or not
   if (f.exists())

	   // Show if the file exists
   System.out.println(myImage + " Exists");
   else

	   // Show if the file does not exists
   System.out.println(myImage + " Does not Exists")
}
/*
   dirName = RunConfiguration.getProjectDir() + '/Profiles'

//Users/cckozie/git/MissionNext-Katalon-Koz/Profiles

println(dirName)

new File(dirName).eachFile() {
	file->println file.name
 }
*/
/*
File[] files = new File(dirName).listFiles()
println( files )


dir = new File(dirName)
dir.eachFileRecurse (FileType.FILES) { file ->
  list << file
}
println(list)


f = new File('/Users/cckozie/Documents/MissionNext/test.txt')

valueA = 'testA'

f.append(valueA+ " hello")
*/