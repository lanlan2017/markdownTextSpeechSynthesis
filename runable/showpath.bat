@echo off
setlocal enabledelayedexpansion 
set remain=%path%
:loop
for /f "tokens=1* delims=;" %%a in ("%remain%") do (
	echo %%a
	::ʣ�µĸ�ֵ��ԭ���ĸ���,�Ա��´ηֶ�
	set remain=%%b
)
::�������ʣ��,������ָ�
if defined remain goto :loop
::pause
