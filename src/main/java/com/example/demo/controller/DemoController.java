package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// localhost:8083/war/path
// localhost:8083/path

@RestController
public class DemoController {

	@GetMapping("/")
	public String testing() {

		return "ONLINE";
	}

	@GetMapping("date")
	public String dateLocal() {
		String retd = "";
		Date date = new Date();
		String strDateFormat = "dd/MM/YYYY hh:mm:ss a";
		DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		retd = dateFormat.format(date);

		return retd;
	}

	/*
	 * @GetMapping("memory") public String kosMemory() throws InterruptedException {
	 * int dummyArraySize = 15; String ret = "Max JVM memory: " +
	 * Runtime.getRuntime().maxMemory() + "\n"; long memoryConsumed = 0; try {
	 * long[] memoryAllocated = null; for (int loop = 0; loop < Integer.MAX_VALUE;
	 * loop++) { memoryAllocated = new long[dummyArraySize]; memoryAllocated[0] = 0;
	 * memoryConsumed += dummyArraySize * Long.SIZE; ret =
	 * "Memory Consumed till now: " + memoryConsumed + "\n"; dummyArraySize *=
	 * dummyArraySize * 2;
	 * 
	 * Thread.sleep(500); } } catch (OutOfMemoryError outofMemory) { ret =
	 * "\n\nCatching out of memory error"; // Log the information,so that we can
	 * generate the statistics (latter on). throw outofMemory; } return ret; }
	 */

	@GetMapping("memory")
	public void kosMemory() throws Exception {
		int iteratorValue = 20;
		System.out.println("\n=================> OOM test started..\n");
		for (int outerIterator = 1; outerIterator < 20; outerIterator++) {
			System.out.println("Iteration " + outerIterator + " Free Mem: " + Runtime.getRuntime().freeMemory());
			int loop1 = 2;
			int[] memoryFillIntVar = new int[iteratorValue];
			// feel memoryFillIntVar array in loop..
			do {
				memoryFillIntVar[loop1] = 0;
				loop1--;
			} while (loop1 > 0);
			iteratorValue = iteratorValue * 5;
			System.out.println("\nRequired Memory for next loop: " + iteratorValue);
			Thread.sleep(1000);
		}
	}
	
	@GetMapping("whoami")
	public String whoAmI(@RequestParam String command) throws Exception {
		
		String s = null;
		String print = "";
		
		if (command.isEmpty()) {
			command = "hostname";
		}
		
		try {
            
	    // run the Unix "ps -ef" command
            // using the Runtime exec method:
            Process p = Runtime.getRuntime().exec(command);
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n<br/>");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                print += s+" \n<br/>";
            }
            
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n<br/>");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
                print += s+"\n<br/>";
            }
            
         //   System.exit(0);
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
//            System.exit(-1);
        }
        
        return print;

	}
	

}
