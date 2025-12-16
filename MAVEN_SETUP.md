# Maven Installation Guide for HealthHub Project

## Maven is Already Configured in the Project ✅

Your project already has Maven configuration in `pom.xml`. You just need to install Maven on your Windows system.

## Step-by-Step Installation

### Option 1: Using Chocolatey (Recommended - Easiest)

If you have Chocolatey package manager:
```powershell
choco install maven
```

### Option 2: Manual Installation

#### Step 1: Download Maven
1. Go to: https://maven.apache.org/download.cgi
2. Download: `apache-maven-3.9.x-bin.zip` (latest version)
3. Extract to: `C:\Program Files\Apache\maven` (or your preferred location)

#### Step 2: Set Environment Variables

**Method A: Using System Properties (GUI)**
1. Right-click "This PC" → Properties
2. Click "Advanced system settings"
3. Click "Environment Variables"
4. Under "System variables", find `Path` and click "Edit"
5. Click "New" and add: `C:\Program Files\Apache\maven\bin`
6. Click "OK" on all windows

**Method B: Using PowerShell (Run as Administrator)**
```powershell
[Environment]::SetEnvironmentVariable("MAVEN_HOME", "C:\Program Files\Apache\maven", "Machine")
[Environment]::SetEnvironmentVariable("Path", $env:Path + ";C:\Program Files\apache-maven-3.9.11-bin", "Machine")
```
C:\Program Files\apache-maven-3.9.11-bin

#### Step 3: Verify Installation
Open a NEW terminal/PowerShell window and run:
```bash
mvn -version
```

You should see output like:
```
Apache Maven 3.9.x
Maven home: C:\Program Files\Apache\maven
Java version: 17.x.x
```

## Step 4: Navigate to Your Project

```bash
cd C:\Users\TornikeAladashvili\IdeaProjects\HealthHub
```

## Step 5: Run Tests

Now you can run the tests:
```bash
mvn clean test
```

## Troubleshooting

### If "mvn" command not found after installation:
1. **Close and reopen** your terminal/PowerShell window
2. Verify PATH: `echo $env:Path` (PowerShell) or `echo %PATH%` (CMD)
3. Check Maven installation: `dir "C:\Program Files\Apache\maven\bin\mvn.cmd"`

### If Java version error:
- Ensure Java 17+ is installed
- Set JAVA_HOME: `[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Java\jdk-17", "Machine")`

### Alternative: Use Maven Wrapper (No Installation Needed)

If you prefer not to install Maven globally, you can add Maven Wrapper to the project:
```bash
mvn wrapper:wrapper
```
Then use: `.\mvnw.cmd clean test` instead of `mvn clean test`

## Quick Reference

Once Maven is installed, use these commands in your project directory:

```bash
# Clean and compile
mvn clean compile

# Run all tests
mvn clean test

# Run only unit tests
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/unit-test-suite.xml

# Run only integration tests
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/integration-test-suite.xml
```


