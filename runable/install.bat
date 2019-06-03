@echo off
::################ 生成当前路径 开始 ########################
:: 获取当前工作目录
set thispath=%~dp0
set "thispath=%thispath:~0,-1%"
echo   当前文件所在的目录的绝对路径:%thispath%
::因为点击运行的时候默认在C:\Windows\System32\目录下
::切换到文件所在的盘符
%~d0
::进入文件所在的路径
cd %~dp0
::
::################ 生成当前路径 结束 ########################
::
:: ################# 创建启动程序 开始 #################
::
echo ############ 生成启动程序 xunfei.bat... #####
::覆盖写入
echo @echo off>xunfei.bat
echo ::切换盘符>>xunfei.bat
echo %~d0>>xunfei.bat
echo :: 进入文件所在路径>>xunfei.bat
echo cd %~dp0>>xunfei.bat
echo :: 判断是否可以联网,为了节省时间,只发送回显请求1次>>xunfei.bat
echo ping www.xfyun.cn -n 1 ^> nul>>xunfei.bat
echo :: 如果可以联网则执行程序>>xunfei.bat
echo if %%errorlevel%% leq 0 (>>xunfei.bat
echo    echo 网络链接正常.>>xunfei.bat
echo    java -jar "%thispath%\xunfei.jar" %%1>>xunfei.bat
echo ) else (>>xunfei.bat
echo :: 如果不可以联网则给出提示>>xunfei.bat
echo    echo 无法连接到www.xfyun.cn,请求检查网络是否连接正常.>>xunfei.bat
echo    pause>>xunfei.bat
echo    exit>>xunfei.bat
echo )>>xunfei.bat
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