@echo off
::
::
:: ### ��ȡ��ǰ�ļ�(uninstall.bat)���ڵ�Ŀ¼�ľ���·�� ####
set thispath=%~dp0
echo ��ȡ����·��%thispath%
::
set "thispath=%thispath:~0,-1%"
echo   ��ǰ�ļ����ڵ�Ŀ¼�ľ���·��:%thispath%
:: ###################################################
setlocal enabledelayedexpansion 
set remain=%path%
::��ɾ���ַ���
set toDel=%thispath%
:loop
for /f "tokens=1* delims=;" %%a in ("%remain%") do (
	if not "%toDel%"=="%%a" (
		::���mypathû�ж���Ļ���ֱ�Ӹ�ֵ,��ֵ֮���ֻ��Ҫ׷��
		if not defined mypath ( set mypath=%%a) else (set mypath=%mypath%;%%a)
	)
	rem ����ȡʣ�µĲ��ָ�������remain����ʵ�������ʹ���ӳٱ�������
	set remain=%%b
)
::�������ʣ��,������ָ�
if defined remain goto :loop
echo ɾ��֮ǰ��path��������
echo %path%
echo.
echo ɾ��֮���path��������
echo %mypath%
echo �����޸�ϵͳpath��������...
setx /m "path" "%mypath%"
echo �޸����...
pause