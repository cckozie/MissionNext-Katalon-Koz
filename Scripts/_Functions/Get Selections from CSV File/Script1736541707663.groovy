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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils

BufferedReader reader;
checked = []
//varFileName = 'Education Candidate Forms/preferred positions.csv'
//varSelections = 'regions'
reader = new BufferedReader(new FileReader("/Users/cckozie/Documents/MissionNext/" + varFileName));
String line = reader.readLine();

include = false
	
while (line != null) {
	println(line)
	group = line.indexOf('<Group>')
	println(group)
	if(group >= 0) {
		selections = line.substring(group + 7,line.length()-1)
		println(selections)
		if(selections == varSelections) {
			include = true
		} else {
			include = false
		}
	} else {
		if (include) {
			println(line)
			comma = line.indexOf(',y')
			if(comma > 0) {
				commaCount = StringUtils.countMatches(line, ',')
				if(commaCount > 1) {
					println('Mulitple Commas : ' + line)
//					line = StringUtils.replaceOnce(line,',','')
					line = line.replace('"','')
					label = line.substring(1,line.length()-2)
				} else {
					label = line.substring(1,comma)
				}
				println(label);
				checked.add(label)
	//			checked.add("'" + label + "'")
			}
		}
	}
	// read next line
	line = reader.readLine();
//	WebUI.delay(1)
}

reader.close();
println(checked)
return checked

