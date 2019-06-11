CREATE TABLE ACCOUNT (
        User_Id varchar(20),
        User_Password varchar(20),
	User_Cookie char(36)
);

CREATE TABLE RECIPE (
        Recipe_Id char(36),
        Recipe_Name varchar(100),
        Recipe_Desc varchar(1000)
);

CREATE TABLE ACCOUNT_RECIPE (
        User_Id varchar(20),
        Recipe_Id char(36),
	User_Cookie char(36)
);

