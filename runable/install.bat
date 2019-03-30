@echo off
::################ 生成当前路径 开始 ########################
::
set thispath=%~dp0
set "thispath=%thispath:~0,-1%"
echo   当前文件所在的目录的绝对路径:%thispath%
::切换盘符
%~d0
::进入路径
cd %~dp0
::
::################ 生成当前路径 结束 ########################
::
:: ################# 创建启动程序 开始 #################
::
echo ############ 生成启动程序 M.bat... #####
::覆盖写入
echo @echo off>M.bat
echo java -jar "%thispath%\M.jar" %%1 %%2 %%3>>M.bat
::
:: ################# 创建启动程序 结束 #################
:: 
::
::
:: ################  配置path环境变量 开始  ##############
echo ############# 配置path环境变量 开始 ##############
::
:: ####################################################
::读取path环境变量到自定义变量中
set remain=%path%
::待添加的路径字符串
set toAdd=%thispath%
::标记,默认没有重复
set finded=false
:loop
for /f "tokens=1* delims=;" %%a in ("%remain%") do (
	::如果找到相同的了
	if "%toAdd%"=="%%a" (
		::直接退出
		goto :isFinded
		::该表标记,true表示有重复的了
		set finded=true
	)
	rem 将截取剩下的部分赋给变量remain，其实这里可以使用延迟变量开关
	set remain=%%b
)
::如果还有剩余,则继续分割
if defined remain goto :loop
::如果没有重复:
if "%finded%"=="false" (
	echo 正在修改系统path环境变量...
	setx /m "path" "%toAdd%;%path%"
	::结束程序
	goto :end
)
:isFinded
echo path环境变量中已经有了该环境变量,无须重复添加.
:end
pause
:: ################  配置path环境变量 开始  ##############