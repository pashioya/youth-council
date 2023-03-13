-- YOUTH COUNCIL
insert into youth_council (id,logo,name) values (1,'logo','Antwerp YC');
-- MUNICIPALITY
insert into municipality values (1,'Antwerp',1);
-- WEBPAGE
insert into webpage values (1,True,True,True,True,True,True,'webpage AYC',1);
-- QUESTION + QUESTIONNAIRE
insert into question (id,question) values (1,'Are pie charts good?');
insert into questionnaire values (1, '2022-05-12 12:55:44', 'Answer truthfully', 'Antwerp Very Important Questionnaire', 1);
update question set questionnaire_id = 1 where id = 1;
-- STYLE
insert into style values (1,'ARIAL','RED','BLACK');

-- THEME

insert into theme values (1,'Education');

-- STANDARD ACTION

insert into standard_action values (1,'Grants',1);
insert into standard_action values (2,'Study Spaces',1);

-- SOCIAL MEDIA LINKS

insert into social_media_link values (1,'https://www.facebook.com/antwerpyouthcouncil',2,1);

-- SECTION

insert into section values (1, 'This is a section of the landing page this is some text', 'Bring back AH pesto!','image link');

-- POST CODE

insert into postcodes values (1,2000);

-- NEWS ITEM

insert into news_item values (1,'Deliveroo discount for students!', 'image', 'Deliveroo Discount', 1);

-- USER
insert into app_user values (1,'u@gmail.com','admin','admin','$2a$10$yruURJeW9UoDOJhKrO2PNei1yzXsg7TfmUi.xCYfhLPE8RYp.z7W6','2000'
,'ROLE_ADMIN');


-- MEMBERSHIP
insert into membership values (1,'2022-05-12 12:55:44', 1, 1, 1);

-- ACTION POINT

insert into action_point values(1,'2022-05-05 12:55:44','We need to do something regarding this really important thing!',
                                'images',5,1,'Very Important Action Point!','video link',1,1);
-- ACTIVITY

insert into activity values (1,'This is an activity, Lets move!','2023-06-05 12:55:44','Title! Running and moving','2023-06-02 12:55:44',1);

-- IDEA

insert into idea values (1,'2023-06-05 12:55:44','Title! Running and moving',20,1,1,1,1);

-- COMMENT

insert into comment values (1,'This is a comment',1,1);

-- ANSWER

insert into answer values (1,'Yes',1);

-- UPDATE YC
update youth_council set style_id = 1 where id = 1;
update youth_council set home_page_id = 1 where id = 1;
update youth_council set municipality_municipality_id = 1 where id = 1;
update youth_council set questionnaire_id = 1 where id = 1;