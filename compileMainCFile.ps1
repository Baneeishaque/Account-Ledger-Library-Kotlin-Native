.\gradlew.bat linkDebugSharedMingwX64
Import-Module "$env:ProgramFiles\Microsoft Visual Studio\2022\Preview\Common7\Tools\Microsoft.VisualStudio.DevShell.dll"
Enter-VsDevShell -VsInstallPath "$env:ProgramFiles\Microsoft Visual Studio\2022\Preview" -DevCmdArguments '-arch=x64'
Set-Location -Path $(Split-Path $($MyInvocation.MyCommand.Path) -Parent)
lib /DEF:lib\build\bin\mingwX64\debugShared\account_ledger_lib.def /OUT:lib\build\bin\mingwX64\debugShared\account_ledger_lib.lib
cl.exe main.c lib\build\bin\mingwX64\debugShared\account_ledger_lib.lib /Fe:lib\build\bin\mingwX64\debugShared\main.exe /Fo:lib\build\bin\mingwX64\debugShared\main.obj
