-- �Ыظ�Ʈw�A�ϥΤ���x�W�r�αƧǳW�h Chinese_Taiwan_Stroke_CI_AS
CREATE DATABASE Shelter
COLLATE Chinese_Taiwan_Stroke_CI_AS;

-- ��ܨϥΪ���Ʈw
USE Shelter;

-- �Ыذʪ����e�Ҹ�ƪ�
CREATE TABLE shelter_animals (
    pet_id VARCHAR(50) PRIMARY KEY,    -- ���e�s�� (�D������)
    species NVARCHAR(10) COLLATE Chinese_Taiwan_Stroke_CI_AS,   -- ���� (�ʪ����O�W��)
    breed NVARCHAR(10) COLLATE Chinese_Taiwan_Stroke_CI_AS,     -- �~�� (�ʪ��~�ئW��)
    color NVARCHAR(10) COLLATE Chinese_Taiwan_Stroke_CI_AS,     -- ��� (�O���ʪ����)
    gender CHAR(2),                    -- �ʧO (�ʪ��ʧO)
    status NVARCHAR(10) COLLATE Chinese_Taiwan_Stroke_CI_AS,    -- ���A (�p�ݻ�i�B�w��i�B���`��)
    created_date DATETIME,             -- ���ɮɶ� (�ɮ׫إߪ�����P�ɶ�)
    arrival_date DATETIME,             -- �J�Ҥ�� (�ʪ��i�J���e�Ҫ�����P�ɶ�)
    intake_reason NVARCHAR(MAX) COLLATE Chinese_Taiwan_Stroke_CI_AS,   -- ���e��] (�p�g���B���)
    departure_date DATETIME,           -- ���Ҥ�� (�ʪ����}���e�Ҫ�����P�ɶ�)
    adopter_name NVARCHAR(10) COLLATE Chinese_Taiwan_Stroke_CI_AS,  -- ��i�H (��i�H���m�W)
    notes NVARCHAR(MAX) COLLATE Chinese_Taiwan_Stroke_CI_AS,        -- �Ƶ� (�S���p���Ƶ����)
    photo IMAGE                     -- �Ӥ� (�ʪ��J�ҩ��᪺�Ӥ�)
);
