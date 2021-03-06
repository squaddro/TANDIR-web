ALTER TABLE ACCOUNT
        ADD CONSTRAINT XPKUSER PRIMARY KEY
     (User_Id);
     
ALTER TABLE RECIPE
        ADD CONSTRAINT XPKRECIPE PRIMARY KEY
     (Recipe_Id);
     
ALTER TABLE ACCOUNT
        ADD CONSTRAINT XPKCOOKIE UNIQUE
     (User_Cookie);

ALTER TABLE PHOTO
	ADD CONSTRAINT XPKCOOKIE PRIMARY KEY
	(Photo_Id);
     
ALTER TABLE ACCOUNT_RECIPE
ADD CONSTRAINT User_Id_FK
FOREIGN KEY (User_Id)
REFERENCES ACCOUNT(User_Id)
ON DELETE CASCADE;
    
ALTER TABLE ACCOUNT_RECIPE
ADD CONSTRAINT Recipe_Id_FK
FOREIGN KEY (Recipe_Id)
REFERENCES RECIPE(Recipe_Id)
ON DELETE CASCADE;

ALTER TABLE ACCOUNT_RECIPE
ADD CONSTRAINT User_Cookie_FK
FOREIGN KEY (User_Cookie)
REFERENCES ACCOUNT(User_Cookie)
ON DELETE CASCADE;

ALTER TABLE RECIPE_PHOTOS
ADD CONSTRAINT RECIPE_PHOTOS_FK
FOREIGN KEY (Recipe_Id)
REFERENCES RECIPE(Recipe_Id)
ON DELETE CASCADE;

ALTER TABLE RECIPE_PHOTO
ADD CONSTRAINT PHOTO_ID_FK
FOREIGN KEY (Photo_Id)
REFERENCES Photo(Photo_Id)
ON DELETE CASCADE;
