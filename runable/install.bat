@echo off
::################ ���ɵ�ǰ·�� ��ʼ ########################
:: ��ȡ��ǰ����Ŀ¼
set thispath=%~dp0
set "thispath=%thispath:~0,-1%"
echo   ��ǰ�ļ����ڵ�Ŀ¼�ľ���·��:%thispath%
::��Ϊ������е�ʱ��Ĭ����C:\Windows\System32\Ŀ¼��
::�л����ļ����ڵ��̷�
%~d0
::�����ļ����ڵ�·��
cd %~dp0
::
::################ ���ɵ�ǰ·�� ���� ########################
::
:: ################# ������������ ��ʼ #################
::
echo ############ ������������ xunfei.bat... #####
::����д��
echo @echo off>xunfei.bat
echo ::�л��̷�>>xunfei.bat
echo %~d0>>xunfei.bat
echo :: �����ļ�����·��>>xunfei.bat
echo cd %~dp0>>xunfei.bat
echo :: �ж��Ƿ��������,Ϊ�˽�ʡʱ��,ֻ���ͻ�������1��>>xunfei.bat
echo ping www.xfyun.cn -n 1 ^> nul>>xunfei.bat
echo :: �������������ִ�г���>>xunfei.bat
echo if %%errorlevel%% leq 0 (>>xunfei.bat
echo    echo ������������.>>xunfei.bat
echo    java -jar "%thispath%\xunfei.jar" %%1>>xunfei.bat
echo ) else (>>xunfei.bat
echo :: ��������������������ʾ>>xunfei.bat
echo    echo �޷����ӵ�www.xfyun.cn,�����������Ƿ���������.>>xunfei.bat
echo    pause>>xunfei.bat
echo    exit>>xunfei.bat
echo )>>xunfei.bat
::
:: ################# ������������ ���� #################
:: 
::
::
:: ################  ����path�������� ��ʼ  ##############
echo ############# ����path�������� ��ʼ ##############
::
:: ####################################################
::��ȡpath�����������Զ��������
set remain=%path%
::����ӵ�·���ַ���
set toAdd=%thispath%
::���,Ĭ��û���ظ�
set finded=false
:loop
for /f "tokens=1* delims=;" %%a in ("%remain%") do (
	::����ҵ���ͬ����
	if "%toAdd%"=="%%a" (
		::ֱ���˳�
		goto :isFinded
		::�ñ���,true��ʾ���ظ�����
		set finded=true
	)
	rem ����ȡʣ�µĲ��ָ�������remain����ʵ�������ʹ���ӳٱ�������
	set remain=%%b
)
::�������ʣ��,������ָ�
if defined remain goto :loop
::���û���ظ�:
if "%finded%"=="false" (
	echo �����޸�ϵͳpath��������...
	setx /m "path" "%toAdd%;%path%"
	::��������
	goto :end
)
:isFinded
echo path�����������Ѿ����˸û�������,�����ظ����.
:end
pause
:: ################  ����path�������� ��ʼ  ##############