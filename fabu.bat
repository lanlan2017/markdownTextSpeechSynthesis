@echo off
setlocal enabledelayedexpansion
:: ���뵱ǰ�ļ����ڵ�Ŀ¼
:: ���뵱ǰ����Ŀ¼
cd %~dp0
echo ��ǰ����Ŀ¼: %cd%
echo ####################  ������ʼ  #######################
::�鿴��Ϣ
	git status
	echo.
::���û����ж��Ƿ��ύ
	set /p isCommit=�Ƿ��ύ?(y/n):
	if %isCommit%==y (
		goto :commit
	) else (
		goto :end
	)
:commit
	::set /p message=�ύ˵��:
	call :input
	echo #################################
	echo !inputStr!
	git add .
	git commit -m !inputStr!

:push
	echo.
	::���û����ж��Ƿ����͵�Github
	set /p isPush=�Ƿ�push��Github?(y/n):
	if %isPush%==y (
		echo ######################## ����master��֧ ########################
		git checkout master
		echo ######################## �ϲ�dev��֧����ǰ��֧ ########################
		git merge dev
		echo ########################  ���͵�Զ�ֿ̲�  ########################
		git push origin master
		::ֱ�Ӹ��±��زֿ�
		call UpdateLocalRepertory.bat
	)
	echo ######################## ����dev��֧ ########################
	git checkout dev

:end
echo ####################  ��������  #######################
::��������Ȼ�����������ӳ���
goto :eof

::####################### ���������ӳ���input ��ʼ
:input
echo ����#�ű�ʾ¼�����.
echo git commit -m '
:nextLine
set /p message=
if not "%message%"=="#" (
	set "sum=%sum%#__#%message%"
	goto :nextLine
) else (
	goto :done
)
:done
::ɾ��ǰ������ķָ���
set "sum=%sum:~4%
::����������ַ�
::echo %sum%
::�ѷָ���`#__#`�滻�ɻ��з�
set inputStr=!sum:#__#=^

!
echo '
goto :eof
::####################### ���������ӳ��� ����