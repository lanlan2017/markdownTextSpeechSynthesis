@echo off
echo ####################  更新本地仓库开始  #######################
:: 保存文件所在路径
set fatherPath=%~dp0
echo 之前的工作目录:%fatherPath%
::切换盘符
D:
::进入本地参考目录
cd D:\GitHub\MD

echo 当前工作路径 %cd%
echo 正在拉取更新 ...

:: 更新版本库
git pull

:: 切换盘符
echo 切换盘符:%~d0
%~d0
:: 返回上级目录
echo 返回上级目录: %fatherPath%
cd %fatherPath%
echo 现在目录:%cd%
echo ####################  更新本地仓库结束  #######################