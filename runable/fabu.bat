@echo off
setlocal enabledelayedexpansion
:: 进入当前文件所在的目录
:: 进入当前工作目录
cd %~dp0
echo 当前工作目录: %cd%
echo ####################  发布开始  #######################
::查看信息
	git status
	echo.
::让用户来判断是否提交
	set /p isCommit=是否提交?(y/n):
	if %isCommit%==y (
		goto :commit
	) else (
		goto :end
	)
:commit
	::set /p message=提交说明:
	call :input
	echo #################################
	echo !inputStr!
	git add .
	git commit -m !inputStr!

:push
	echo.
	::让用户来判断是否推送到Github
	set /p isPush=是否push到Github?(y/n):
	if %isPush%==y (
		echo ######################## 换到master分支 ########################
		git checkout master
		echo ######################## 合并dev分支到当前分支 ########################
		git merge dev
		echo ########################  推送到远程仓库  ########################
		git push origin master
		::直接更新本地仓库
		call UpdateLocalRepertory.bat
	)
	echo ######################## 换回dev分支 ########################
	git checkout dev

:end
echo ####################  发布结束  #######################
::结束程序不然会进入下面的子程序
goto :eof

::####################### 多行输入子程序input 开始
:input
echo 输入#号表示录入结束.
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
::删除前面多加入的分隔符
set "sum=%sum:~4%
::输出处理后的字符
::echo %sum%
::把分隔符`#__#`替换成换行符
set inputStr=!sum:#__#=^

!
echo '
goto :eof
::####################### 多行输入子程序 结束