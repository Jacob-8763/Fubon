-- 創建資料庫，使用中文台灣字形排序規則 Chinese_Taiwan_Stroke_CI_AS
CREATE DATABASE Shelter
COLLATE Chinese_Taiwan_Stroke_CI_AS;

-- 選擇使用的資料庫
USE Shelter;

-- 創建動物收容所資料表
CREATE TABLE shelter_animals (
    pet_id VARCHAR(50) PRIMARY KEY,    -- 收容編號 (主索引鍵)
    species NVARCHAR(10) COLLATE Chinese_Taiwan_Stroke_CI_AS,   -- 種類 (動物類別名稱)
    breed NVARCHAR(10) COLLATE Chinese_Taiwan_Stroke_CI_AS,     -- 品種 (動物品種名稱)
    color NVARCHAR(10) COLLATE Chinese_Taiwan_Stroke_CI_AS,     -- 花色 (記錄動物花色)
    gender CHAR(2),                    -- 性別 (動物性別)
    status NVARCHAR(10) COLLATE Chinese_Taiwan_Stroke_CI_AS,    -- 狀態 (如待領養、已領養、死亡等)
    created_date DATETIME,             -- 建檔時間 (檔案建立的日期與時間)
    arrival_date DATETIME,             -- 入所日期 (動物進入收容所的日期與時間)
    intake_reason NVARCHAR(MAX) COLLATE Chinese_Taiwan_Stroke_CI_AS,   -- 收容原因 (如迷路、遺棄等)
    departure_date DATETIME,           -- 離所日期 (動物離開收容所的日期與時間)
    adopter_name NVARCHAR(10) COLLATE Chinese_Taiwan_Stroke_CI_AS,  -- 領養人 (領養人的姓名)
    notes NVARCHAR(MAX) COLLATE Chinese_Taiwan_Stroke_CI_AS,        -- 備註 (特殊狀況的備註欄位)
    photo IMAGE                     -- 照片 (動物入所拍攝的照片)
);
