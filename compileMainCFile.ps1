Import-Module "$env:ProgramFiles\Microsoft Visual Studio\2022\Preview\Common7\Tools\Microsoft.VisualStudio.DevShell.dll"
Enter-VsDevShell -VsInstallPath "$env:ProgramFiles\Microsoft Visual Studio\2022\Preview" -DevCmdArguments '-arch=x64'
lib /DEF:lib\build\bin\native\debugShared\native.def /OUT:lib\build\bin\native\debugShared\native.lib
cl.exe main.c lib\build\bin\native\debugShared\native.lib /Fe:lib\build\bin\native\debugShared\main.exe /Fo:lib\build\bin\native\debugShared\main.obj
