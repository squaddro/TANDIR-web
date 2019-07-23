CREATE TABLE ACCOUNT (
        User_Id varchar(20),
        User_Password varchar(20),
	User_Cookie char(36)
		Token varchar(200)
);

CREATE TABLE RECIPE (
        Recipe_Id char(36),
        Recipe_Name varchar(100),
        Recipe_Desc varchar(1000),
	Tag varchar(20),
	Recipe_date varchar(50)
	
);

CREATE TABLE ACCOUNT_RECIPE (
        User_Id varchar(20),
        Recipe_Id char(36),
	User_Cookie char(36)
);

CREATE TABLE RECIPE_PHOTOS(
       recipe_Id char(36),
       photo_Id char (36)
);

CREATE TABLE Photo (
	Photo_id char(36),
	Photo_img bytea
);