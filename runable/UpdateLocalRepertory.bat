@echo off
echo ####################  ���±��زֿ⿪ʼ  #######################
:: �����ļ�����·��
set fatherPath=%~dp0
echo ֮ǰ�Ĺ���Ŀ¼:%fatherPath%
::�л��̷�
D:
::���뱾�ؿ�¡��Ŀ¼
cd D:\GitHub\xunfei

echo ��ǰ����·�� %cd%
echo ������ȡ���� ...

:: ���°汾��
git pull

:: �л��̷�
echo �л��̷�:%~d0
%~d0
:: �����ϼ�Ŀ¼
echo �����ϼ�Ŀ¼: %fatherPath%
cd %fatherPath%
echo ����Ŀ¼:%cd%
echo ####################  ���±��زֿ����  #######################