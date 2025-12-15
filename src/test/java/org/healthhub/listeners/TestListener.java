package org.healthhub.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class TestListener implements ITestListener, ISuiteListener {

    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;
    private static int skippedTests = 0;
    
    @Override
    public void onStart(ISuite suite) {
        System.out.println();
        System.out.println("Test Suite Started: " + suite.getName());
        System.out.println();
        resetCounters();
    }
    
    @Override
    public void onFinish(ISuite suite) {
        System.out.println();
        System.out.println("Test Suite Finished: " + suite.getName());
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + failedTests);
        System.out.println("Skipped: " + skippedTests);
        System.out.println();
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        totalTests++;
        System.out.println("LISTENER Test Started: " + result.getMethod().getMethodName() +
                         " in class: " + result.getTestClass().getName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        passedTests++;
        System.out.println("LISTENER  Test Passed: " + result.getMethod().getMethodName() +
                         " (Duration: " + (result.getEndMillis() - result.getStartMillis()) + "ms)");
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        failedTests++;
        System.out.println("LISTENER Test Failed: " + result.getMethod().getMethodName());
        if (result.getThrowable() != null) {
            System.out.println("LISTENER Failure Reason: " + result.getThrowable().getMessage());
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        skippedTests++;
        System.out.println("LISTENER Test Skipped: " + result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("LISTENER Test Failed but within success percentage: " +
                         result.getMethod().getMethodName());
    }
    
    private void resetCounters() {
        totalTests = 0;
        passedTests = 0;
        failedTests = 0;
        skippedTests = 0;
    }
    
    public static int getTotalTests() {
        return totalTests;
    }
    
    public static int getPassedTests() {
        return passedTests;
    }
    
    public static int getFailedTests() {
        return failedTests;
    }
    
    public static int getSkippedTests() {
        return skippedTests;
    }
}

