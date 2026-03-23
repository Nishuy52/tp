@echo off
setlocal enableextensions
pushd %~dp0

cd ..

:: ── step 1: build ─────────────────────────────────────────────────────────────
call gradlew clean shadowJar
if errorlevel 1 (
    echo [FAIL] Build failed -- gradlew clean shadowJar did not complete successfully
    exit /b 1
)
echo [PASS] Build

:: ── step 2: locate jar ────────────────────────────────────────────────────────
cd build\libs
set jarloc=
for /f "tokens=*" %%a in ('dir /b *.jar 2^>nul') do set jarloc=%%a

if "%jarloc%"=="" (
    echo [FAIL] No jar found in build\libs\
    exit /b 1
)
echo [PASS] Jar located: %jarloc%

:: ── step 3: run app ───────────────────────────────────────────────────────────
java -jar %jarloc% < ..\..\text-ui-test\input.txt > ..\..\text-ui-test\ACTUAL.TXT
if errorlevel 1 (
    echo [FAIL] Application crashed during run -- check input.txt and output above
    exit /b 1
)
cd ..\..\text-ui-test
powershell -NoProfile -Command ^
    "$text = (Get-Content ACTUAL.TXT -Raw) -replace '\x1b\[[0-9;]*[a-zA-Z]', ''; [System.IO.File]::WriteAllText('ACTUAL.TXT', $text, (New-Object System.Text.UTF8Encoding $False))"
echo [PASS] Application run

:: ── step 4: resolve date placeholder ─────────────────────────────────────────

for /f "tokens=1-3 delims=-" %%a in (
    'powershell -NoProfile -Command "Get-Date -Format yyyy-MM-dd"'
) do set TODAY=%%a-%%b-%%c

powershell -NoProfile -Command ^
    "$text = (Get-Content EXPECTED.TXT -Raw) -replace '\{TODAY\}', '%TODAY%'; [System.IO.File]::WriteAllText('EXPECTED-RESOLVED.TXT', $text, (New-Object System.Text.UTF8Encoding $False))"
if errorlevel 1 (
    echo [FAIL] Could not resolve {TODAY} placeholder in EXPECTED.TXT
    exit /b 1
)
echo [PASS] Date placeholder resolved (%TODAY%^)

:: ── step 5: diff ──────────────────────────────────────────────────────────────
FC ACTUAL.TXT EXPECTED-RESOLVED.TXT
if errorlevel 1 (
    echo [FAIL] Output mismatch -- see diff above
    exit /b 1
)

echo [PASS] All tests passed!