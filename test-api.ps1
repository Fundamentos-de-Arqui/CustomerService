# Customer Service API Testing Script
# Run this script to test all endpoints

Write-Host "=== Customer Service API Testing ===" -ForegroundColor Green

# Base URL
$baseUrl = "http://localhost:8080"

# Test 1: Health Check
Write-Host "`n1. Testing Health Check..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "$baseUrl/api/v1/ping" -Method GET
    Write-Host "✅ Health Check: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Response: $($response.Content)" -ForegroundColor Cyan
} catch {
    Write-Host "❌ Health Check Failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 2: Get All Patients
Write-Host "`n2. Testing Get All Patients..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "$baseUrl/api/v1/patients" -Method GET
    Write-Host "✅ Get Patients: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Response: $($response.Content)" -ForegroundColor Cyan
} catch {
    Write-Host "❌ Get Patients Failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 3: Create Patient
Write-Host "`n3. Testing Create Patient..." -ForegroundColor Yellow
$patientData = @{
    firstNames = "John"
    paternalSurname = "Doe"
    maternalSurname = "Smith"
    documentNumber = "87654321"
    documentType = "DNI"
    phone = "+987654321"
    email = "john.doe@email.com"
    birthPlace = "Lima, Peru"
    birthDate = "1990-01-01"
    firstAppointmentAge = 30
    currentAge = 33
    gender = "MALE"
    maritalStatus = "SINGLE"
    currentAddress = "Av. Principal 123"
    district = "Miraflores"
    province = "Lima"
    region = "Lima"
    country = "Peru"
    religion = "CATHOLIC"
    educationLevel = "UNIVERSITY"
    occupation = "Software Engineer"
    currentEducationalInstitution = "Universidad Nacional de Ingenieria"
    receiptType = "INVOICE"
} | ConvertTo-Json

try {
    $response = Invoke-WebRequest -Uri "$baseUrl/api/v1/patients" -Method POST -Body $patientData -ContentType "application/json"
    Write-Host "✅ Create Patient: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Response: $($response.Content)" -ForegroundColor Cyan
} catch {
    Write-Host "❌ Create Patient Failed: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $responseBody = $reader.ReadToEnd()
        Write-Host "Error Details: $responseBody" -ForegroundColor Red
    }
}

# Test 4: Broker Health Check
Write-Host "`n4. Testing Broker Health Check..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "$baseUrl/api/v1/patients/broker/health" -Method GET
    Write-Host "✅ Broker Health: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Response: $($response.Content)" -ForegroundColor Cyan
} catch {
    Write-Host "❌ Broker Health Check Failed: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $responseBody = $reader.ReadToEnd()
        Write-Host "Error Details: $responseBody" -ForegroundColor Red
    }
}

# Test 5: Send Test Message to Broker
Write-Host "`n5. Testing Broker Message Send..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "$baseUrl/api/v1/patients/broker/test" -Method POST
    Write-Host "✅ Test Message: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Response: $($response.Content)" -ForegroundColor Cyan
} catch {
    Write-Host "❌ Test Message Failed: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $responseBody = $reader.ReadToEnd()
        Write-Host "Error Details: $responseBody" -ForegroundColor Red
    }
}

# Test 6: Upload Excel File (if file exists)
Write-Host "`n6. Testing Excel Upload..." -ForegroundColor Yellow
$excelFile = "test-patient.xlsx"
if (Test-Path $excelFile) {
    try {
        # Use curl for multipart form data
        $curlCommand = "curl -X POST `"$baseUrl/api/v1/patients/upload`" -F `"fileName=$excelFile`" -F `"file=@$excelFile`""
        $response = Invoke-Expression $curlCommand
        Write-Host "✅ Excel Upload: 200" -ForegroundColor Green
        Write-Host "Response: $response" -ForegroundColor Cyan
    } catch {
        Write-Host "❌ Excel Upload Failed: $($_.Exception.Message)" -ForegroundColor Red
        if ($_.Exception.Response) {
            $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
            $responseBody = $reader.ReadToEnd()
            Write-Host "Error Details: $responseBody" -ForegroundColor Red
        }
    }
} else {
    Write-Host "⚠️ Excel file '$excelFile' not found. Skipping Excel upload test." -ForegroundColor Yellow
    Write-Host "To test Excel upload, create a file named '$excelFile' with patient data." -ForegroundColor Cyan
}

Write-Host "`n=== Testing Complete ===" -ForegroundColor Green


